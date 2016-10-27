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

import ocpp.cs._2012._06.HeartbeatRequest;
import ocpp.cs._2012._06.HeartbeatResponse;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;

public class HeartbeatHandler extends Thread {

	private static final Logger log = Logger.getLogger(HeartbeatHandler.class
			.getName());

	private CentralSystemServiceClient csServiceClient = null;

	private boolean isRunning = false;
	private int heartbeatInterval = 0;
	private String chargeBoxIdentity;
	private ControlData controlData = null;

	public HeartbeatHandler(ChargePoint chargePointService) {
		csServiceClient = chargePointService.getCentralSystemServiceClient();
		controlData = chargePointService.getControlData();
	}

	@Override
	public void run() {

		HeartbeatRequest request = new HeartbeatRequest();

		while (isRunning) {

			log.info("Heartbeat triggered at chargeBoxIdentity = "
					+ chargeBoxIdentity + " [Next Heartbeat in "
					+ heartbeatInterval + "s]");
			HeartbeatResponse response = csServiceClient.sendHeartbeat(
					chargeBoxIdentity, request);
			controlData.setCentralSystemServiceDateAndTime(response
					.getCurrentTime());

			try {
				Thread.sleep(heartbeatInterval * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void init(String chargeBoxIdentity, int heartbeatInterval) {
		this.chargeBoxIdentity = chargeBoxIdentity;
		this.heartbeatInterval = heartbeatInterval;
	}

	public void startHeartBeat() {
		log.info("Starting HeartBeat Thread [Interval: " + heartbeatInterval
				+ "s]");
		isRunning = true;
		if (!this.isAlive())
			this.start();
	}

	public void stopHeartBeat() {
		log.info("Stopping HeartBeat Thread");
		isRunning = false;
	}

	public void updateHeartbeat(int heartbeatInterv) {
		log.info("Updating HeartBeat Interval [New Interval: "
				+ heartbeatInterv + "s]");
		this.stopHeartBeat();
		this.heartbeatInterval = heartbeatInterv;
		this.startHeartBeat();
	}

	public boolean isRunning() {
		return isRunning;
	}
}