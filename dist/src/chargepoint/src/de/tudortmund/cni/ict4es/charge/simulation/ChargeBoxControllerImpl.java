package de.tudortmund.cni.ict4es.charge.simulation;

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
import java.util.logging.Logger;

import ocpp.cp._2012._06.AvailabilityType;
import ocpp.cp._2012._06.ResetType;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;

public class ChargeBoxControllerImpl implements IChargeBoxController {

	private static final Logger log = Logger
			.getLogger(ChargeBoxControllerImpl.class.getName());

	private ChargeSimController chargeSimController = null;

	public ChargeBoxControllerImpl(ChargeSimController chargeSimController) {
		this.chargeSimController = chargeSimController;
	}

	@Override
	public CBControllerResp handleChangeAvailabilityReq(
			AvailabilityType availabilityType, int connectorId) {
		log.info("Receive ChangeAvailabiltyReq | AvailabilityType: "
				+ availabilityType.toString() + " | ConnectorId: "
				+ connectorId);
		chargeSimController.stopSimulation();
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleStartRemoteTransactionReq(
			Connector connector, String idTag) {
		log.info("Receive StartRemoteTransactionReq | ConnectorId: "
				+ connector.getConnectorId() + " | IdTag: " + idTag);
		chargeSimController.startSimulation(true, connector, idTag);
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleStopRemoteTransactionReq() {
		log.info("Receive StopRemoteTransactionReq");
		chargeSimController.stopSimulation();
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleUnlockConnectorReq(int connectorId) {
		log.info("Receive UnlockConnectorReq | ConnectorId: " + connectorId);
		chargeSimController.stopSimulation();
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleResetReq(ResetType resetType) {
		log.info("Receive ResetReq | ResetType: " + resetType.toString());
		chargeSimController.stopSimulation();
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleReserveNowReq(int connectorId) {
		log.info("Receive ReserveNowReq | ConnectorId: " + connectorId);
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

	@Override
	public CBControllerResp handleCancelReservationReq(int connectorId) {
		log.info("Receive CancelReservationReq | ConnectorId: " + connectorId);
		CBControllerResp response = CBControllerResp.ACCEPTED;
		return response;
	}

}
