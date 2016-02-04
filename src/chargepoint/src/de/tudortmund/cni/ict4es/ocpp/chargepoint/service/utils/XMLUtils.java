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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargePointService;

public class XMLUtils {

	private static final Logger log = Logger
			.getLogger(XMLUtils.class.getName());

	private static File loadResource(String pathToResource) {

		URL path = IChargePointService.class
				.getResource("IChargePointService.class");
		String protocolType = path.getProtocol();

		if (protocolType.equalsIgnoreCase(Constants.RESOURCE_PROTOCOL_TYPE_JAR)) {

			StringBuffer b = new StringBuffer(System.getProperty("user.dir"));
			b.append("/resources").append(pathToResource);
			pathToResource = b.toString();
		}

		URL url = XMLUtils.class.getClass().getResource(pathToResource);

		log.info("Loading resource from " + url + ".");

		File file = null;
		try {

			if (protocolType
					.equalsIgnoreCase(Constants.RESOURCE_PROTOCOL_TYPE_JAR)) {

				file = new File(pathToResource);

			} else {

				if (url != null) {
					file = new File(url.toURI());
				} else {
					log.warning("Can NOT load " + pathToResource);
					return null;
				}
			}
		} catch (URISyntaxException e) {
			log.warning("Can NOT load " + pathToResource + "\nException "
					+ e.toString());
			return null;
		}

		return file;

	}

	public static Document loadXML(String pathToFile) {
		Document xml = null;

		// File fileConfig = loadResource(pathToFile);

		File fileConfig = new File(pathToFile);

		if (fileConfig != null) {

			if (fileConfig.exists() || fileConfig.length() > 0) {

				try {

					DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder dBuilder;

					dBuilder = dbFactory.newDocumentBuilder();
					xml = dBuilder.parse(fileConfig);
					xml.getDocumentElement().normalize();

				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		return xml;
	}

	public static Node foundKey(Document xml, String key) {

		Node nNode = null;

		NodeList nList = xml.getDocumentElement().getChildNodes();

		for (int i = 0; i < nList.getLength(); i++) {

			nNode = nList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (nNode.getNodeName().equals(key)) {
					return nNode;
				}
			}
		}
		return nNode;
	}

	public static void updateConfigValue(Document xml, String key, Object value) {

		Node node = foundKey(xml, key);

		if (node != null) {
			node.setTextContent(String.valueOf(value));
			log.info("Updating key: " + key + " to " + String.valueOf(value));
		} else {
			log.warning("Updating key: " + key + " to " + String.valueOf(value)
					+ " failed!");
		}

	}

	public static String getKeyValue(Document xml, String key) {

		String value = "";
		Node node = foundKey(xml, key);

		if (node != null) {
			value = node.getTextContent();
		} else {
			log.warning("Charge Point key: " + key + " NOT FOUND!");
		}

		return value;
	}

	public static void saveXML(String pathToResource, Document xml) {

		String xmlString = null;

		try {

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(xml);
			transformer.transform(source, result);
			xmlString = result.getWriter().toString();

		} catch (TransformerException e) {
			e.printStackTrace();
		}

		File fileAuthorisationsList = loadResource(pathToResource);

		FileWriter writer = null;

		try {
			writer = new FileWriter(fileAuthorisationsList);
			writer.write(xmlString);
		} catch (IOException ex) {
			Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null,
					ex);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		log.info("XML saved!");
	}

	public static void main(String[] args) {

	}

}
