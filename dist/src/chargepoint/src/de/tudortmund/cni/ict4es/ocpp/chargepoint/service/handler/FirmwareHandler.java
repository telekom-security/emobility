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

import ocpp.cp._2012._06.UpdateFirmwareRequest;
import ocpp.cp._2012._06.UpdateFirmwareResponse;
import ocpp.cs._2012._06.FirmwareStatus;
import ocpp.cs._2012._06.FirmwareStatusNotificationRequest;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;

public class FirmwareHandler {

	private static final Logger log = Logger.getLogger(FirmwareHandler.class
			.getName());

	private ChargePoint chargePointService;
	private CentralSystemServiceClient cServiceClient = null;

	public FirmwareHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		cServiceClient = chargePointService.getCentralSystemServiceClient();
	}

	public UpdateFirmwareResponse handleUpdateFirmwareReq(
			String chargeBoxIdentity, UpdateFirmwareRequest request) {

		log.info("UpdateFirmwareReq called at chargeBoxID: "
				+ chargeBoxIdentity + ", URL: " + request.getLocation());

		// optional
		if (request.getRetries() != null) {
			request.getRetries();
			log.info("Retries: " + request.getRetries());
		}

		// optional
		if (request.getRetryInterval() != null) {
			request.getRetryInterval();
			log.info("RetryInterval: " + request.getRetryInterval());
		}

		// optional
		if (request.getRetrieveDate() != null) {
			request.getRetrieveDate();
			log.info("RetrieveDate: " + request.getRetrieveDate());
		}

		UpdateFirmwareResponse response = new UpdateFirmwareResponse();
		log.info("UpdateFirmwareResp...");
		return response;

	}

	public void processFirmwareStatusNotification(String chargeBoxIdentity,
			FirmwareStatus firmwareStatus) {

		log.info("Process FirmwareStatusNotification");

		FirmwareStatusNotificationRequest request = cServiceClient
				.prepareFirmwareStatusNotificationReq(chargeBoxIdentity,
						firmwareStatus);
		cServiceClient.sendFirmwareStatusNotification(chargeBoxIdentity,
				request);

	}
}
