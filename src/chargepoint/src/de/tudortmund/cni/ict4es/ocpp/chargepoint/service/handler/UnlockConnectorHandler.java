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

import ocpp.cp._2012._06.UnlockConnectorRequest;
import ocpp.cp._2012._06.UnlockConnectorResponse;
import ocpp.cp._2012._06.UnlockStatus;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class UnlockConnectorHandler {

	private static final Logger log = Logger
			.getLogger(UnlockConnectorHandler.class.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private IChargeBoxController chargeBoxController = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	public UnlockConnectorHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
		chargeBoxController = chargePointService.getChargeBoxController();
	}

	public UnlockConnectorResponse handleUnlockConnectorReq(
			String chargeBoxIdentity, UnlockConnectorRequest request) {

		int connectorId = request.getConnectorId();

		log.info("UnlockConnectorReq called at ChargeBoxID "
				+ chargeBoxIdentity + ", ConnectorId: " + connectorId);

		// Preparing the response
		UnlockConnectorResponse response = new UnlockConnectorResponse();
		UnlockStatus unlockStatus = UnlockStatus.REJECTED;
		CBControllerResp cbcResponse = null;

		controlData = chargePointService.getControlData();
		List<Connector> connectorList = controlData.getConnectors();

		Connector connector = ConnectorUtils.getConnector(connectorList,
				connectorId);

		if (connector != null) {

			// Transaction in progress? -> terminate transaction and transition
			// to AVAILABLE
			if (connector.getStatus().equals(ChargePointStatus.OCCUPIED)) {

				cbcResponse = chargeBoxController
						.handleUnlockConnectorReq(connectorId);

				if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

					statusNotificationHandler.processStatusNotification(
							chargeBoxIdentity, connectorId,
							ChargePointErrorCode.NO_ERROR,
							ChargePointStatus.AVAILABLE, "",
							DateTimeUtils.getCurrentDateTimeAsXML(), "", "");

					log.warning("UnlockConnector ACCEPTED -> ChargeBoxController "
							+ cbcResponse + " the request.");
					unlockStatus = UnlockStatus.ACCEPTED;

				} else {
					log.warning("UnlockConnector REJECTED -> ChargeBoxController "
							+ cbcResponse + " the request.");
					unlockStatus = UnlockStatus.REJECTED;
				}

			} else {
				log.warning("UnlockConnector REJECTED -> Connector has invalid state "
						+ connector.getStatus());
				unlockStatus = UnlockStatus.REJECTED;
			}

		} else {
			log.warning("UnlockConnector REJECTED -> Connector with id "
					+ connectorId + " is UNKNOWN");
			unlockStatus = UnlockStatus.REJECTED;
		}

		log.info("UnlockConnectorResp | ConnectorId: "
				+ request.getConnectorId() + " | UnlockStatus: " + unlockStatus
				+ " | CBControllerResp: " + cbcResponse);
		response.setStatus(unlockStatus);
		return response;

	}

}
