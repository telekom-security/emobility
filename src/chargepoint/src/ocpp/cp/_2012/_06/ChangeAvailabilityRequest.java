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
 * Defines the ChangeAvailability.req PDU
 * 
 * <p>
 * Java class for ChangeAvailabilityRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ChangeAvailabilityRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{urn://Ocpp/Cp/2012/06/}AvailabilityType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeAvailabilityRequest", propOrder = { "connectorId",
		"type" })
public class ChangeAvailabilityRequest {

	protected int connectorId;
	@XmlElement(required = true)
	protected AvailabilityType type;

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
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link AvailabilityType }
	 * 
	 */
	public AvailabilityType getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link AvailabilityType }
	 * 
	 */
	public void setType(AvailabilityType value) {
		this.type = value;
	}

}
