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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import ocpp.cp._2012._06.CancelReservationRequest;
import ocpp.cp._2012._06.CancelReservationResponse;
import ocpp.cp._2012._06.CancelReservationStatus;
import ocpp.cp._2012._06.ReservationStatus;
import ocpp.cp._2012._06.ReserveNowRequest;
import ocpp.cp._2012._06.ReserveNowResponse;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Reservation;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.InputUtils;

public class ReservationHandler {

	private static final Logger log = Logger
			.getLogger(StatusNotificationHandler.class.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private IChargeBoxController chargeBoxController = null;

	private StatusNotificationHandler statusNotificationHandler = null;

	public ReservationHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		statusNotificationHandler = chargePointService
				.getStatusNotificationHandler();
		chargeBoxController = chargePointService.getChargeBoxController();
	}

	public ReserveNowResponse handleReserveNowReq(String chargeBoxIdentity,
			ReserveNowRequest request) {

		int reqConnectorId = request.getConnectorId();
		String idTag = request.getIdTag();

		log.info("ReserveNowReq called at chargeBoxID: " + chargeBoxIdentity
				+ ", IdTag: " + idTag + ", ConnectorId: " + reqConnectorId);

		XMLGregorianCalendar expiryDatetime = request.getExpiryDate();
		int reqReservationId = request.getReservationId();
		String parentIdTag = null;
		CBControllerResp cbcResponse = null;

		if (InputUtils.isNullOrEmpty(request.getParentIdTag())) {
			parentIdTag = null;
		}

		// Create response
		ReserveNowResponse response = new ReserveNowResponse();
		ReservationStatus status = ReservationStatus.REJECTED;

		controlData = chargePointService.getControlData();
		List<Connector> connectorList = controlData.getConnectors();

		if (reqConnectorId == 0) {

			boolean validState = validateConnectorState(connectorList);

			if (validState) {

				cbcResponse = chargeBoxController
						.handleReserveNowReq(reqConnectorId);

				if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

					log.warning("ReserveNow ACCEPTED -> ChargeBoxController "
							+ cbcResponse + " the request.");

					for (int i = 0; i < connectorList.size(); i++) {

						int connectorId = connectorList.get(i).getConnectorId();
						Reservation newReservation = new Reservation(
								connectorId, reqReservationId, idTag,
								chargeBoxIdentity,
								DateTimeUtils.getCurrentDateTimeAsXML(),
								expiryDatetime, parentIdTag);

						if (connectorList.get(i).getStatus()
								.equals(ChargePointStatus.AVAILABLE)) {

							statusNotificationHandler
									.processStatusNotification(
											chargeBoxIdentity, connectorId,
											ChargePointErrorCode.NO_ERROR,
											ChargePointStatus.RESERVED, "",
											DateTimeUtils
													.getCurrentDateTimeAsXML(),
											"", "");
							addNewReservation(newReservation);

						} else if (connectorList.get(i).getStatus()
								.equals(ChargePointStatus.RESERVED)) {

							for (int j = 0; j < controlData.getReservations()
									.size(); j++) {

								if (controlData.getReservations().get(j)
										.getConnectorId() == connectorId) {
									updateReservation(controlData
											.getReservations().get(j),
											newReservation);

								}

							}

						}

					}

					status = ReservationStatus.ACCEPTED;

				} else {

					log.warning("ReserveNow FAILED -> ChargeBoxController "
							+ cbcResponse + " the request.");
					status = ReservationStatus.REJECTED;

				}

			} else {
				status = ReservationStatus.REJECTED;
			}

			// Reservation on specific connectorID
		} else {

			Connector connector = ConnectorUtils.getConnector(connectorList,
					reqConnectorId);
			Reservation newReservation = new Reservation(reqConnectorId,
					reqReservationId, idTag, chargeBoxIdentity,
					DateTimeUtils.getCurrentDateTimeAsXML(), expiryDatetime,
					parentIdTag);

			if (connector != null) {

				if (connector.getStatus().equals(ChargePointStatus.AVAILABLE)) {

					cbcResponse = chargeBoxController
							.handleReserveNowReq(reqConnectorId);

					if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

						log.warning("ReserveNow ACCEPTED -> ChargeBoxController "
								+ cbcResponse + " the request.");

						statusNotificationHandler
								.processStatusNotification(
										chargeBoxIdentity,
										reqConnectorId,
										ChargePointErrorCode.NO_ERROR,
										ChargePointStatus.RESERVED,
										"",
										DateTimeUtils.getCurrentDateTimeAsXML(),
										"", "");
						addNewReservation(newReservation);
						status = ReservationStatus.ACCEPTED;

					} else {

						log.warning("ReserveNow FAILED -> ChargeBoxController "
								+ cbcResponse + " the request.");
						status = ReservationStatus.REJECTED;
					}

				} else if (connector.getStatus().equals(
						ChargePointStatus.RESERVED)) {

					for (int j = 0; j < controlData.getReservations().size(); j++) {

						if (connector.getConnectorId() == reqConnectorId) {
							updateReservation(controlData.getReservations()
									.get(j), newReservation);
							status = ReservationStatus.ACCEPTED;
							break;
						}
					}

				} else {

					status = ReservationStatus.REJECTED;
				}

			}
		}

		log.info("ReserveNowResp | ConnectorId: " + reqConnectorId
				+ " | ExpiryDate: " + expiryDatetime + " | IdTag: " + idTag
				+ " | ReservationId: " + reqReservationId
				+ " | ReservationStatus: " + status + " | CBControllerResp: "
				+ cbcResponse);
		response.setStatus(status);
		return response;
	}

	public CancelReservationResponse handleCancelReservationReq(
			String chargeBoxIdentity, CancelReservationRequest request) {

		int reservationId = request.getReservationId();

		log.info("CancelReservationReq called at ChargeBoxID "
				+ chargeBoxIdentity + ", ReservationId: " + reservationId);

		CancelReservationResponse response = new CancelReservationResponse();
		CancelReservationStatus status = CancelReservationStatus.REJECTED;
		CBControllerResp cbcResponse = null;

		controlData = chargePointService.getControlData();
		List<Connector> connectorList = controlData.getConnectors();
		List<Integer> listOfConnectorIds = getConnectorIdFromReservationId(reservationId);

		for (int i = 0; i < listOfConnectorIds.size(); i++) {

			Connector connector = ConnectorUtils.getConnector(connectorList,
					listOfConnectorIds.get(i));

			if (connector.getStatus().equals(ChargePointStatus.RESERVED)) {

				cbcResponse = chargeBoxController
						.handleCancelReservationReq(connector.getConnectorId());

				if (cbcResponse.equals(CBControllerResp.ACCEPTED)) {

					boolean isCanceled = cancelReservation(reservationId);

					if (isCanceled) {

						log.warning("CancelReservation ACCEPTED -> ChargeBoxController "
								+ cbcResponse + " the request.");

						statusNotificationHandler
								.processStatusNotification(
										chargeBoxIdentity,
										connector.getConnectorId(),
										ChargePointErrorCode.NO_ERROR,
										ChargePointStatus.AVAILABLE,
										"",
										DateTimeUtils.getCurrentDateTimeAsXML(),
										"", "");

						status = CancelReservationStatus.ACCEPTED;

					} else {
						status = CancelReservationStatus.REJECTED;
					}

				} else {

					log.warning("CancelReservation FAILED -> ChargeBoxController "
							+ cbcResponse + " the request.");
					status = CancelReservationStatus.REJECTED;
				}

			}

		}

		log.info("CancelReservationResp | CancelReservationStatus: " + status
				+ " | CBControllerResp: " + cbcResponse);
		response.setStatus(status);
		return response;
	}

	private List<Integer> getConnectorIdFromReservationId(int reservationId) {

		controlData = chargePointService.getControlData();
		List<Integer> listOfConnectorIds = new ArrayList<Integer>();

		for (Reservation localReservation : controlData.getReservations()) {

			if (localReservation.getReservationId() == reservationId) {
				listOfConnectorIds.add(localReservation.getConnectorId());
			}

		}

		return listOfConnectorIds;
	}

	public void addNewReservation(Reservation newReservation) {
		controlData = chargePointService.getControlData();
		controlData.getReservations().add(newReservation);
		log.info("Added new reservation for ConnectorID: "
				+ newReservation.getConnectorId() + " | ReservationID: "
				+ newReservation.getReservationId());
	}

	public void updateReservation(Reservation localReservation,
			Reservation newReservation) {
		localReservation.setReservationId(newReservation.getReservationId());
		localReservation.setExpiryDatetime(newReservation.getExpiryDatetime());
		localReservation.setChargeBoxId(newReservation.getChargeBoxId());
		localReservation.setIdTag(newReservation.getIdTag());
		localReservation.setStartDatetime(newReservation.getStartDatetime());
		log.info("Updated existing reservation for ConnectorID: "
				+ newReservation.getConnectorId() + " | ReservationID: "
				+ localReservation.getReservationId());
	}

	private boolean cancelReservation(int reservationId) {

		controlData = chargePointService.getControlData();

		for (Reservation localReservation : controlData.getReservations()) {

			if (localReservation.getReservationId() == reservationId) {
				controlData.getReservations().remove(localReservation);
				log.info("Canceled existing reservation for ConnectorID: "
						+ localReservation.getConnectorId()
						+ " | ReservationID: "
						+ localReservation.getReservationId());
				return true;
			}

		}

		log.info("Unkown reservationId " + reservationId);
		return false;
	}

	private boolean validateConnectorState(List<Connector> connectorList) {

		boolean validState = false;

		for (int i = 0; i < connectorList.size(); i++) {

			// Connectors are in UNAVAILABLE state or AVAILABLE
			if (connectorList.get(i).getStatus()
					.equals(ChargePointStatus.AVAILABLE)
					|| connectorList.get(i).getStatus()
							.equals(ChargePointStatus.RESERVED)) {
				validState = true;
			} else {
				return validState = false;
			}

		}

		return validState;
	}

}
