package de.tudortmund.cni.ict4es.config.cp;
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
import java.util.List;

public class ChargePointItem {
	
	private List<ConfigItem> chargePointItemConfigList;

	private ConfigItem chargePointServiceURLConfigItem;
	
	private ConfigItem centralSystemWebServiceURLConfigItem;
	private ConfigItem centralSystemWebServiceURIConfigItem;
	private ConfigItem centralSystemWebServiceNameConfigItem;
	
	private ConfigItem numberOfConnectorsConfigItem;
	
	private ConfigItem chargeBoxIdentityConfigItem; 
	private ConfigItem chargePointModelConfigItem; 
	private ConfigItem chargePointVendorConfigItem;
	private ConfigItem chargePointSerialNumberConfigItem;
	private ConfigItem chargeBoxSerialNumberConfigItem;
	private ConfigItem firmwareVersionConfigItem;
	private ConfigItem iccidConfigItem; 
	private ConfigItem imsiConfigItem;
	private ConfigItem meterSerialNumberConfigItem;
	private ConfigItem meterTypeConfigItem;	

	public ChargePointItem() {}
	
	public ChargePointItem(String chargePointServiceIpv4, String centralSystemWebServiceURL, String centralSystemWebServiceURI, String centralSystemWebServiceName, String numberOfConnectors, String chargeBoxIdentity, String chargeBoxSerialNumber, String chargePointModel, String chargePointVendor, String chargePointSerialNumber, String firmwareVersion,
	String iccid, String imsi, String meterSerialNumber, String meterType){
		
		chargePointItemConfigList = new ArrayList<ConfigItem>();
		
		chargePointServiceURLConfigItem = new ConfigItem("ChargePointWebServiceIPv4", chargePointServiceIpv4);
		chargePointItemConfigList.add(chargePointServiceURLConfigItem);
		
		centralSystemWebServiceURLConfigItem = new ConfigItem("CentralSystemWebServiceURL", centralSystemWebServiceURL);
		centralSystemWebServiceURIConfigItem = new ConfigItem("CentralSystemWebServiceURI", centralSystemWebServiceURI);
		centralSystemWebServiceNameConfigItem = new ConfigItem("CentralSystemWebServiceName", centralSystemWebServiceName);
		
		numberOfConnectorsConfigItem = new ConfigItem("NumberOfConnectors", numberOfConnectors);

		chargeBoxIdentityConfigItem = new ConfigItem("ChargeBoxIdentity", chargeBoxIdentity);
		chargePointModelConfigItem = new ConfigItem("ChargePointModel", chargePointModel); 
		chargePointVendorConfigItem= new ConfigItem("ChargePointVendor", chargePointVendor);
		chargePointSerialNumberConfigItem = new ConfigItem("ChargePointSerialNumber", chargePointSerialNumber);
		chargeBoxSerialNumberConfigItem = new ConfigItem("ChargeBoxSerialNumber", chargeBoxSerialNumber);
		
		firmwareVersionConfigItem = new ConfigItem("FirmwareVersion", firmwareVersion);
		iccidConfigItem = new ConfigItem("Iccid", iccid);
		imsiConfigItem = new ConfigItem("Imsi", imsi);
		meterSerialNumberConfigItem = new ConfigItem("MeterSerialNumber", meterSerialNumber);
		meterTypeConfigItem = new ConfigItem("MeterType", meterType);
		
		chargePointItemConfigList.add(centralSystemWebServiceURLConfigItem);
		chargePointItemConfigList.add(centralSystemWebServiceURIConfigItem);
		chargePointItemConfigList.add(centralSystemWebServiceNameConfigItem);
		
		chargePointItemConfigList.add(numberOfConnectorsConfigItem);
		
		chargePointItemConfigList.add(chargeBoxIdentityConfigItem);
		chargePointItemConfigList.add(chargePointModelConfigItem);
		chargePointItemConfigList.add(chargePointVendorConfigItem);
		chargePointItemConfigList.add(chargePointSerialNumberConfigItem);
		chargePointItemConfigList.add(chargeBoxSerialNumberConfigItem);
		
		chargePointItemConfigList.add(firmwareVersionConfigItem);
		chargePointItemConfigList.add(iccidConfigItem);
		chargePointItemConfigList.add(imsiConfigItem);
		chargePointItemConfigList.add(meterSerialNumberConfigItem);
		chargePointItemConfigList.add(meterTypeConfigItem);

	}
	
	
	public List<ConfigItem> getConfigList(){
		return chargePointItemConfigList;
	}
	
	public class ConfigItem{
		
		private String configName;
		private String configValue;
		
		public ConfigItem(String configName, String configValue){
			this.configName = configName;
			this.configValue = configValue;
		}

		public String getConfigName() {
			return configName;
		}

		public void setConfigName(String configName) {
			this.configName = configName;
		}

		public String getConfigValue() {
			return configValue;
		}

		public void setConfigValue(String configValue) {
			this.configValue = configValue;
		}
		
	}


	public ConfigItem getChargePointServiceURLConfigItem() {
		return chargePointServiceURLConfigItem;
	}

	public ConfigItem getCentralSystemWebServiceURLConfigItem() {
		return centralSystemWebServiceURLConfigItem;
	}

	public ConfigItem getCentralSystemWebServiceURIConfigItem() {
		return centralSystemWebServiceURIConfigItem;
	}


	public ConfigItem getChargeBoxIdentityConfigItem() {
		return chargeBoxIdentityConfigItem;
	}

	public ConfigItem getChargePointModelConfigItem() {
		return chargePointModelConfigItem;
	}

	public ConfigItem getChargePointVendorConfigItem() {
		return chargePointVendorConfigItem;
	}

	public ConfigItem getChargePointSerialNumberConfigItem() {
		return chargePointSerialNumberConfigItem;
	}

	public ConfigItem getChargeBoxSerialNumberConfigItem() {
		return chargeBoxSerialNumberConfigItem;
	}

	public ConfigItem getFirmwareVersionConfigItem() {
		return firmwareVersionConfigItem;
	}

	public ConfigItem getIccidConfigItem() {
		return iccidConfigItem;
	}

	public ConfigItem getImsiConfigItem() {
		return imsiConfigItem;
	}

	public ConfigItem getMeterSerialNumberConfigItem() {
		return meterSerialNumberConfigItem;
	}

	public ConfigItem getMeterTypeConfigItem() {
		return meterTypeConfigItem;
	}
	
}
