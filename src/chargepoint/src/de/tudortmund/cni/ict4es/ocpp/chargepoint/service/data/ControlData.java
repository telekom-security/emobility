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

import javax.xml.datatype.XMLGregorianCalendar;

import ocpp.cs._2012._06.RegistrationStatus;

/**
 * @version 1.0
 * @created 27-Jan-2015 16:03:37
 */
public class ControlData {

	public enum ChargePointServiceMode {
		ONLINE, OFFLINE
	}

	private String chargePointWebServiceIPv4;
	private String chargePointWebServiceProxyURL;

	private String centralSystemWebServiceURL;
	private String centralSystemWebServiceURI;
	private String centralSystemWebServiceName;

	private String numberOfConnectors;

	private String chargeBoxIdentity;
	private String chargePointModel;
	private String chargePointVendor;
	private String chargePointSerialNumber;
	private String chargeBoxSerialNumber;
	private String firmwareVersion;
	private String iccid;
	private String imsi;
	private String meterSerialNumber;
	private String meterType;
	private String diagnosticsStatus;
	private XMLGregorianCalendar centralSystemServiceDateAndTime;

	private ChargePointServiceMode chargePointServiceMode;
	private RegistrationStatus registrationStatus;

	private LocalAuthorisationList localAuthorisationList = new LocalAuthorisationList();
	private Configuration Configuration = new Configuration();
	private ArrayList<Connector> connectors = new ArrayList<Connector>();
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

	public ControlData() {

	}

	public void finalize() throws Throwable {

	}

	public String getChargePointWebServiceIPv4() {
		return chargePointWebServiceIPv4;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargePointWebServiceIPv4(String newVal) {
		chargePointWebServiceIPv4 = newVal;
	}

	public String getCentralSystemWebServiceURL() {
		return centralSystemWebServiceURL;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCentralSystemWebServiceURL(String newVal) {
		centralSystemWebServiceURL = newVal;
	}

	public String getCentralSystemWebServiceURI() {
		return centralSystemWebServiceURI;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCentralSystemWebServiceURI(String newVal) {
		centralSystemWebServiceURI = newVal;
	}

	public String getCentralSystemWebServiceName() {
		return centralSystemWebServiceName;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCentralSystemWebServiceName(String newVal) {
		centralSystemWebServiceName = newVal;
	}

	public String getChargeBoxIdentity() {
		return chargeBoxIdentity;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargeBoxIdentity(String newVal) {
		chargeBoxIdentity = newVal;
	}

	public String getChargeBoxSerialNumber() {
		return chargeBoxSerialNumber;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargeBoxSerialNumber(String newVal) {
		chargeBoxSerialNumber = newVal;
	}

	public String getChargePointModel() {
		return chargePointModel;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargePointModel(String newVal) {
		chargePointModel = newVal;
	}

	public String getChargePointSerialNumber() {
		return chargePointSerialNumber;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargePointSerialNumber(String newVal) {
		chargePointSerialNumber = newVal;
	}

	public String getChargePointVendor() {
		return chargePointVendor;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargePointVendor(String newVal) {
		chargePointVendor = newVal;
	}

	public String getIccid() {
		return iccid;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setIccid(String newVal) {
		iccid = newVal;
	}

	public String getImsi() {
		return imsi;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setImsi(String newVal) {
		imsi = newVal;
	}

	public String getMeterType() {
		return meterType;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterType(String newVal) {
		meterType = newVal;
	}

	public String getDiagnosticsStatus() {
		return diagnosticsStatus;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDiagnosticsStatus(String newVal) {
		diagnosticsStatus = newVal;
	}

	public XMLGregorianCalendar getCentralSystemServiceDateAndTime() {
		return centralSystemServiceDateAndTime;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCentralSystemServiceDateAndTime(XMLGregorianCalendar newVal) {
		centralSystemServiceDateAndTime = newVal;
	}

	public Configuration getConfiguration() {
		return Configuration;
	}

	public ArrayList<Connector> getConnectors() {
		return connectors;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setConnectors(ArrayList<Connector> newVal) {
		connectors = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setReservations(ArrayList<Reservation> newVal) {
		reservations = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTransactions(ArrayList<Transaction> newVal) {
		transactions = newVal;
	}

	public LocalAuthorisationList getLocalAuthorisationList() {
		return localAuthorisationList;
	}

	public String getMeterSerialNumber() {
		return meterSerialNumber;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterSerialNumber(String newVal) {
		meterSerialNumber = newVal;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setFirmwareVersion(String newVal) {
		firmwareVersion = newVal;
	}

	public ChargePointServiceMode getChargePointServiceMode() {
		return chargePointServiceMode;
	}

	public void setChargePointServiceMode(
			ChargePointServiceMode chargePointServiceMode) {
		this.chargePointServiceMode = chargePointServiceMode;
	}

	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getNumberOfConnectors() {
		return numberOfConnectors;
	}

	public void setNumberOfConnectors(String numberOfConnectors) {
		this.numberOfConnectors = numberOfConnectors;
	}

	public String getChargePointWebServiceProxyIPv4() {
		return chargePointWebServiceProxyURL;
	}

	public void setChargePointWebServiceProxyURL(
			String chargePointWebServiceProxyURL) {
		this.chargePointWebServiceProxyURL = chargePointWebServiceProxyURL;
	}

}// end ControlData