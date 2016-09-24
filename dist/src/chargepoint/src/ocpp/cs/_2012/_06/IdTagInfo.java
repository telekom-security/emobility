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
 * <p>
 * Java class for IdTagInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="IdTagInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{urn://Ocpp/Cs/2012/06/}AuthorizationStatus"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="parentIdTag" type="{urn://Ocpp/Cs/2012/06/}IdToken" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdTagInfo", propOrder = { "status", "expiryDate",
		"parentIdTag" })
public class IdTagInfo {

	@XmlElement(required = true)
	protected AuthorizationStatus status;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar expiryDate;
	protected String parentIdTag;

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link AuthorizationStatus }
	 * 
	 */
	public AuthorizationStatus getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link AuthorizationStatus }
	 * 
	 */
	public void setStatus(AuthorizationStatus value) {
		this.status = value;
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

}
