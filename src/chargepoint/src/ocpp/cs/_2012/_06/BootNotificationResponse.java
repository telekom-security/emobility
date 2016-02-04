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
 * Defines the BootNotification.conf PDU
 * 
 * <p>
 * Java class for BootNotificationResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="BootNotificationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{urn://Ocpp/Cs/2012/06/}RegistrationStatus"/>
 *         &lt;element name="currentTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="heartbeatInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationResponse", propOrder = { "status",
		"currentTime", "heartbeatInterval" })
public class BootNotificationResponse {

	@XmlElement(required = true)
	protected RegistrationStatus status;
	@XmlElement(required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar currentTime;
	protected int heartbeatInterval;

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link RegistrationStatus }
	 * 
	 */
	public RegistrationStatus getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link RegistrationStatus }
	 * 
	 */
	public void setStatus(RegistrationStatus value) {
		this.status = value;
	}

	/**
	 * Gets the value of the currentTime property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getCurrentTime() {
		return currentTime;
	}

	/**
	 * Sets the value of the currentTime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setCurrentTime(XMLGregorianCalendar value) {
		this.currentTime = value;
	}

	/**
	 * Gets the value of the heartbeatInterval property.
	 * 
	 */
	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}

	/**
	 * Sets the value of the heartbeatInterval property.
	 * 
	 */
	public void setHeartbeatInterval(int value) {
		this.heartbeatInterval = value;
	}

}
