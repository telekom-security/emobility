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
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UnitOfMeasure.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="UnitOfMeasure">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Wh"/>
 *     &lt;enumeration value="kWh"/>
 *     &lt;enumeration value="varh"/>
 *     &lt;enumeration value="kvarh"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="kW"/>
 *     &lt;enumeration value="var"/>
 *     &lt;enumeration value="kvar"/>
 *     &lt;enumeration value="Amp"/>
 *     &lt;enumeration value="Volt"/>
 *     &lt;enumeration value="Celsius"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitOfMeasure")
@XmlEnum
public enum UnitOfMeasure {

	@XmlEnumValue("Wh")
	WH("Wh"), @XmlEnumValue("kWh")
	K_WH("kWh"), @XmlEnumValue("varh")
	VARH("varh"), @XmlEnumValue("kvarh")
	KVARH("kvarh"), W("W"), @XmlEnumValue("kW")
	K_W("kW"), @XmlEnumValue("var")
	VAR("var"), @XmlEnumValue("kvar")
	KVAR("kvar"), @XmlEnumValue("Amp")
	AMP("Amp"), @XmlEnumValue("Volt")
	VOLT("Volt"), @XmlEnumValue("Celsius")
	CELSIUS("Celsius");
	private final String value;

	UnitOfMeasure(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static UnitOfMeasure fromValue(String v) {
		for (UnitOfMeasure c : UnitOfMeasure.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
