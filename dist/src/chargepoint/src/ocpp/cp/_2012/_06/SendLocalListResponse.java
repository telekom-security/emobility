package ocpp.cp._2012._06;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Defines the SendLocalList.conf PDU
 * 
 * <p>
 * Java class for SendLocalListResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SendLocalListResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{urn://Ocpp/Cp/2012/06/}UpdateStatus"/>
 *         &lt;element name="hash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendLocalListResponse", propOrder = { "status", "hash" })
public class SendLocalListResponse {

	@XmlElement(required = true)
	protected UpdateStatus status;
	protected String hash;

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link UpdateStatus }
	 * 
	 */
	public UpdateStatus getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link UpdateStatus }
	 * 
	 */
	public void setStatus(UpdateStatus value) {
		this.status = value;
	}

	/**
	 * Gets the value of the hash property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Sets the value of the hash property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setHash(String value) {
		this.hash = value;
	}

}
