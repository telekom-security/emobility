package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler;

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
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ocpp.cp._2012._06.ChangeConfigurationRequest;
import ocpp.cp._2012._06.ChangeConfigurationResponse;
import ocpp.cp._2012._06.ConfigurationStatus;
import ocpp.cp._2012._06.GetConfigurationRequest;
import ocpp.cp._2012._06.GetConfigurationResponse;
import ocpp.cp._2012._06.KeyValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Configuration;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.Constants;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.XMLUtils;

public class ConfigurationHandler {

	private static final Logger log = Logger
			.getLogger(ConfigurationHandler.class.getName());

	private Document configXML;
	private ChargePoint chargePointService;

	public ConfigurationHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
	}

	public boolean init(String configPath) {

		Configuration configuration = chargePointService.getControlData()
				.getConfiguration();

		configXML = XMLUtils.loadXML(configPath);

		if (configXML != null) {

			NodeList nList = configXML.getElementsByTagName("ConfigItem");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String key = eElement.getAttribute("key");
					String isReadOnly = eElement.getAttribute("readonly");
					String value = eElement.getElementsByTagName("Value")
							.item(0).getTextContent();

					KeyValue keyValueItem = new KeyValue();
					keyValueItem.setKey(key);
					keyValueItem.setValue(value);
					keyValueItem.setReadonly(Boolean.parseBoolean(isReadOnly));
					configuration.getKeyValueList().add(keyValueItem);

				}
			}

			log.info("Loading ChargePoint configuration SUCCESSFUL.");
			return true;

		} else {
			log.info("Loading ChargePoint configuration FAILED -> "
					+ configPath + " not existing?");
			return false;
		}

	}

	public GetConfigurationResponse handleGetConfigReq(
			String chargeBoxIdentity, GetConfigurationRequest request) {

		log.info("GetConfigurationReq called at ChargeBoxID "
				+ chargeBoxIdentity);

		// Preparing the response
		GetConfigurationResponse response = new GetConfigurationResponse();

		// Loading current configuration from control data
		Configuration configuration = chargePointService.getControlData()
				.getConfiguration();

		// If requested list of keys = 0 -> return all charge point configs
		if (request.getKey().size() == 0) {

			log.info("Requested key list is empty --> sending all charge point configs");

			if (configuration.getKeyValueList() != null) {
				response.getConfigurationKey().addAll(
						configuration.getKeyValueList());
			}

			// other: get the requested keys
			// differentiate between known unknown keys
		} else if (request.getKey().size() > 0) {

			log.info("Requested key list not empty");

			for (String requestedKey : request.getKey()) {

				KeyValue keyValueItem = getKeyValueItem(requestedKey,
						configuration.getKeyValueList());

				if (keyValueItem != null) {
					response.getConfigurationKey().add(keyValueItem);
				} else {
					response.getUnknownKey().add(requestedKey);
				}
			}

		}

		log.info("GetConfigurationResp...");
		return response;
	}

	public ChangeConfigurationResponse handleChangeConfigReq(
			String chargeBoxIdentity, ChangeConfigurationRequest request) {

		String key = request.getKey();
		String value = request.getValue();

		log.info("ChangeConfigurationReq called at ChargeBoxID "
				+ chargeBoxIdentity);
		log.info("Key: " + request.getKey() + " | Value: " + request.getValue());

		// Preparing the response
		ChangeConfigurationResponse response = new ChangeConfigurationResponse();
		ConfigurationStatus status = ConfigurationStatus.REJECTED;

		status = updateConfigKeyValue(key, value);

		response.setStatus(status);
		log.info("ChangeConfigurationResp | Key=Value: " + key + "=" + value
				+ " | ConfigurationStatus: " + status);
		return response;
	}

	public ConfigurationStatus updateConfigKeyValue(String reqItemKey,
			String reqItemValue) {

		ConfigurationStatus status = ConfigurationStatus.REJECTED;

		// Load current configuration
		Configuration configuration = chargePointService.getControlData()
				.getConfiguration();
		List<KeyValue> configList = configuration.getKeyValueList();

		if (configList != null) {

			KeyValue keyValueItemToUpdate = getKeyValueItem(reqItemKey,
					configList);

			if (keyValueItemToUpdate != null) {

				if (!keyValueItemToUpdate.isReadonly()) {

					if (reqItemKey.equals(Constants.HEARTBEAT_INTERVAL)) {

						// Update key
						keyValueItemToUpdate.setValue(reqItemValue);
						chargePointService.getHeartbeatHandler()
								.updateHeartbeat(
										Integer.valueOf(keyValueItemToUpdate
												.getValue()));
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey.equals(Constants.CONNECTION_TIMEOUT)) {

						keyValueItemToUpdate.setValue(reqItemValue);
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey.equals(Constants.RESET_RETRIES)) {

						keyValueItemToUpdate.setValue(reqItemValue);
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey.equals(Constants.BLINK_REPEAT)) {

						keyValueItemToUpdate.setValue(reqItemValue);
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey.equals(Constants.LIGHT_INTENSITY)) {

						keyValueItemToUpdate.setValue(reqItemValue);
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey
							.equals(Constants.METER_VALUE_SAMPLE_INTERVAL)) {

						keyValueItemToUpdate.setValue(reqItemValue);
						status = ConfigurationStatus.ACCEPTED;

					} else if (reqItemKey
							.equals(Constants.CLOCK_ALIGNED_DATA_INTERVAL)) {

						keyValueItemToUpdate.setValue(reqItemValue);

					} else if (reqItemKey
							.equals(Constants.METER_VALUES_SAMPLED_DATA)) {

						keyValueItemToUpdate.setValue(reqItemValue);

					} else if (reqItemKey
							.equals(Constants.METER_VALUES_ALIGNED_DATA)) {

						keyValueItemToUpdate.setValue(reqItemValue);

					} else if (reqItemKey
							.equals(Constants.STOP_TXN_SAMPLED_DATA)) {

						keyValueItemToUpdate.setValue(reqItemValue);

					} else if (reqItemKey
							.equals(Constants.STOP_TXN_ALIGNED_DATA)) {

					}
				} else {
					status = ConfigurationStatus.REJECTED;
				}

			} else {
				status = ConfigurationStatus.NOT_SUPPORTED;
			}

		}

		return status;
	}

	public KeyValue getKeyValueItem(String searchKey,
			List<KeyValue> localConfigList) {

		KeyValue keyValue = null;

		for (int i = 0; i < localConfigList.size(); i++) {

			if (localConfigList.get(i).getKey().equals(searchKey)) {
				keyValue = localConfigList.get(i);
				break;
			}

		}

		return keyValue;

	}

	public void saveCurrentConfigurationToXML() {

		log.info("Saving Charge Point configuration");

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}

		List<KeyValue> currentConfigList = chargePointService.getControlData()
				.getConfiguration().getKeyValueList();

		// root elements
		Document xmlDoc = docBuilder.newDocument();
		Element rootElement = xmlDoc.createElement("Configurations");
		xmlDoc.appendChild(rootElement);

		for (KeyValue item : currentConfigList) {

			Element configItemElement = xmlDoc.createElement("ConfigItem");
			configItemElement.setAttribute("key", item.getKey());
			configItemElement.setAttribute("readonly",
					String.valueOf(item.isReadonly()));

			Element valueElement = xmlDoc.createElement("Value");
			valueElement.setTextContent(item.getValue());
			configItemElement.appendChild(valueElement);

			rootElement.appendChild(configItemElement);

		}

		XMLUtils.saveXML(
				Constants.BASE_CONFIG_DIR + Constants.CP_CONFIGURATION, xmlDoc);

	}

}
