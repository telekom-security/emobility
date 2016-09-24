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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Defines the ReserveNow.req PDU
 * 
 * <p>
 * Java class for ReserveNowRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ReserveNowRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idTag" type="{urn://Ocpp/Cp/2012/06/}IdToken"/>
 *         &lt;element name="parentIdTag" type="{urn://Ocpp/Cp/2012/06/}IdToken" minOccurs="0"/>
 *         &lt;element name="reservationId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReserveNowRequest", propOrder = { "connectorId", "expiryDate",
		"idTag", "parentIdTag", "reservationId" })
public class ReserveNowRequest {

	protected int connectorId;
	@XmlElement(required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar expiryDate;
	@XmlElement(required = true)
	protected String idTag;
	protected String parentIdTag;
	protected int reservationId;

	/**
	 * Gets the value of the connectorId property.
	 * 
	 */
	public int getConnectorId() {
		return connectorId;
	}

	/**
	 * Sets the value of the connectorId property.
	 * 
	 */
	public void setConnectorId(int value) {
		this.connectorId = value;
	}

	/**
	 * Gets the value of the expiryDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the value of the expiryDate property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setExpiryDate(XMLGregorianCalendar value) {
		this.expiryDate = value;
	}

	/**
	 * Gets the value of the idTag property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIdTag() {
		return idTag;
	}

	/**
	 * Sets the value of the idTag property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIdTag(String value) {
		this.idTag = value;
	}

	/**
	 * Gets the value of the parentIdTag property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParentIdTag() {
		return parentIdTag;
	}

	/**
	 * Sets the value of the parentIdTag property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParentIdTag(String value) {
		this.parentIdTag = value;
	}

	/**
	 * Gets the value of the reservationId property.
	 * 
	 */
	public int getReservationId() {
		return reservationId;
	}

	/**
	 * Sets the value of the reservationId property.
	 * 
	 */
	public void setReservationId(int value) {
		this.reservationId = value;
	}

}
