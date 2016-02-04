package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces;

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
import ocpp.cp._2012._06.AvailabilityType;
import ocpp.cp._2012._06.ResetType;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.CBControllerResp;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;

public interface IChargeBoxController {

	public CBControllerResp handleChangeAvailabilityReq(
			AvailabilityType availabilityType, int connectorId);

	public CBControllerResp handleStartRemoteTransactionReq(
			Connector connector, String idTag);

	public CBControllerResp handleStopRemoteTransactionReq();

	public CBControllerResp handleUnlockConnectorReq(int connectorId);

	public CBControllerResp handleResetReq(ResetType resetType);

	public CBControllerResp handleReserveNowReq(int connectorId);

	public CBControllerResp handleCancelReservationReq(int connectorId);

	// public enum CBControllerResp {
	//
	// REJECTED("Rejected"), ACCEPTED("Accepted");
	//
	// private final String value;
	//
	// CBControllerResp(String v) {
	// value = v;
	// }
	//
	// public String value() {
	// return value;
	// }
	//
	// public static CBControllerResp fromValue(String v) {
	// for (CBControllerResp c : CBControllerResp.values()) {
	// if (c.value.equals(v)) {
	// return c;
	// }
	// }
	// throw new IllegalArgumentException(v);
	// }
	//
	// }

}
