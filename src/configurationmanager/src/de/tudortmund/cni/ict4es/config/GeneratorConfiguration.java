package de.tudortmund.cni.ict4es.config;
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

import de.tudortmund.cni.ict4es.config.utils.PropertyReader;

public class GeneratorConfiguration extends PropertyReader {

	private static final Logger log = Logger.getLogger(GeneratorConfiguration.class.getName());

	public enum Config {

		// Central System Connection
		CENTRALSYSTEM_DB_URL("centralsystem.database.url"),
		
		CENTRALSYSTEM_DB_USER("centralsystem.database.user"),
		
		CENTRALSYSTEM_DB_PASSWORD("centralsystem.database.password"),
		
		// Central System Charge Point Management
		CENTRALSYSYTEM_ENABLE_CHARGEPOINT_IMPORT("centralsystem.enable.chargepoint.import"),

		CENTRALSYSYTEM_DELETE_CHARGEPOINT_DATA("centralsystem.delete.chargepoint.data"),
		
		START_CONFIG_INDEX("start.config.index"),

		NUMBER_OF_CHARGEPOINTS("number.of.chargpoints"),

		CHARGEPOINT_WEBSERVICE_IPv4("chargepoint.webservice.ipv4"),
		
		CHARGEPOINT_WEBSERVICE_PORT("chargepoint.webservice.port"),
		
		CHARGEPOINT_WEBSERVICE_NAME("chargepoint.webservice.name"),

		CENTRALSYSTEM_WEBSERVICE_URL("centralsystem.webservice.url"),
		
		CENTRALSYSTEM_WEBSERVICE_URI("centralsystem.webservice.uri"),
		
		CENTRALSYSTEM_WEBSERVICE_NAME("centralsystem.webservice.name"),
		
		NUMBER_OF_CONNECTORS("number.of.connectors"),
		
		CHARGEBOX_IDENTITY("chargebox.identity"),
		
		CHARGEBOX_SERIALNUMBER("chargebox.serialnumber"),
		
		CHARGEPOINT_MODEL("chargepoint.model"),
		
		CHARGEPOINT_VENDOR("chargepoint.vendor"),
		
		CHARGEPOINT_SERIALNUMBER("chargepoint.serialnumber"),
		
		FIRMWARE_VERSION("firmware.version"),
		
		ICCID("iccid"),
		
		IMSI("imsi"),
		
		METER_SERIALNUMBER("meter.serialnumber"),
		
		METER_TYPE("meter.type"),
		
		// Central System User Management
		CENTRALSYSYTEM_ENABLE_USER_IMPORT("centralsystem.enable.user.import"),

		CENTRALSYSYTEM_DELETE_USER_DATA("centralsystem.delete.user.data"),
		
		NUMBER_OF_USERS("number.of.users"),
		
		USER_IDTAG_LENGTH("user.idtag.length");
		
		private final String text;

		private Config(final String name) {
			text = name;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public GeneratorConfiguration() {

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
	
	public String getCentralSystemDBUrl() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_DB_URL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getCentralSystemDBUser() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_DB_USER.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getCentralSystemDBPassword() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_DB_PASSWORD.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public boolean csEnableCPImport() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.CENTRALSYSYTEM_ENABLE_CHARGEPOINT_IMPORT.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public boolean csDeleteCPData() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.CENTRALSYSYTEM_DELETE_CHARGEPOINT_DATA.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public boolean csEnableUserImport() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.CENTRALSYSYTEM_ENABLE_USER_IMPORT.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public boolean csDeleteUserData() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.CENTRALSYSYTEM_DELETE_USER_DATA.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	

	public int getStartConfigIndex() {

		int res = 0;

		try {
			res = getPropertyValueInt(Config.START_CONFIG_INDEX.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public int getNumberOfChargePoints() {

		int res = 0;

		try {
			res = getPropertyValueInt(Config.NUMBER_OF_CHARGEPOINTS.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public String getChargePointWebServiceIPv4() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_WEBSERVICE_IPv4.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public String getChargePointWebServicePort() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_WEBSERVICE_PORT.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getChargePointWebServiceName() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_WEBSERVICE_NAME.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getCentralSystemWebServiceURL() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_WEBSERVICE_URL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getCentralSystemWebServiceURI() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_WEBSERVICE_URI.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getCentralSystemWebServiceName() {

		String res = "";

		try {
			res = getPropertyValue(Config.CENTRALSYSTEM_WEBSERVICE_NAME.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getNumberOfConnectors() {

		String res = "";

		try {
			res = getPropertyValue(Config.NUMBER_OF_CONNECTORS.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public String getChargeBoxIdentity() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEBOX_IDENTITY.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	
	public String getChargeBoxSerialnumber() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEBOX_SERIALNUMBER.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public String getChargePointModel() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_MODEL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public String getChargePointVendor() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_VENDOR.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getChargePointSerialNumber() {

		String res = "";

		try {
			res = getPropertyValue(Config.CHARGEPOINT_SERIALNUMBER.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getFirmwareVersion() {

		String res = "";

		try {
			res = getPropertyValue(Config.FIRMWARE_VERSION.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	
	public boolean getICCID() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.ICCID.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	

	public boolean getIMSI() {

		boolean res = false;

		try {
			res = getPropertyValueBool(Config.IMSI.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getMeterSerialNumber() {

		String res = "";

		try {
			res = getPropertyValue(Config.METER_SERIALNUMBER.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public String getMeterType() {

		String res = "";

		try {
			res = getPropertyValue(Config.METER_TYPE.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public int getUserIdTagLength() {

		int res = 0;

		try {
			res = getPropertyValueInt(Config.USER_IDTAG_LENGTH.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public int getNumberOfUsers() {

		int res = 0;

		try {
			res = getPropertyValueInt(Config.NUMBER_OF_USERS.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}




}
