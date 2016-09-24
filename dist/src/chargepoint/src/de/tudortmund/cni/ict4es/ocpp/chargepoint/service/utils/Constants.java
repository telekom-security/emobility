package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils;

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
public interface Constants {

	// Charge Point Configuration Paths and Files

	public static final String BASE_CONFIG_DIR = "/opt/emobility/conf/";
	public static final String CP_INFO = "chargepoint_info.xml";
	public static final String CP_CONFIGURATION = "configuration.xml";
	public static final String CP_LOCAL_AUTHORISATION_LIST = "local_authorisation_list.xml";

	// Charge Point Information
	public static final String CP_WEBSERVICE_IPv4 = "ChargePointWebServiceIPv4";

	public static final String CS_WEBSERVICE_URL = "CentralSystemWebServiceURL";
	public static final String CS_WEBSERVICE_URI = "CentralSystemWebServiceURI";
	public static final String CS_WEBSERVICE_NAME = "CentralSystemWebServiceName";

	public static final String CHARGEBOX_IDENTITY = "ChargeBoxIdentity";
	public static final String CP_MODEL = "ChargePointModel";
	public static final String CP_VENDOR = "ChargePointVendor";
	public static final String CP_SERIAL_NUMBER = "ChargePointSerialNumber";
	public static final String CHARGEBOX_SERIAL_NUMBER = "ChargeBoxSerialNumber";
	public static final String FIRMWARE_VERSION = "FirmwareVersion";
	public static final String ICCID = "Iccid";
	public static final String IMSI = "Imsi";
	public static final String METER_SERIAL_NUMBER = "MeterSerialNumber";
	public static final String METER_TYPE = "MeterType";
	public static final String NUMBER_OF_CONNECTORS = "NumberOfConnectors";

	// Charge Point Configuration
	public static final String HEARTBEAT_INTERVAL = "HeartBeatInterval";
	public static final String CONNECTION_TIMEOUT = "ConnectionTimeOut";
	public static final String RESET_RETRIES = "ResetRetries";
	public static final String BLINK_REPEAT = "BlinkRepeat";
	public static final String LIGHT_INTENSITY = "LightIntensity";
	public static final String METER_VALUE_SAMPLE_INTERVAL = "MeterValueSampleInterval";
	public static final String CLOCK_ALIGNED_DATA_INTERVAL = "ClockAlignedDataInterval";
	public static final String METER_VALUES_SAMPLED_DATA = "MeterValuesSampledData";
	public static final String METER_VALUES_ALIGNED_DATA = "MeterValuesAlignedData";
	public static final String STOP_TXN_SAMPLED_DATA = "StopTxnSampledData";
	public static final String STOP_TXN_ALIGNED_DATA = "StopTxnAlignedData";

	// ChargeBoxController Status
	public static final String REJECTED = "Rejected";
	public static final String ACCEPTED = "Accepted";

	public static final String RESOURCE_PROTOCOL_TYPE_FILE = "file";
	public static final String RESOURCE_PROTOCOL_TYPE_JAR = "jar";
}
