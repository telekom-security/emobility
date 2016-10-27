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

import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;

/**
 * @version 1.0
 * @created 27-Jan-2015 16:46:52
 */
public class Connector {

	private XMLGregorianCalendar timeStamp;
	private ChargePointStatus status;
	private ChargePointErrorCode errorCode;
	private int connectorId;
	private int meterValue;

	public Connector() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param chargeBoxId
	 * @param connectorId
	 * @param timeStamp
	 * @param status
	 * @param errorCode
	 */
	public Connector(int connectorId, XMLGregorianCalendar xmlCal,
			ChargePointStatus chargePointStatus,
			ChargePointErrorCode chargePointErrorCode) {
		this.connectorId = connectorId;
		this.timeStamp = xmlCal;
		this.status = chargePointStatus;
		this.errorCode = chargePointErrorCode;
	}

	public int getConnectorId() {
		return connectorId;
	}

	public XMLGregorianCalendar getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTimeStamp(XMLGregorianCalendar newVal) {
		timeStamp = newVal;
	}

	public ChargePointStatus getStatus() {
		return status;
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
	public void setStatus(String newVal) {
		status = ChargePointStatus.fromValue(newVal);
	}

	public ChargePointErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setErrorCode(String newVal) {
		errorCode = ChargePointErrorCode.fromValue(newVal);
	}

	public int getMeterValue() {
		return meterValue;
	}

	public void setMeterValue(int meterValue) {
		this.meterValue = meterValue;
	}
}// end Connector