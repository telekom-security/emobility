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
 * <p>
 * Java class for AuthorisationData complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorisationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idTag" type="{urn://Ocpp/Cp/2012/06/}IdToken"/>
 *         &lt;element name="idTagInfo" type="{urn://Ocpp/Cp/2012/06/}IdTagInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorisationData", propOrder = { "idTag", "idTagInfo" })
public class AuthorisationData {

	@XmlElement(required = true)
	protected String idTag;
	protected IdTagInfo idTagInfo;

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
	 * Gets the value of the idTagInfo property.
	 * 
	 * @return possible object is {@link IdTagInfo }
	 * 
	 */
	public IdTagInfo getIdTagInfo() {
		return idTagInfo;
	}

	/**
	 * Sets the value of the idTagInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link IdTagInfo }
	 * 
	 */
	public void setIdTagInfo(IdTagInfo value) {
		this.idTagInfo = value;
	}

}
