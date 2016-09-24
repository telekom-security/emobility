package de.tudortmund.cni.ict4es.charge.simulation;

/**
 * @author Jens Schmutzler <jens.schmutzler@tu-dortmund.de>
 * @author Thomas Grabowski <thomas.grabowski@tu-dortmund.de>
 * @author Mohamad Sbeiti <mohamad.sbeiti@telekom.de>
 * 
 *         This program is free software; you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation; either version 2 of the License, or (at
 *         your option) any later version. For further information see file
 *         COPYING in the top level directory
 * 
 ********************************************************************************
 *         This work is a joint work between Communication Networks Institute
 *         (CNI - Prof. Dr.-Ing. Christian Wietfeld) at Technische Universitaet
 *         Dortmund, Germany and the Deutsche Telekom
 * ********************************************************************************/
public class ChargeSimControllerConfiguration extends PropertyReader {

	public enum Config {

		// Central System Connection
		EV_STATE_OF_CHARGE("ev.state.of.charge"),

		USER_CHARGING_REQUEST_DELAY("user.charging.request.delay"),

		CONNECTOR_METER_VALUE("connector.meter.value");

		private final String text;

		private Config(final String name) {
			text = name;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public ChargeSimControllerConfiguration() {

	}

	public boolean loadProperties(String propertyFileName) {

		try {
			loadPropertyFile(propertyFileName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public String getEVStateOfCharge() {

		String res = "";

		try {
			res = getPropertyValue(Config.EV_STATE_OF_CHARGE.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public String getUSerChargingRequestDelay() {

		String res = "";

		try {
			res = getPropertyValue(Config.USER_CHARGING_REQUEST_DELAY
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public String getConnectorMeterValue() {

		String res = "";

		try {
			res = getPropertyValue(Config.CONNECTOR_METER_VALUE.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

}
