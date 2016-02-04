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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import ocpp.cp._2012._06.AvailabilityStatus;
import ocpp.cp._2012._06.AvailabilityType;
import ocpp.cp._2012._06.ChangeAvailabilityRequest;
import ocpp.cp._2012._06.ChangeAvailabilityResponse;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class ChangeAvailabilityHandler {

	private static final Logger log = Logger
			.getLogger(StatusNotificationHandler.class.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	private IChargeBoxController chargeBoxController = null;

	public ChangeAvailabilityHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
		chargeBoxController = chargePointService.getChargeBoxController();
	}

	public ChangeAvailabilityResponse handleChangeAvailabilityReq(
			String chargeBoxIdentity, ChangeAvailabilityRequest request) {

		AvailabilityType availabilityType = request.getType();
		int connectorId = request.getConnectorId();
		CBControllerResp cbcResponse = null;

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("AvailabilityType", availabilityType.value());
		properties.put("ConnectorId", String.valueOf(connectorId));

		log.info("ChangeAvailabilityReq called at ChargeBoxID: "
				+ chargeBoxIdentity + ", AvailabilityType: "
				+ availabilityType.toString() + ", ConnectorId: " + connectorId);

		ChangeAvailabilityResponse response = new ChangeAvailabilityResponse();
		AvailabilityStatus status = AvailabilityStatus.REJECTED;

		controlData = chargePointService.getControlData();
		List<Connector> connectorList = controlData.getConnectors();

		// ChangeAvailability on the whole charge point
		if (connectorId == 0) {

			if (availabilityType.equals(AvailabilityType.INOPERATIVE)) {

				boolean validState = validateConnectorState(connectorList);

				if (validState) {

					cbcResponse = chargeBoxController
							.handleChangeAvailabilityReq(availabilityType,
									connectorId);

					if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

						log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
								+ cbcResponse + " the request.");

						for (int i = 0; i < connectorList.size(); i++) {

							// Connector is in AVAILABLE state -> transition to
							// UNAVAILABLE
							if (connectorList.get(i).getStatus()
									.equals(ChargePointStatus.AVAILABLE)) {

								statusNotificationHandler
										.processStatusNotification(
												chargeBoxIdentity,
												connectorList.get(i)
														.getConnectorId(),
												ChargePointErrorCode.NO_ERROR,
												ChargePointStatus.UNAVAILABLE,
												"",
												DateTimeUtils
														.getCurrentDateTimeAsXML(),
												"", "");

								status = AvailabilityStatus.ACCEPTED;

								// TODO: Connector is in OCCUPIED state ->
								// transition will be scheduled after the
								// transaction has finished
							} else if (connectorList.get(i).getStatus()
									.equals(ChargePointStatus.OCCUPIED)) {

							}

						}

					} else if (cbcResponse.equals(CBControllerResp.REJECTED)) {
						log.warning("ChangeAvailability REJECTED -> ChargeBoxController "
								+ cbcResponse + " the request.");
						status = AvailabilityStatus.REJECTED;
					}

				} else {
					log.warning("ChangeAvailability REJECTED -> Connectors are in invalid states.");
					status = AvailabilityStatus.REJECTED;
				}

			} else if (availabilityType.equals(AvailabilityType.OPERATIVE)) {

				boolean validState = validateConnectorState(connectorList);

				if (validState) {

					for (int i = 0; i < connectorList.size(); i++) {

						// Connector is in UNAVAILABLE state -> transition to
						// AVAILABLE
						if (connectorList.get(i).getStatus()
								.equals(ChargePointStatus.UNAVAILABLE)) {

							cbcResponse = chargeBoxController
									.handleChangeAvailabilityReq(
											availabilityType, connectorId);

							if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

								status = AvailabilityStatus.ACCEPTED;
								log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
										+ cbcResponse + " the request.");
								statusNotificationHandler
										.processStatusNotification(
												chargeBoxIdentity,
												connectorList.get(i)
														.getConnectorId(),
												ChargePointErrorCode.NO_ERROR,
												ChargePointStatus.AVAILABLE,
												"",
												DateTimeUtils
														.getCurrentDateTimeAsXML(),
												"", "");

							} else {

								log.warning("ChangeAvailability FAILED -> ChargeBoxController "
										+ cbcResponse + " the request.");
								status = AvailabilityStatus.REJECTED;

							}

							// TODO: Connector is in OCCUPIED state ->
							// transition will be scheduled after the
							// transaction has finished
						} else if (connectorList.get(i).getStatus()
								.equals(ChargePointStatus.OCCUPIED)) {

							cbcResponse = chargeBoxController
									.handleChangeAvailabilityReq(
											availabilityType, connectorId);

							if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

								status = AvailabilityStatus.ACCEPTED;
								log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
										+ cbcResponse + " the request.");
								statusNotificationHandler
										.processStatusNotification(
												chargeBoxIdentity,
												connectorList.get(i)
														.getConnectorId(),
												ChargePointErrorCode.NO_ERROR,
												ChargePointStatus.AVAILABLE,
												"",
												DateTimeUtils
														.getCurrentDateTimeAsXML(),
												"", "");

							} else {

								log.warning("ChangeAvailability FAILED -> ChargeBoxController "
										+ cbcResponse + " the request.");
								status = AvailabilityStatus.REJECTED;

							}

						}

					}

				} else {
					log.warning("ChangeAvailability REJECTED -> Connectors are in ivalid states.");
					status = AvailabilityStatus.REJECTED;
				}

			}

			// ChangeAvailability on specific connectorID
		} else {

			Connector connector = ConnectorUtils.getConnector(connectorList,
					connectorId);

			if (availabilityType.equals(AvailabilityType.INOPERATIVE)) {

				if (connector != null) {

					// Connector is in UNAVAILABLE
					if (connector.getStatus().equals(
							ChargePointStatus.UNAVAILABLE)) {

						status = AvailabilityStatus.ACCEPTED;

						// Connector is in AVAILABLE transition to UNAVAILABLE
					} else if (connector.getStatus().equals(
							ChargePointStatus.AVAILABLE)) {

						cbcResponse = chargeBoxController
								.handleChangeAvailabilityReq(availabilityType,
										connectorId);

						if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

							log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							statusNotificationHandler
									.processStatusNotification(
											chargeBoxIdentity, connector
													.getConnectorId(),
											ChargePointErrorCode.NO_ERROR,
											ChargePointStatus.UNAVAILABLE, "",
											DateTimeUtils
													.getCurrentDateTimeAsXML(),
											"", "");

							status = AvailabilityStatus.ACCEPTED;

						} else {
							log.warning("ChangeAvailability REJECTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							status = AvailabilityStatus.REJECTED;
						}

						// TODO: Connector is in OCCUPIED state -> transition
						// will be scheduled after the transaction has finished
					} else if (connector.getStatus().equals(
							ChargePointStatus.OCCUPIED)) {

						cbcResponse = chargeBoxController
								.handleChangeAvailabilityReq(availabilityType,
										connectorId);

						if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

							log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							statusNotificationHandler
									.processStatusNotification(
											chargeBoxIdentity, connector
													.getConnectorId(),
											ChargePointErrorCode.NO_ERROR,
											ChargePointStatus.UNAVAILABLE, "",
											DateTimeUtils
													.getCurrentDateTimeAsXML(),
											"", "");

							status = AvailabilityStatus.ACCEPTED;

						} else {
							log.warning("ChangeAvailability REJECTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							status = AvailabilityStatus.REJECTED;
						}

					}

				} else {
					log.warning("ChangeAvailability REJECTED -> Connector with id "
							+ connectorId + " is UNKNOWN");
					status = AvailabilityStatus.REJECTED;
				}

			} else if (availabilityType.equals(AvailabilityType.OPERATIVE)) {

				if (connector != null) {

					if (connector.getStatus().equals(
							ChargePointStatus.AVAILABLE)
							|| connector.getStatus().equals(
									ChargePointStatus.OCCUPIED)) {

						status = AvailabilityStatus.ACCEPTED;

					} else if (connector.getStatus().equals(
							ChargePointStatus.UNAVAILABLE)) {

						cbcResponse = chargeBoxController
								.handleChangeAvailabilityReq(availabilityType,
										connectorId);

						if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

							log.warning("ChangeAvailability ACCEPTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							statusNotificationHandler
									.processStatusNotification(
											chargeBoxIdentity, connector
													.getConnectorId(),
											ChargePointErrorCode.NO_ERROR,
											ChargePointStatus.AVAILABLE, "",
											DateTimeUtils
													.getCurrentDateTimeAsXML(),
											"", "");

							status = AvailabilityStatus.ACCEPTED;

						} else {

							log.warning("ChangeAvailability REJECTED -> ChargeBoxController "
									+ cbcResponse + " the request.");
							status = AvailabilityStatus.REJECTED;
						}

					}

				} else {
					log.warning("ChangeAvailability REJECTED -> Connector with id "
							+ connectorId + " is UNKNOWN");
					status = AvailabilityStatus.REJECTED;
				}

			}

		}

		log.info("ChangeAvailabilityResp | AvailabilityType: "
				+ availabilityType + " | AvailabilityStatus: " + status
				+ " | CBControllerResp: " + cbcResponse);
		response.setStatus(status);
		return response;
	}

	private boolean validateConnectorState(List<Connector> connectorList) {

		boolean validState = false;

		for (int i = 0; i < connectorList.size(); i++) {

			// Connectors are in UNAVAILABLE state or AVAILABLE
			if (connectorList.get(i).getStatus()
					.equals(ChargePointStatus.UNAVAILABLE)
					|| connectorList.get(i).getStatus()
							.equals(ChargePointStatus.AVAILABLE)) {
				validState = true;
			} else {
				return validState = false;
			}

		}

		return validState;

	}
}
