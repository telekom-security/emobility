/******************************************************************
 * PropertyReader.java 
 *
 *
 * Author: redbeard
 * Date: Apr 19, 2013
 * E-Mail: jan.radon@tu-dortmund.de
 * 
 * 
 *****************************************************************/
package de.tudortmund.cni.ict4es.config.utils;
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
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

//XXX taken from Common-Utils project
public class PropertyReader {

	protected String propertyFileName = null;
	protected Properties prop = null;

	public PropertyReader() {
	}
	
	public PropertyReader(String propertyFileName) throws Exception {
		if (propertyFileName == null) {
			throw new Exception("Property File name must be given");
		}
		this.propertyFileName = propertyFileName;
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(this.propertyFileName));
			prop = new Properties();
			prop.load(stream);
			stream.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public void loadPropertyFile(String propertyFileName) throws Exception{
		if (propertyFileName == null) {
			throw new Exception("Property File name must be given");
		}
		this.propertyFileName = propertyFileName;
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(this.propertyFileName));
			prop = new Properties();
			prop.load(stream);
			stream.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	//XXX handling reading from any inputstream e.g. resources in jar file
	public PropertyReader(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new Exception("InputStream must be given");
		}
		try {
			BufferedInputStream stream = new BufferedInputStream(inputStream);
			prop = new Properties();
			prop.load(stream);
			stream.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public String getPropertyFileName() {
		return propertyFileName;
	}

	public Properties getProp() {
		return prop;
	}

	public String getPropertyValue(String key) throws Exception  {
		if (key == null) {
			throw new Exception("Key must be given!");
		}
		if (!prop.containsKey(key)) {
			throw new Exception("Key " + key + " is not present in config file: " + propertyFileName);
		}
		String value = prop.getProperty(key);
		if (isEmpty(value)) {
			throw new Exception("Key " + key + " is present in config file: " + propertyFileName
					+ " but has no value.");
		}
		return value;
	}

	public int getPropertyValueInt(String key) throws Exception  {

		String value = getPropertyValue(key);
		int valueInt = 0;
		try {
			valueInt = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new Exception("Value for Key : " + key + " must be a number but is: " + value);
		}
		return valueInt;
	}

	public boolean getPropertyValueBool(String key) throws Exception {

		String value = getPropertyValue(key);
		boolean valueBoolean = false;
		try {
			valueBoolean = Boolean.valueOf(value);
		} catch (NumberFormatException e) {
			throw new Exception("Value for Key : " + key + " must be a boolean value (true,false) but is: "
					+ value);
		}
		return valueBoolean;
	}

	public List<String> getPropertyListValue(String key, String separator) throws Exception {
		if (key == null) {
			throw new Exception("Key must be given!");
		}
		if (separator == null) {
			throw new Exception("separator must be given!");
		}

		String value = getPropertyValue(key);
		try {
			if (!value.contains(separator)) {
				throw new Exception("String: " + value + " does not contain separator  (" + separator + ")");
			}
			String[] values = value.split(separator);
			List<String> list = new ArrayList<String>(values.length);
			for (String s : values) {
				list.add(s);
			}
			return list;
		} catch (PatternSyntaxException e) {
			throw new Exception(e);
		}

	}
	
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "PropertyReader [propertyFileName=" + propertyFileName + ", prop=" + prop + "]";
	}

}
