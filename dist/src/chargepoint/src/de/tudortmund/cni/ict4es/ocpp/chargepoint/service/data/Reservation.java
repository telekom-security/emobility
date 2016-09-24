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

/**
 * @version 1.0
 * @created 27-Jan-2015 16:03:37
 */
public class Reservation {

	private int connectorId;
	private int reservationId;
	private String idTag;
	private String chargeBoxId;
	private XMLGregorianCalendar startDatetime;
	private XMLGregorianCalendar expiryDatetime;
	private String parentIdTag;

	public Reservation() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param connectorId
	 * @param reservationId
	 * @param idTag
	 * @param chargeBoxId
	 * @param startDatetime
	 * @param expiryDatetime
	 * @param parentIdTag
	 */
	public Reservation(int connectorId, int reservationId, String idTag,
			String chargeBoxId, XMLGregorianCalendar startDatetime,
			XMLGregorianCalendar expiryDatetime, String parentIdTag) {
		this.connectorId = connectorId;
		this.reservationId = reservationId;
		this.idTag = idTag;
		this.parentIdTag = parentIdTag;
		this.chargeBoxId = chargeBoxId;
		this.startDatetime = startDatetime;
		this.expiryDatetime = expiryDatetime;
	}

	public String getChargeBoxId() {
		return chargeBoxId;
	}

	public XMLGregorianCalendar getExpiryDatetime() {
		return expiryDatetime;
	}

	public String getIdTag() {
		return idTag;
	}

	public int getReservationId() {
		return reservationId;
	}

	public XMLGregorianCalendar getStartDatetime() {
		return startDatetime;
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
	public void setExpiryDatetime(XMLGregorianCalendar newVal) {
		expiryDatetime = newVal;
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
	public void setReservationId(int newVal) {
		reservationId = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setStartDatetime(XMLGregorianCalendar newVal) {
		startDatetime = newVal;
	}

	public int getConnectorId() {
		return connectorId;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setConnectorId(int newVal) {
		connectorId = newVal;
	}

	public String getParentIdTag() {
		return parentIdTag;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setParentIdTag(String newVal) {
		parentIdTag = newVal;
	}
}// end Reservation