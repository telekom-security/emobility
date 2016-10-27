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
import javax.xml.datatype.XMLGregorianCalendar;

import ocpp.cs._2012._06.TransactionData;

/**
 * @version 1.0
 * @created 27-Jan-2015 16:03:37
 */
public class Transaction {

	private int connectorId;
	private String chargeBoxId;
	private String idTag;
	private int meterStart;
	private int meterStop;
	private XMLGregorianCalendar startTimestamp;
	private XMLGregorianCalendar stopTimestamp;
	private int transactionId;
	private int reservationId;
	private TransactionData transactionData;

	public Transaction() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param chargeBoxId
	 * @param connectorId
	 * @param idTag
	 * @param startTimestamp
	 * @param stopTimestamp
	 * @param meterStart
	 */
	public Transaction(String chargeBoxId, int connectorId, String idTag,
			XMLGregorianCalendar startTimestamp,
			XMLGregorianCalendar stopTimestamp, int meterStart) {
		this.chargeBoxId = chargeBoxId;
		this.connectorId = connectorId;
		this.idTag = idTag;
		this.startTimestamp = startTimestamp;
		this.stopTimestamp = stopTimestamp;
		this.meterStart = meterStart;

	}

	/**
	 * 
	 * @param chargeBoxId
	 * @param transactionId
	 * @param connectorId
	 * @param idTag
	 * @param startTimestamp
	 * @param meterStart
	 */
	public Transaction(String chargeBoxId, int transactionId, int connectorId,
			String idTag, XMLGregorianCalendar startTimestamp, int meterStart) {
		this.chargeBoxId = chargeBoxId;
		this.connectorId = connectorId;
		this.idTag = idTag;
		this.startTimestamp = startTimestamp;
		this.meterStart = meterStart;
		this.transactionId = transactionId;
	}

	public String getChargeBoxId() {
		return chargeBoxId;
	}

	public int getConnectorId() {
		return connectorId;
	}

	public int getMeterStart() {
		return meterStart;
	}

	public int getMeterStop() {
		return meterStop;
	}

	public XMLGregorianCalendar getStartTimestamp() {
		return startTimestamp;
	}

	public XMLGregorianCalendar getStopTimestamp() {
		return stopTimestamp;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChargeBoxId(String newVal) {
		chargeBoxId = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setConnectorId(int newVal) {
		connectorId = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterStart(int newVal) {
		meterStart = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMeterStop(int newVal) {
		meterStop = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setStartTimestamp(XMLGregorianCalendar newVal) {
		startTimestamp = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setStopTimestamp(XMLGregorianCalendar newVal) {
		stopTimestamp = newVal;
	}

	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTransactionId(int newVal) {
		transactionId = newVal;
	}

	public int getReservationId() {
		return reservationId;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setReservationId(int newVal) {
		reservationId = newVal;
	}

	public String getIdTag() {
		return idTag;
	}

	public TransactionData getTransactionData() {
		return transactionData;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setIdTag(String newVal) {
		idTag = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTransactionData(TransactionData newVal) {
		transactionData = newVal;
	}

}// end Transaction