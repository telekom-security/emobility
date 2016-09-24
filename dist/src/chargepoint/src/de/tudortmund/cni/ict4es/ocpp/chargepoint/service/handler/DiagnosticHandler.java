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

import ocpp.cp._2012._06.GetDiagnosticsRequest;
import ocpp.cp._2012._06.GetDiagnosticsResponse;
import ocpp.cs._2012._06.DiagnosticsStatus;
import ocpp.cs._2012._06.DiagnosticsStatusNotificationRequest;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;

public class DiagnosticHandler {

	private static final Logger log = Logger.getLogger(DiagnosticHandler.class
			.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private CentralSystemServiceClient csServiceClient = null;

	public DiagnosticHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = chargePointService.getCentralSystemServiceClient();
	}

	public GetDiagnosticsResponse handleGetDiagnosticsReq(
			String chargeBoxIdentity, GetDiagnosticsRequest request) {

		String url = request.getLocation();

		log.info("GetDiagnosticsReq called at ChargeBoxID " + chargeBoxIdentity);
		log.info("URL: " + url);

		// Preparing response
		// TODO: Diagnostics information not yet defined
		GetDiagnosticsResponse response = new GetDiagnosticsResponse();

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
		if (request.getStartTime() != null) {
			request.getStartTime();
			log.info("StartTime: " + request.getStartTime());
		}

		// optional
		if (request.getStopTime() != null) {
			request.getStopTime();
			log.info("StopTime: " + request.getStopTime());
		}

		log.info("GetDiagnosticsResp...");
		return response;

	}

	public void processDiagnosticStatusNotification(String chargeBoxIdentity,
			DiagnosticsStatus diagnosticsStatus) {

		log.info("Process DiagnsticStatusNotification");

		controlData = chargePointService.getControlData();

		DiagnosticsStatusNotificationRequest request = csServiceClient
				.prepareDiagnosticStatusNotificationReq(chargeBoxIdentity,
						diagnosticsStatus);
		csServiceClient.sendDiagnosticStatusNotification(chargeBoxIdentity,
				request);

	}

}
