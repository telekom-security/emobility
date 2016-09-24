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

import ocpp.cp._2012._06.RemoteStartStopStatus;
import ocpp.cp._2012._06.RemoteStartTransactionRequest;
import ocpp.cp._2012._06.RemoteStartTransactionResponse;
import ocpp.cp._2012._06.RemoteStopTransactionRequest;
import ocpp.cp._2012._06.RemoteStopTransactionResponse;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.StopTransactionRequest;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Transaction;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class RemoteTransactionHandler {

	private static final Logger log = Logger
			.getLogger(StatusNotificationHandler.class.getName());

	private ChargePoint chargePointService;

	private CentralSystemServiceClient csServiceClient = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	private ControlData controlData = null;

	private IChargeBoxController chargeBoxController = null;

	public RemoteTransactionHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = chargePointService.getCentralSystemServiceClient();
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
		chargeBoxController = chargePointService.getChargeBoxController();
	}

	public RemoteStartTransactionResponse handleRemoteStartTransactionReq(
			String chargeBoxIdentity, RemoteStartTransactionRequest request) {

		log.info("RemoteStartTransactionReq called at ChargeBoxID: "
				+ chargeBoxIdentity);

		// Preparing the response
		RemoteStartTransactionResponse response = new RemoteStartTransactionResponse();
		RemoteStartStopStatus status = RemoteStartStopStatus.REJECTED;
		CBControllerResp cbcResponse = null;

		String idTag = request.getIdTag();

		if (request.getConnectorId() != null) {

			int connectorId = request.getConnectorId();

			log.info("IdTag:" + idTag);
			log.info("ConnectorId:" + request.getConnectorId());

			controlData = chargePointService.getControlData();
			Connector connector = ConnectorUtils.getConnector(
					controlData.getConnectors(), connectorId);

			if (connector != null) {

				if (connector.getStatus().equals(ChargePointStatus.AVAILABLE)) {

					// Sending Event to Charge Box Controller
					cbcResponse = chargeBoxController
							.handleStartRemoteTransactionReq(connector, idTag);

					if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

						log.warning("RemoteStartTransaction ACCEPTED -> ChargeBoxController "
								+ cbcResponse + " the request.");

						status = RemoteStartStopStatus.ACCEPTED;

						// removed to support charge simulation
						// StartTransactionRequest startTransactionRequest =
						// csServiceClient.prepareStartTransactionReq(chargeBoxIdentity,
						// connectorId, idTag,
						// 0, DateTimeUtils.getCurrentDateTimeAsXML(), -1);
						// StartTransactionResponse startTransactionResp =
						// csServiceClient.sendStartTransaction(chargeBoxIdentity,
						// startTransactionRequest);
						//
						// if
						// (startTransactionResp.getIdTagInfo().getStatus().equals(AuthorizationStatus.ACCEPTED))
						// {
						//
						// addTransaction(new Transaction(chargeBoxIdentity,
						// startTransactionResp.getTransactionId(), connectorId,
						// idTag,
						// DateTimeUtils.getCurrentDateTimeAsXML(), 0));
						//
						// statusNotificationHandler.processStatusNotification(chargeBoxIdentity,
						// connectorId, ChargePointErrorCode.NO_ERROR,
						// ChargePointStatus.OCCUPIED, "",
						// DateTimeUtils.getCurrentDateTimeAsXML(), "", "");
						//
						// log.warning("RemoteStartTransactionReq ACCEPTED -> IdTag AuthorizationStatus is "
						// + startTransactionResp.getIdTagInfo().getStatus());
						// status = RemoteStartStopStatus.ACCEPTED;
						//
						// } else {
						// log.warning("RemoteStartTransactionReq REJECTED -> IdTag AuthorizationStatus is "
						// + startTransactionResp.getIdTagInfo().getStatus());
						// status = RemoteStartStopStatus.REJECTED;
						// }

					} else {
						log.warning("RemoteStartTransaction REJECTED -> ChargeBoxController "
								+ cbcResponse + " the request.");
						status = RemoteStartStopStatus.REJECTED;
					}

				} else {
					log.warning("RemoteStartTransaction REJECTED -> Connector has invalid state "
							+ connector.getStatus());
					status = RemoteStartStopStatus.REJECTED;
				}

			} else {
				log.warning("RemoteStartTransaction REJECTED -> Connector with id "
						+ connectorId + " is UNKNOWN");
				status = RemoteStartStopStatus.REJECTED;
			}

		} else {
			log.warning("RemoteStartTransaction REJECTED -> Connectorid is NULL");
			status = RemoteStartStopStatus.REJECTED;
		}

		log.info("RemoteStartTransactionResp | RemoteStartStatus: " + status
				+ " | CBControllerResp: " + cbcResponse);
		response.setStatus(status);
		return response;
	}

	public RemoteStopTransactionResponse handleRemoteStopTransactionReq(
			String chargeBoxIdentity, RemoteStopTransactionRequest request) {

		int transactionId = request.getTransactionId();

		log.info("RemoteStopTransactionReq called at ChargeBoxID: "
				+ chargeBoxIdentity + ", TransactionId: " + transactionId);

		// Preparing the response
		RemoteStopTransactionResponse response = new RemoteStopTransactionResponse();
		RemoteStartStopStatus status = RemoteStartStopStatus.REJECTED;
		CBControllerResp cbcResponse = null;
		controlData = chargePointService.getControlData();

		Transaction transaction = getTransaction(transactionId);

		if (transaction != null) {

			Connector connector = ConnectorUtils.getConnector(
					controlData.getConnectors(), transaction.getConnectorId());

			if (connector != null) {

				if (connector.getStatus().equals(ChargePointStatus.OCCUPIED)) {

					cbcResponse = chargeBoxController
							.handleStopRemoteTransactionReq();

					if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

						StopTransactionRequest stopTransactionRequest = csServiceClient
								.prepareStopTransactionReq(
										transaction.getChargeBoxId(),
										connector.getMeterValue(),
										DateTimeUtils.getCurrentDateTimeAsXML(),
										transactionId, transaction.getIdTag(),
										null, transaction.getConnectorId());

						csServiceClient.sendStopTransaction(chargeBoxIdentity,
								stopTransactionRequest);

						statusNotificationHandler
								.processStatusNotification(
										chargeBoxIdentity,
										connector.getConnectorId(),
										ChargePointErrorCode.NO_ERROR,
										ChargePointStatus.AVAILABLE,
										"",
										DateTimeUtils.getCurrentDateTimeAsXML(),
										"", "");

						log.warning("RemoteStartStopStatus ACCEPTED -> ChargeBoxController "
								+ cbcResponse + " the request.");
						status = RemoteStartStopStatus.ACCEPTED;

					} else {
						log.warning("RemoteStartStopStatus REJECTED -> ChargeBoxController "
								+ cbcResponse + " the request.");
						status = RemoteStartStopStatus.REJECTED;
					}

				} else {
					log.warning("RemoteStartStopStatus REJECTED -> Connector state is "
							+ connector.getStatus() + " is INVALID");
					status = RemoteStartStopStatus.REJECTED;
				}

			} else {
				log.warning("RemoteStartStopStatus REJECTED -> Connector with id "
						+ transaction.getConnectorId() + " is UNKNOWN");
				status = RemoteStartStopStatus.REJECTED;
			}

		} else {
			log.warning("RemoteStartStopStatus REJECTED -> Transaction with id "
					+ transactionId + " is UNKNOWN");
			status = RemoteStartStopStatus.REJECTED;
		}

		log.info("RemoteStopTransactionResp | RemoteStopStatus: " + status
				+ " | CBControllerResp: " + cbcResponse);
		response.setStatus(status);
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
