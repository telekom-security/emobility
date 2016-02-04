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
 * @created 27-Jan-2015 16:03:00
 */
public class User {

	private String idTag;
	private String parentIdTag;
	private XMLGregorianCalendar expiryDate;
	private String authorizationStatus;

	public User() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param idTag
	 * @param parentIdTag
	 * @param expiryDate
	 * @param idTagStatus
	 */
	public User(String idTag, String parentIdTag,
			XMLGregorianCalendar expiryDate, String authorizationStatus) {
		this.idTag = idTag;
		this.parentIdTag = parentIdTag;
		this.expiryDate = expiryDate;
		this.authorizationStatus = authorizationStatus;
	}

	public XMLGregorianCalendar getExpiryDate() {
		return expiryDate;
	}

	public String getIdTag() {
		return idTag;
	}

	public String getIdTagStatus() {
		return authorizationStatus;
	}

	public String getParentIdTag() {
		return parentIdTag;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setExpiryDate(XMLGregorianCalendar newVal) {
		expiryDate = newVal;
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
	public void setIdTagStatus(String newVal) {
		authorizationStatus = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setParentIdTag(String newVal) {
		parentIdTag = newVal;
	}
}// end User