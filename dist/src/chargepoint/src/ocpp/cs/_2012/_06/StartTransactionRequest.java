package ocpp.cs._2012._06;

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
 * Defines the StartTransaction.req PDU
 * 
 * <p>
 * Java class for StartTransactionRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="StartTransactionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idTag" type="{urn://Ocpp/Cs/2012/06/}IdToken"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="meterStart" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reservationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartTransactionRequest", propOrder = { "connectorId",
		"idTag", "timestamp", "meterStart", "reservationId" })
public class StartTransactionRequest {

	protected int connectorId;
	@XmlElement(required = true)
	protected String idTag;
	@XmlElement(required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar timestamp;
	protected int meterStart;
	protected Integer reservationId;

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
	 * Gets the value of the timestamp property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the value of the timestamp property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setTimestamp(XMLGregorianCalendar value) {
		this.timestamp = value;
	}

	/**
	 * Gets the value of the meterStart property.
	 * 
	 */
	public int getMeterStart() {
		return meterStart;
	}

	/**
	 * Sets the value of the meterStart property.
	 * 
	 */
	public void setMeterStart(int value) {
		this.meterStart = value;
	}

	/**
	 * Gets the value of the reservationId property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getReservationId() {
		return reservationId;
	}

	/**
	 * Sets the value of the reservationId property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setReservationId(Integer value) {
		this.reservationId = value;
	}

}
