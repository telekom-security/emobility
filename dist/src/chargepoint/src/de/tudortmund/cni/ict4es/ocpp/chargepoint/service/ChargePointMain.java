package de.tudortmund.cni.ict4es.ocpp.chargepoint.service;

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

import ocpp.cs._2012._06.BootNotificationResponse;
import ocpp.cs._2012._06.RegistrationStatus;
import de.tudortmund.cni.ict4es.charge.simulation.ChargeBoxControllerImpl;
import de.tudortmund.cni.ict4es.charge.simulation.ChargeSimController;
import de.tudortmund.cni.ict4es.charge.simulation.Utils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData.ChargePointServiceMode;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class ChargePointMain {

	public static void main(String[] args) {

		ChargePoint chargePoint = null;
		ChargeBoxControllerImpl chargeBoxController = null;
		ChargeSimController cpChargeSimController = null;

		String pathAndConfigFile = null;
		String cpEndpointProxyURL = null;
		boolean boot = false;
		boolean startChargeSimulation = false;

		if (args.length > 0) {

			System.out
					.println("\n---------------- Charge Point initial configuration ----------------");

			for (int i = 0; i < args.length; i++) {

				if (args[i].toLowerCase().equals("-c")) {
					pathAndConfigFile = args[i + 1];
					System.out.println("PathToConfigFile:\t -> "
							+ pathAndConfigFile);

				} else if (args[i].toLowerCase().equals("-p")) {
					cpEndpointProxyURL = args[i + 1];
					System.out.println("CPServiceProxyURL:\t -> "
							+ cpEndpointProxyURL);

				} else if (args[i].toLowerCase().equals("-b")) {
					boot = Boolean.valueOf(args[i + 1]);
					System.out.println("SendBootNotification?:\t -> " + boot);

				} else if (args[i].toLowerCase().equals("-s")) {
					startChargeSimulation = Boolean.valueOf(args[i + 1]);
					System.out.println("StartChargeSimulation?:\t -> "
							+ startChargeSimulation);
				}
			}

			System.out
					.println("--------------------------------------------------------------------\n");

			chargePoint = new ChargePoint();
			cpChargeSimController = new ChargeSimController(chargePoint);
			cpChargeSimController.init();
			chargeBoxController = new ChargeBoxControllerImpl(
					cpChargeSimController);
			chargePoint.registerChargeBoxController(chargeBoxController);

			if (chargePoint.init(pathAndConfigFile, cpEndpointProxyURL)) {
				System.out.println("Charge Point initialization -> OK");

				if (chargePoint.startService()) {
					System.out.println("Start Charge Point Service -> OK");

					if (boot) {

						ControlData controlData = chargePoint.getControlData();

						if (controlData.getChargePointServiceMode().equals(
								ChargePointServiceMode.ONLINE)) {

							System.out
									.println("Sending BootNotification Req from chargeBoxId "
											+ controlData
													.getChargeBoxIdentity());
							BootNotificationResponse response = chargePoint
									.doBootNotification(
											controlData.getChargeBoxIdentity(),
											controlData.getChargePointModel(),
											controlData.getChargePointVendor(),
											controlData
													.getChargePointSerialNumber(),
											controlData
													.getChargeBoxSerialNumber(),
											controlData.getFirmwareVersion(),
											controlData.getIccid(), controlData
													.getImsi(), controlData
													.getMeterSerialNumber(),
											controlData.getMeterType());
							System.out
									.println("Received BootNotification Resp -> "
											+ response.getStatus());

							if (response.getStatus().equals(
									RegistrationStatus.ACCEPTED)) {

								List<Connector> connectorList = controlData
										.getConnectors();

								System.out.println("\nInit EVSE Connectors...");

								for (int i = 0; i < connectorList.size(); i++) {

									Connector connector = connectorList.get(i);
									int connectorMeterValue = Utils
											.getRandomValueFromConfigProperty(cpChargeSimController
													.getCpChargeSimControllerConfiguration()
													.getConnectorMeterValue());
									connector
											.setMeterValue(connectorMeterValue);
									System.out
											.println("Sending StatusNotificationReq | ConnectorId: "
													+ connector
															.getConnectorId());
									System.out.println("Meter value: "
											+ connector.getMeterValue());
									chargePoint.doStatusNotification(
											controlData.getChargeBoxIdentity(),
											connector.getConnectorId(),
											connector.getErrorCode(), connector
													.getStatus(), null,
											DateTimeUtils
													.getCurrentDateTimeAsXML(),
											null, null);
								}

								System.out.println("\n");

								System.out
										.println("Charge Point with chargeBoxId "
												+ controlData
														.getChargeBoxIdentity()
												+ " successfully booted, start charge simulation?= "
												+ startChargeSimulation);

								if (startChargeSimulation) {

									cpChargeSimController.startSimulation(
											false, null, null);

								}

							} else {
								System.out
										.println("Charge Point has been NOT accepted by Central System.");
							}

						} else {
							System.out
									.println("Charge Point NOT connected to Central System.");
						}

					}

				} else {
					System.out.println("Start Charge Point Service -> FAILED");
				}

			} else {
				System.out.println("Charge Point initialization -> FAILED");
			}

		}

	}

}
