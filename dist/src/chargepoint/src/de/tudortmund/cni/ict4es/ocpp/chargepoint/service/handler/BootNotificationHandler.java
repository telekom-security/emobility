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
import java.util.logging.Logger;

import ocpp.cs._2012._06.BootNotificationRequest;
import ocpp.cs._2012._06.BootNotificationResponse;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;

public class BootNotificationHandler {

	private static final Logger log = Logger
			.getLogger(BootNotificationHandler.class.getName());

	private ChargePoint chargePointService;
	private ControlData controlData = null;
	private CentralSystemServiceClient cServiceClient = null;

	public BootNotificationHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		cServiceClient = chargePointService.getCentralSystemServiceClient();
	}

	public BootNotificationResponse processBootNotification(
			String chargeBoxIdentity, String chargePointModel,
			String chargePointVendor, String chargePointSerialNumber,
			String chargeBoxSerialNumber, String firmwareVersion, String iccid,
			String imsi, String meterSerialNumber, String meterType) {

		log.info("Process BootNotification");

		controlData = chargePointService.getControlData();

		BootNotificationRequest request = cServiceClient
				.prepareBootNotificationReq(chargeBoxIdentity,
						chargePointModel, chargePointVendor,
						chargePointSerialNumber, chargeBoxSerialNumber,
						firmwareVersion, iccid, imsi, meterSerialNumber,
						meterType);
		BootNotificationResponse response = cServiceClient
				.sendBootNotification(chargeBoxIdentity, request);

		// Update the Control Data
		controlData.setCentralSystemServiceDateAndTime(response
				.getCurrentTime());
		controlData.getConfiguration().setHeartBeatInterval(
				response.getHeartbeatInterval());
		controlData.setRegistrationStatus(response.getStatus());

		return response;

	}

}
