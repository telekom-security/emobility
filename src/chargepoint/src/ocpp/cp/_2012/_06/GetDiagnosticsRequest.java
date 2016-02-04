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
 * Defines the GetDiagnostics.req PDU
 * 
 * <p>
 * Java class for GetDiagnosticsRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GetDiagnosticsRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="stopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="retries" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="retryInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDiagnosticsRequest", propOrder = { "location", "startTime",
		"stopTime", "retries", "retryInterval" })
public class GetDiagnosticsRequest {

	@XmlElement(required = true)
	@XmlSchemaType(name = "anyURI")
	protected String location;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar startTime;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar stopTime;
	protected Integer retries;
	protected Integer retryInterval;

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * Gets the value of the startTime property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}

	/**
	 * Sets the value of the startTime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setStartTime(XMLGregorianCalendar value) {
		this.startTime = value;
	}

	/**
	 * Gets the value of the stopTime property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getStopTime() {
		return stopTime;
	}

	/**
	 * Sets the value of the stopTime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setStopTime(XMLGregorianCalendar value) {
		this.stopTime = value;
	}

	/**
	 * Gets the value of the retries property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRetries() {
		return retries;
	}

	/**
	 * Sets the value of the retries property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRetries(Integer value) {
		this.retries = value;
	}

	/**
	 * Gets the value of the retryInterval property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRetryInterval() {
		return retryInterval;
	}

	/**
	 * Sets the value of the retryInterval property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRetryInterval(Integer value) {
		this.retryInterval = value;
	}

}
