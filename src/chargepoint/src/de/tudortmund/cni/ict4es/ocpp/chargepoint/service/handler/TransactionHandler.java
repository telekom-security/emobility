package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler;

/**
 * @author    Jens Schmutzler <jens.schmutzler@tu-dortmund.de>
 * @author    Thomas Grabowski <thomas.grabowski@tu-dortmund.de>
 * @author    Mohamad Sbeiti <mohamad.sbeiti@telekom.de>
 * 
 *	This program is free software; you can redistribute it
 *	and/or modify it under the terms of the GNU General Public
 *	License as published by the Free Software Foundation; either
 *	version 2 of the License, or (at your option) any later version.
 *  For further information see file COPYING in the top level directory
 *  
 ********************************************************************************
 * This work is a joint work between Communication Networks Institute 
 * (CNI - Prof. Dr.-Ing. Christian Wietfeld) at Technische Universitaet Dortmund, Germany 
 * and the Deutsche Telekom 
 *  ********************************************************************************/
import java.util.List;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import ocpp.cs._2012._06.AuthorizationStatus;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.IdTagInfo;
import ocpp.cs._2012._06.MeterValue;
import ocpp.cs._2012._06.StartTransactionRequest;
import ocpp.cs._2012._06.StartTransactionResponse;
import ocpp.cs._2012._06.StopTransactionRequest;
import ocpp.cs._2012._06.StopTransactionResponse;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Transaction;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;

public class TransactionHandler {

	private static final Logger log = Logger.getLogger(TransactionHandler.class
			.getName());

	private ChargePoint chargePointService;

	private CentralSystemServiceClient csServiceClient = null;

	private ControlData controlData = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	public TransactionHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = chargePointService.getCentralSystemServiceClient();
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
	}

	public StartTransactionResponse processStartTransaction(
			String chargeBoxIdentity, int connectorID, String idTag,
			int meterStart, XMLGregorianCalendar xmlCalTimestampStart,
			int reservationId) {

		log.info("Process StartTransaction");

		AuthorizationStatus authorizationStatus = AuthorizationStatus.INVALID;
		StartTransactionResponse response = null;

		// Start Transaction only valid if connector is available
		controlData = chargePointService.getControlData();
		Connector connector = ConnectorUtils.getConnector(
				controlData.getConnectors(), connectorID);

		if (connector.getStatus().equals(ChargePointStatus.AVAILABLE)) {

			StartTransactionRequest request = csServiceClient
					.prepareStartTransactionReq(chargeBoxIdentity, connectorID,
							idTag, meterStart, xmlCalTimestampStart,
							reservationId);
			response = csServiceClient.sendStartTransaction(chargeBoxIdentity,
					request);
			authorizationStatus = response.getIdTagInfo().getStatus();

			if (authorizationStatus.equals(AuthorizationStatus.CONCURRENT_TX)) {
				addTransaction(new Transaction(chargeBoxIdentity,
						response.getTransactionId(), connectorID, idTag,
						xmlCalTimestampStart, meterStart));
				statusNotificationHandler.processStatusNotification(
						chargeBoxIdentity, connectorID,
						ChargePointErrorCode.NO_ERROR,
						ChargePointStatus.OCCUPIED, "", xmlCalTimestampStart,
						"", "");
				connector.setStatus(ChargePointStatus.OCCUPIED.value());

			} else {

				log.warning("StartTransactionReq REJECTED -> IdTag AuthorizationStatus is "
						+ authorizationStatus);
			}

		} else {

			log.warning("StartTransactionReq REJECTED -> Requested ConnectorId "
					+ connectorID
					+ " in invalid state "
					+ connector.getStatus());

			IdTagInfo idTagInfo = new IdTagInfo();
			idTagInfo.setStatus(authorizationStatus);
			response = new StartTransactionResponse();
			response.setIdTagInfo(idTagInfo);
		}

		log.info("StopTransactionResponse | ConnectorId: " + connectorID
				+ " | IdTag: " + idTag + " | Status: " + authorizationStatus);
		return response;
	}

	public StopTransactionResponse processStopTransaction(
			String chargeBoxIdentity, int meterStop,
			XMLGregorianCalendar xmlCalTimestampStop, int transactionID,
			String idTag, List<MeterValue> meterValueList, int connectorID) {

		log.info("Process StopTransaction, transactionId: " + transactionID);

		AuthorizationStatus authorizationStatus = AuthorizationStatus.INVALID;
		StopTransactionResponse response = null;
		IdTagInfo idTagInfo = null;

		// Stop Transaction only valid if connector is occupied
		controlData = chargePointService.getControlData();
		Connector connector = ConnectorUtils.getConnector(
				controlData.getConnectors(), connectorID);

		if (connector.getStatus().equals(ChargePointStatus.OCCUPIED)) {

			Transaction transaction = getTransaction(transactionID);

			if (transaction != null) {

				StopTransactionRequest request = csServiceClient
						.prepareStopTransactionReq(chargeBoxIdentity,
								meterStop, xmlCalTimestampStop, transactionID,
								idTag, meterValueList, connectorID);
				response = csServiceClient.sendStopTransaction(
						chargeBoxIdentity, request);
				statusNotificationHandler.processStatusNotification(
						chargeBoxIdentity, connectorID,
						ChargePointErrorCode.NO_ERROR,
						ChargePointStatus.AVAILABLE, "", xmlCalTimestampStop,
						"", "");
				connector.setStatus(ChargePointStatus.AVAILABLE.value());

			} else {
				log.warning("StopTransactionreq REJECTED -> Transaction with id "
						+ transactionID + " is UNKNOWN");
				idTagInfo = new IdTagInfo();
				idTagInfo.setStatus(authorizationStatus);
				response = new StopTransactionResponse();
				response.setIdTagInfo(idTagInfo);
			}

		} else {
			log.warning("StopTransactionreq REJECTED -> Requested ConnectorId "
					+ connectorID + " in invalid state "
					+ connector.getStatus());
			idTagInfo = new IdTagInfo();
			idTagInfo.setStatus(authorizationStatus);
			response = new StopTransactionResponse();
			response.setIdTagInfo(idTagInfo);
		}

		log.info("StopTransactionResponse | ConnectorId: " + connectorID
				+ " | IdTag: " + idTag + " | Status: "
				+ response.getIdTagInfo().getStatus());
		return response;
	}

	public void addTransaction(Transaction transaction) {
		chargePointService.getControlData().getTransactions().add(transaction);
	}

	private Transaction getTransaction(int transactionId) {

		Transaction transaction = null;

		List<Transaction> transactionsList = chargePointService
				.getControlData().getTransactions();

		for (int i = 0; i < transactionsList.size(); i++) {
			if (transactionsList.get(i).getTransactionId() == transactionId) {
				transaction = transactionsList.get(i);
				break;
			}
		}
		return transaction;
	}
}
