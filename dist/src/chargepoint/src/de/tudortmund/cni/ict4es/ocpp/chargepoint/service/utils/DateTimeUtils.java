package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeUtils {

	private static DatatypeFactory factory;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");

	static {
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Unable to create an XML datatype factory", e);
		}
	}

	/**
	 * Converts Calendar to XMLGregorianCalendar.
	 */
	public static XMLGregorianCalendar calToXML(Calendar cal) {

		XMLGregorianCalendar xgc = null;
		xgc = factory.newXMLGregorianCalendar((GregorianCalendar) cal);

		return xgc;
	}

	/**
	 * Converts a Date to XMLGregorianCalendar.
	 */
	private static XMLGregorianCalendar dateToXML(Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return factory.newXMLGregorianCalendar(gc);
		}
	}

	/**
	 * get the current time as XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getCurrentDateTimeAsXML() {
		XMLGregorianCalendar xgc = null;
		xgc = factory.newXMLGregorianCalendar((GregorianCalendar) Calendar
				.getInstance());
		return xgc;
	}

	/**
	 * Converts a String of pattern "dd-MM-yyyy HH:mm" to Date.
	 */
	private static Date stringToDate(String str) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Converts a XMLGregorianCalendar and format to String.
	 */
	public static String xmlToString(XMLGregorianCalendar xmlCal) {
		Date date = xmltoDate(xmlCal);
		String newstring = simpleDateFormat.format(date);
		return newstring;
	}

	/**
	 * Converts a XMLGregorianCalendar to Date.
	 */
	public static Date xmltoDate(XMLGregorianCalendar xml) {
		if (xml == null) {
			return null;
		}
		return xml.toGregorianCalendar().getTime();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * Converts a String to XMLGregorianCalendar.
	 */
	public static XMLGregorianCalendar StringToXML(String string) {
		Date date = stringToDate(string);
		return dateToXML(date);
	}

	/**
	 * Converts a String to XMLGregorianCalendar.
	 */
	public static XMLGregorianCalendar stringToXml(String dateTimeString) {
		Date date = stringToDate(dateTimeString);
		return dateToXML(date);
	}

	/**
	 * Converts a Date to XMLGregorianCalendar.
	 */
	public static XMLGregorianCalendar dateToXml(Date date) {
		DateFormat dateFormat = simpleDateFormat;
		String strDate = dateFormat.format(date);
		XMLGregorianCalendar xmlDate = factory.newXMLGregorianCalendar(strDate);
		return xmlDate;

	}

}
