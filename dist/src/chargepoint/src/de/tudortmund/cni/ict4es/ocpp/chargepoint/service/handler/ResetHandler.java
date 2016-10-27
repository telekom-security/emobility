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

import ocpp.cp._2012._06.ResetRequest;
import ocpp.cp._2012._06.ResetResponse;
import ocpp.cp._2012._06.ResetStatus;
import ocpp.cp._2012._06.ResetType;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class ResetHandler {

	private static final Logger log = Logger
			.getLogger(StatusNotificationHandler.class.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	private IChargeBoxController chargeBoxController = null;

	public ResetHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
		chargeBoxController = chargePointService.getChargeBoxController();
	}

	public ResetResponse handleResetReq(String chargeBoxIdentity,
			ResetRequest request) {

		ResetType resetType = request.getType();

		log.info("ResetRequest called at chargeBoxID " + chargeBoxIdentity
				+ ", ResetType: " + resetType);

		// Preparing the response
		ResetResponse response = new ResetResponse();
		ResetStatus status = ResetStatus.REJECTED;
		CBControllerResp cbcResponse = null;

		controlData = chargePointService.getControlData();
		List<Connector> connectorList = controlData.getConnectors();

		cbcResponse = chargeBoxController.handleResetReq(resetType);

		if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

			log.warning("Reset ACCEPTED -> ChargeBoxController " + cbcResponse
					+ " the request.");

			// What kind of reset type? -> SOFT or HARD
			// TODO: Reset has to be handled in Queue
			// SOFT
			if (resetType.equals(ResetType.SOFT)) {

				for (int i = 0; i < connectorList.size(); i++) {

					// Connectors are in OCCUPIED state
					if (connectorList.get(i).getStatus()
							.equals(ChargePointStatus.OCCUPIED)) {

						// TODO: We need to stop current
						// transactions/reservations

					} else {

						statusNotificationHandler.processStatusNotification(
								chargeBoxIdentity, connectorList.get(i)
										.getConnectorId(),
								ChargePointErrorCode.NO_ERROR,
								ChargePointStatus.AVAILABLE, "", DateTimeUtils
										.getCurrentDateTimeAsXML(), "", "");
					}
				}

				status = ResetStatus.ACCEPTED;

				// HARD
				// CP in any state -> reset
			} else if (resetType.equals(ResetType.HARD)) {

				for (int i = 0; i < connectorList.size(); i++) {

					statusNotificationHandler.processStatusNotification(
							chargeBoxIdentity, connectorList.get(i)
									.getConnectorId(),
							ChargePointErrorCode.NO_ERROR,
							ChargePointStatus.AVAILABLE, "", DateTimeUtils
									.getCurrentDateTimeAsXML(), "", "");
				}

				status = ResetStatus.ACCEPTED;
			}

		} else if (cbcResponse.equals(CBControllerResp.REJECTED)) {

			log.warning("Reset FAILED -> ChargeBoxController " + cbcResponse
					+ " the request.");
			status = ResetStatus.REJECTED;

		}

		log.info("ResetResp | ResetType: " + resetType + " | ResetStatus: "
				+ status + " | CBControllerResp: " + cbcResponse);
		response.setStatus(status);
		return response;

	}

}
