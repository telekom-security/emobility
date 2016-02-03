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
import java.io.File;
import java.util.Iterator;
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
import org.w3c.dom.Element;

import de.tudortmund.cni.ict4es.config.cp.ChargePointItem;
import de.tudortmund.cni.ict4es.config.cp.ChargePointItem.ConfigItem;
import de.tudortmund.cni.ict4es.config.user.LocalAuthorisationList;
import de.tudortmund.cni.ict4es.config.user.UserItem;

public class XMLUtils {

	private static final Logger log = Logger.getLogger(XMLUtils.class.getName());

	public static void createLocalAuthorisationListXMLFile(LocalAuthorisationList localAuthorisationList) {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("AuthorizationList");
			doc.appendChild(rootElement);

			Element versionElement = createElement(doc, "Version", String.valueOf(localAuthorisationList.getVersion()));
			rootElement.appendChild(versionElement);

			Element userListElement = doc.createElement("UserList");
			rootElement.appendChild(userListElement);

			for (Iterator<UserItem> iterator = localAuthorisationList.getUserList().iterator(); iterator.hasNext();) {

				Element userElement = doc.createElement("User");
				userListElement.appendChild(userElement);

				UserItem userItem = (UserItem) iterator.next();

				Element elIdTag = createElement(doc, "IdTag", userItem.getUserIdTag());
				userElement.appendChild(elIdTag);
				Element elParentIdTag = createElement(doc, "ParentIdTag", userItem.getParentIdTag());
				userElement.appendChild(elParentIdTag);
				Element elExpireDate = createElement(doc, "ExpireDate", userItem.getExpireDate());
				userElement.appendChild(elExpireDate);
				Element elStatus = createElement(doc, "Status", userItem.getStatus());
				userElement.appendChild(elStatus);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("generated/local_authorisation_list.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

	public static void createChargePointConfigXMLFile(ChargePointItem chargePointItem, int index) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("ChargePoint");
			doc.appendChild(rootElement);

			for (Iterator<ConfigItem> iterator = chargePointItem.getConfigList().iterator(); iterator.hasNext();) {
				ConfigItem configItem = (ConfigItem) iterator.next();
				Element el = createElement(doc, configItem.getConfigName(), configItem.getConfigValue());
				rootElement.appendChild(el);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("generated/chargepoint_" + index + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private static Element createElement(Document doc, String elName, String elValue) {
		Element el = doc.createElement(elName);
		el.appendChild(doc.createTextNode(elValue));
		return el;
	}

	// public static void saveXML(String fileName, Document xml) {
	//
	// String xmlString = null;
	//
	// try {
	//
	// Transformer transformer = TransformerFactory.newInstance().newTransformer();
	// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	// StreamResult result = new StreamResult(new StringWriter());
	// DOMSource source = new DOMSource(xml);
	// transformer.transform(source, result);
	// xmlString = result.getWriter().toString();
	//
	// } catch (TransformerException e) {
	// e.printStackTrace();
	// }
	//
	// FileWriter writer = null;
	//
	// try {
	//
	// writer = new FileWriter(fileName);
	// writer.write(xmlString);
	//
	// } catch (IOException ex) {
	// Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
	//
	// } finally {
	// try {
	// writer.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// log.info("XML saved!");
	// }

}
