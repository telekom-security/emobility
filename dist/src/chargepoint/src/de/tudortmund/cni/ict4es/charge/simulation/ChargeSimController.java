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
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.User;

public class ChargeSimController implements Runnable {

	private final static String CONFIG_BASE_DIR = "/opt/emobility/conf/";
	private final static String CONFIG_FILE_NAME = "charge_simulation_configuration.properties";

	private ChargeSimControllerConfiguration chargeSimControllerConfiguration = null;

	private ChargePoint chargePoint = null;
	private ControlData controlData = null;
	private Message message = new Message();
	private ChargeSimThread chargeSimThread = null;

	public ChargeSimController(ChargePoint chargePoint) {
		this.chargePoint = chargePoint;

		Thread waiter = new Thread(this);
		waiter.setName("Wait_Thread");
		waiter.start();
	}

	public boolean init() {

		if (!loadConfiguration(CONFIG_BASE_DIR + CONFIG_FILE_NAME)) {
			return false;

		} else {
			System.out
					.println("\n---------------- Charge Point Charge Sim Configuration -------------");
			System.out.println("EV state of charge\t -> "
					+ chargeSimControllerConfiguration.getEVStateOfCharge()
					+ " Watt");
			System.out.println("USER request delay\t -> "
					+ chargeSimControllerConfiguration
							.getUSerChargingRequestDelay() + " min");
			System.out.println("EVSE conn. meter value\t -> "
					+ chargeSimControllerConfiguration.getConnectorMeterValue()
					+ " Watt");
			System.out
					.println("-------------------------------------------------------------------\n");
			return true;
		}

	}

	private boolean loadConfiguration(String propertyFileName) {
		chargeSimControllerConfiguration = new ChargeSimControllerConfiguration();
		return chargeSimControllerConfiguration
				.loadProperties(propertyFileName);
	}

	public void startSimulation(boolean isRemoteStartTransaction,
			Connector connector, String idTag) {

		controlData = chargePoint.getControlData();
		controlData.getLocalAuthorisationList();

		String chargeBoxId = controlData.getChargeBoxIdentity();

		int stateOfCharge = Utils
				.getRandomValueFromConfigProperty(chargeSimControllerConfiguration
						.getEVStateOfCharge());
		int requestDelay = Utils
				.getRandomValueFromConfigProperty(chargeSimControllerConfiguration
						.getUSerChargingRequestDelay());

		String selectedIdTag = "";
		Connector selectedConnector = null;

		// Remote Request
		if (!isRemoteStartTransaction) {
			selectedIdTag = ((User) Utils
					.getRandomItemFromList(chargePoint.getControlData()
							.getLocalAuthorisationList().getUserList()))
					.getIdTag();
			selectedConnector = (Connector) Utils
					.getRandomItemFromList(chargePoint.getControlData()
							.getConnectors());

			// Local Request
		} else {
			selectedIdTag = idTag;
			selectedConnector = connector;
		}

		System.out
				.println("\nStart to simulate charging process on chargeBoxId "
						+ chargeBoxId + " in " + requestDelay
						+ " min with values:");
		System.out.println("USER ID tag\t\t -> " + selectedIdTag);
		System.out.println("USER request delay\t -> " + requestDelay + " min");
		System.out
				.println("EV state of charge\t -> " + stateOfCharge + " Watt");
		System.out.println("EVSE chargebox ID\t -> " + chargeBoxId);
		System.out.println("EVSE conn. ID\t\t -> "
				+ selectedConnector.getConnectorId());
		System.out.println("EVSE conn. state\t -> "
				+ selectedConnector.getStatus());
		System.out.println("EVSE conn. meter value\t -> "
				+ selectedConnector.getMeterValue() + " Watt\n");

		chargeSimThread = new ChargeSimThread(chargePoint, selectedConnector,
				selectedIdTag, stateOfCharge, requestDelay, message);

		Thread c = new Thread(chargeSimThread);
		c.setName("ChargeSim_Thread");
		c.start();

	}

	public void stopSimulation() {
		System.out.println("SIM: Stopping current charging simulation");
		chargeSimThread.stopCharging();
	}

	public ChargeSimControllerConfiguration getCpChargeSimControllerConfiguration() {
		return chargeSimControllerConfiguration;
	}

	@Override
	public void run() {

		while (true) {

			synchronized (message) {
				try {
					message.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(message.getText());
				startSimulation(false, null, null);
			}

		}

	}
}
