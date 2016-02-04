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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import ocpp.cs._2012._06.AuthorizationStatus;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.MeterValue;
import ocpp.cs._2012._06.MeterValue.Value;
import ocpp.cs._2012._06.StartTransactionResponse;
import ocpp.cs._2012._06.UnitOfMeasure;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;

public class ChargeSimThread implements Runnable {

	private static final Logger log = Logger.getLogger(ChargeSimThread.class
			.getName());

	private ChargePoint chargePoint;

	private int batteryCapacity = 20000;
	private double stateOfCharge = 0;
	private double chargingPower;
	private double totalReceivedEnergy;

	private boolean processChargeLoop = false;
	private long startTime = 0;
	private List<MeterValue> meterValueList = null;

	private String chargeBoxId;
	private String idTag;
	private Connector connector;
	private int transactionId;
	private int requestDelay;
	private Message message = null;

	public ChargeSimThread(ChargePoint chargePoint, Connector connector,
			String idTag, double stateOfCharge, int requestDelay,
			Message message) {
		this.chargePoint = chargePoint;
		this.chargeBoxId = chargePoint.getControlData().getChargeBoxIdentity();
		this.connector = connector;
		this.idTag = idTag;
		this.stateOfCharge = stateOfCharge;
		this.requestDelay = requestDelay;
		this.message = message;
	}

	public boolean preCharging(ChargePoint chargePoint) {

		try {
			Thread.sleep(requestDelay * 60000);
		} catch (InterruptedException e) {

			synchronized (message) {
				message.setText("SIM: Charge simulation stopped");
				message.notify();
			}

			return false;
		}

		boolean preCharge = false;

		totalReceivedEnergy = 0;

		if (connector.getStatus().equals(ChargePointStatus.AVAILABLE)) {

			System.out.println("SIM: ConnectorId " + connector.getConnectorId()
					+ " is available, start user authorisation for idTag "
					+ idTag);

			AuthorizationStatus authorizationStatus = chargePoint
					.doAuthorization(chargeBoxId, idTag);

			if (authorizationStatus.equals(AuthorizationStatus.ACCEPTED)) {

				System.out.println("SIM: User with idTag " + idTag + " -> "
						+ authorizationStatus);
				System.out.println("SIM: Start transaction on chargeBoxId "
						+ chargeBoxId + " and connectorId "
						+ connector.getConnectorId());

				StartTransactionResponse startTransactionResponse = chargePoint
						.doStartTransaction(chargeBoxId,
								connector.getConnectorId(), idTag,
								connector.getMeterValue(),
								DateTimeUtils.getCurrentDateTimeAsXML(), -1);

				if (startTransactionResponse != null) {

					if (startTransactionResponse.getIdTagInfo().getStatus()
							.equals(AuthorizationStatus.CONCURRENT_TX)) {
						transactionId = startTransactionResponse
								.getTransactionId();
						return true;
					}

				}

			} else if (authorizationStatus
					.equals(AuthorizationStatus.CONCURRENT_TX)) {

				synchronized (message) {
					message.setText("SIM: User with idTag "
							+ idTag
							+ " is already loading -> start new charging simulation");
					message.notify();
				}

				return false;

			} else {

				synchronized (message) {
					message.setText("SIM: User with idTag " + idTag
							+ " is invalid -> start new charging simulation");
					message.notify();
				}

				return false;

			}

		} else {

			synchronized (message) {
				message.setText("SIM: ConnectorId "
						+ connector.getConnectorId() + " is not available");
				message.notify();
			}

			return false;

		}
		return preCharge;

	}

	@Override
	public void run() {
		startChargeSimulation();
	}

	public void startChargeSimulation() {

		processChargeLoop = true;

		if (!preCharging(chargePoint)) {
			stopCharging();
			return;
		}

		startTime = Calendar.getInstance().getTimeInMillis();
		log.info(">>> Start Charging at " + new Date(startTime));

		double receivedEnergyInInterval = 0;

		while (processChargeLoop) {

			// Charging Power in Watt
			chargingPower = 240;
			receivedEnergyInInterval = (chargingPower / 60);

			totalReceivedEnergy += receivedEnergyInInterval;
			stateOfCharge += receivedEnergyInInterval;

			System.out
					.println("\nSIM: total received energy in Wh since start time: "
							+ totalReceivedEnergy);
			System.out.println("SIM: received energy in intervall: "
					+ receivedEnergyInInterval);
			System.out.println("SIM: state of charge: " + stateOfCharge);
			System.out.println("SIM: conn. meter value: "
					+ connector.getMeterValue() + "\n");

			Value value = new Value();
			value.setUnit(UnitOfMeasure.W);
			value.setValue(String.valueOf(receivedEnergyInInterval));

			MeterValue meterValue = new MeterValue();
			meterValue.setTimestamp(DateTimeUtils.getCurrentDateTimeAsXML());
			meterValue.getValue().add(value);

			meterValueList = new ArrayList<MeterValue>();
			meterValueList.add(meterValue);

			chargePoint.doMeterValues(chargeBoxId, connector.getConnectorId(),
					transactionId, meterValueList);

			try {
				Thread.sleep(60 * 1000);
			} catch (Exception e) {

				synchronized (message) {
					message.setText("SIM: Charge simulation stopped");
					message.notify();
				}

				return;
			}

			if (stateOfCharge >= batteryCapacity) {
				stopCharging();
				chargePoint.doStopTransaction(chargeBoxId,
						(int) connector.getMeterValue(),
						DateTimeUtils.getCurrentDateTimeAsXML(), transactionId,
						idTag, null, connector.getConnectorId());

				synchronized (message) {
					message.setText("SIM: Battery is full stop charging process");
					message.notify();
				}

			}

		}

	}

	public void stopCharging() {
		processChargeLoop = false;
	}

}
