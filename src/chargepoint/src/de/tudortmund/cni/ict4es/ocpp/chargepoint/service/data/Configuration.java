package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data;

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

import ocpp.cp._2012._06.KeyValue;
import ocpp.cs._2012._06.MeterValue;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.Constants;

/**
 * @version 1.0
 * @created 27-Jan-2015 16:24:39
 */
public class Configuration {

	private List<KeyValue> keyValueList = new ArrayList<KeyValue>();
	private ArrayList<MeterValue> meterValuesAlignedDataList;
	private ArrayList<MeterValue> stopTxnSampledDataList;
	private ArrayList<MeterValue> stopTxnAlignedDataList;
	private ArrayList<MeterValue> meterValuesSampledDataList;

	public Configuration() {

	}

	public void finalize() throws Throwable {

	}

	public int getBlinkRepeat() {
		return Integer.valueOf(getConfigValueFromKey(Constants.BLINK_REPEAT,
				keyValueList).getValue());
	}

	public int getClockAlignedDataInterval() {
		return Integer
				.valueOf(getConfigValueFromKey(
						Constants.CLOCK_ALIGNED_DATA_INTERVAL, keyValueList)
						.getValue());

	}

	public int getConnectionTimeOut() {
		return Integer.valueOf(getConfigValueFromKey(
				Constants.CONNECTION_TIMEOUT, keyValueList).getValue());
	}

	public int getHeartBeatInterval() {
		return Integer.valueOf(getConfigValueFromKey(
				Constants.HEARTBEAT_INTERVAL, keyValueList).getValue());
	}

	public int getLightIntensity() {
		return Integer.valueOf(getConfigValueFromKey(Constants.LIGHT_INTENSITY,
				keyValueList).getValue());
	}

	public int getMeterValueSampleInterval() {
		return Integer
				.valueOf(getConfigValueFromKey(
						Constants.METER_VALUE_SAMPLE_INTERVAL, keyValueList)
						.getValue());
	}

	public int getResetRetries() {
		return Integer.valueOf(getConfigValueFromKey(Constants.RESET_RETRIES,
				keyValueList).getValue());
	}

	public ArrayList<MeterValue> getMeterValuesSampledDataList() {
		return meterValuesSampledDataList;
	}

	public ArrayList<MeterValue> getStopTxnAlignedDataList() {
		return stopTxnAlignedDataList;
	}

	public ArrayList<MeterValue> getStopTxnSampledDataList() {
		return stopTxnSampledDataList;
	}

	public ArrayList<MeterValue> getMeterValuesAlignedDataList() {
		return meterValuesAlignedDataList;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setBlinkRepeat(int newVal) {
		updateValueOfKey(Constants.BLINK_REPEAT, String.valueOf(newVal),
				keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setClockAlignedDataInterval(int newVal) {
		updateValueOfKey(Constants.CLOCK_ALIGNED_DATA_INTERVAL,
				String.valueOf(newVal), keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setConnectionTimeOut(int newVal) {
		updateValueOfKey(Constants.CONNECTION_TIMEOUT, String.valueOf(newVal),
				keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setHeartBeatInterval(int newVal) {
		updateValueOfKey(Constants.HEARTBEAT_INTERVAL, String.valueOf(newVal),
				keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLightIntensity(int newVal) {
		updateValueOfKey(Constants.LIGHT_INTENSITY, String.valueOf(newVal),
				keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterValuesAlignedDataList(ArrayList<MeterValue> newVal) {
		meterValuesAlignedDataList = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterValueSampleInterval(int newVal) {
		updateValueOfKey(Constants.METER_VALUE_SAMPLE_INTERVAL,
				String.valueOf(newVal), keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setResetRetries(int newVal) {
		updateValueOfKey(Constants.RESET_RETRIES, String.valueOf(newVal),
				keyValueList);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterValuesSampledDataList(ArrayList<MeterValue> newVal) {
		meterValuesSampledDataList = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setStopTxnAlignedDataList(ArrayList<MeterValue> newVal) {
		stopTxnAlignedDataList = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setStopTxnSampledDataList(ArrayList<MeterValue> newVal) {
		stopTxnSampledDataList = newVal;
	}

	/**
	 * 
	 * @param searchKey
	 * @param localConfigList
	 */
	private KeyValue getConfigValueFromKey(String searchKey,
			List<KeyValue> localConfigList) {

		KeyValue keyValue = null;

		for (int i = 0; i < localConfigList.size(); i++) {

			if (localConfigList.get(i).getKey().equals(searchKey)) {
				keyValue = localConfigList.get(i);
				break;
			}

		}

		return keyValue;

	}

	/**
	 * 
	 * @param updateKey
	 * @param updateKeyValue
	 * @param localConfigList
	 */
	private void updateValueOfKey(String updateKey, String updateKeyValue,
			List<KeyValue> localConfigList) {

		KeyValue keyValue = getConfigValueFromKey(updateKey, localConfigList);

		if (keyValue != null) {
			keyValue.setValue(updateKeyValue);
		}

	}

	public List<KeyValue> getKeyValueList() {
		return keyValueList;
	}

	public void setKeyValueList(List<KeyValue> keyValueList) {
		this.keyValueList = keyValueList;
	};

}// end Configuration