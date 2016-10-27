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

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ocpp.cp._2012._06.AuthorisationData;
import ocpp.cp._2012._06.AuthorizationStatus;
import ocpp.cp._2012._06.ClearCacheRequest;
import ocpp.cp._2012._06.ClearCacheResponse;
import ocpp.cp._2012._06.ClearCacheStatus;
import ocpp.cp._2012._06.GetLocalListVersionRequest;
import ocpp.cp._2012._06.GetLocalListVersionResponse;
import ocpp.cp._2012._06.SendLocalListRequest;
import ocpp.cp._2012._06.SendLocalListResponse;
import ocpp.cp._2012._06.UpdateStatus;
import ocpp.cp._2012._06.UpdateType;
import ocpp.cs._2012._06.AuthorizeRequest;
import ocpp.cs._2012._06.AuthorizeResponse;
import ocpp.cs._2012._06.IdTagInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.LocalAuthorisationList;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.User;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.Constants;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.InputUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.XMLUtils;

public class AuthorisationHandler {

	private static final Logger log = Logger
			.getLogger(AuthorisationHandler.class.getName());

	private ChargePoint chargePointService;

	private CentralSystemServiceClient csServiceClient = null;

	private LocalAuthorisationList localAuthorisationList;

	private ControlData controlData = null;

	public AuthorisationHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = this.chargePointService
				.getCentralSystemServiceClient();
	}

	public boolean init(String path) {
		if (loadAuthorizationList(path)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean loadAuthorizationList(String path) {

		clearLocalAuthorisationList();

		Document xmlDoc = XMLUtils.loadXML(path);

		if (xmlDoc != null) {

			Node versionNode = xmlDoc.getElementsByTagName("Version").item(0);

			updateLocalAuthorisationListVersion(Integer.valueOf(versionNode
					.getTextContent()));

			NodeList usersList = xmlDoc.getElementsByTagName("User");

			if (usersList != null && usersList.getLength() > 0) {

				for (int i = 0; i < usersList.getLength(); i++) {

					Node node = usersList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {

						User user = new User();

						String parentIdTag = "";
						String expireDate = "";
						String status = "";
						XMLGregorianCalendar cal = null;

						Element e = (Element) node;
						NodeList nodeList = e.getElementsByTagName("IdTag");
						String idTag = nodeList.item(0).getChildNodes().item(0)
								.getNodeValue();

						user.setIdTag(idTag);

						nodeList = e.getElementsByTagName("ParentIdTag");
						if (nodeList.item(0).getChildNodes().item(0) != null) {
							parentIdTag = nodeList.item(0).getChildNodes()
									.item(0).getNodeValue();
							user.setParentIdTag(parentIdTag);
						}

						nodeList = e.getElementsByTagName("ExpireDate");
						if (nodeList.item(0).getChildNodes().item(0) != null) {
							expireDate = nodeList.item(0).getChildNodes()
									.item(0).getNodeValue();
							cal = DateTimeUtils.stringToXml(expireDate);
							user.setExpiryDate(cal);
						}

						nodeList = e.getElementsByTagName("Status");
						if (nodeList.item(0).getChildNodes().item(0) != null) {
							status = nodeList.item(0).getChildNodes().item(0)
									.getNodeValue();
							user.setIdTagStatus(status);
						}

						addUser(user);

					}
				}

			}

			log.info("Loading LocalAuthorisationList SUCCESSFUL. List version: "
					+ getLocalAuthorisationListVersion()
					+ " with "
					+ getLocalAuthorisationListUserList().size() + " user(s).");
			return true;

		} else {
			log.info("Loading LocalAuthorisationList FAILED -> LocalAuthorisationList not existing!");
			return false;
		}

	}

	public ClearCacheResponse handleClearCacheReq(String chargeBoxIdentity,
			ClearCacheRequest parameters) {

		log.info("ClearCacheReq called at ChargeBoxID " + chargeBoxIdentity);

		ClearCacheStatus status = ClearCacheStatus.REJECTED;

		if (getLocalAuthorisationListVersion() != -1) {
			clearLocalAuthorisationList();
			status = ClearCacheStatus.ACCEPTED;
		} else {
			status = ClearCacheStatus.REJECTED;
		}

		ClearCacheResponse response = new ClearCacheResponse();
		log.info("ClearCacheResp | ClearCacheStatus: " + status);
		response.setStatus(status);
		return response;
	}

	public GetLocalListVersionResponse handleGetLocalListVersionReq(
			String chargeBoxIdentity, GetLocalListVersionRequest parameters) {

		log.info("GetLocalListVersionReq called at ChargeBoxID "
				+ chargeBoxIdentity);

		int localList = getLocalAuthorisationListVersion();

		// Preparing the response
		GetLocalListVersionResponse reponse = new GetLocalListVersionResponse();

		log.info("GetLocalListVersionResp | Listversion: " + localList);
		reponse.setListVersion(localList);
		return reponse;

	}

	public SendLocalListResponse handleSendLocalListReq(
			String chargeBoxIdentity, SendLocalListRequest request) {

		// TODO: Authorization list to be defined
		UpdateType updateType = request.getUpdateType();
		int listVersion = request.getListVersion();
		List<AuthorisationData> updateList = request
				.getLocalAuthorisationList();

		log.info("SendLocalListReq called at ChargeBoxID " + chargeBoxIdentity
				+ ", UpdateType: " + updateType);

		// Preparing the response
		SendLocalListResponse response = new SendLocalListResponse();
		UpdateStatus status = UpdateStatus.FAILED;

		if (updateType.equals(UpdateType.FULL)) {
			fullLocalAuthorizationListUpdate(listVersion, updateList);
			status = UpdateStatus.ACCEPTED;

		} else if (updateType.equals(UpdateType.DIFFERENTIAL)) {

			if (listVersion == getLocalAuthorisationListVersion()) {
				differentialLocalAuthorizationListUpdate(updateList);
				status = UpdateStatus.ACCEPTED;
			} else {
				status = UpdateStatus.VERSION_MISMATCH;
			}

		}

		log.info("SendLocalListResp | UpdateStatus: " + status);
		response.setStatus(status);
		return response;
	}

	public void fullLocalAuthorizationListUpdate(int listVersion,
			List<AuthorisationData> list) {

		log.info("Starting fullLocalAuthorizationListUpdate");

		clearLocalAuthorisationList();

		int reqListLength = list.size();

		updateLocalAuthorisationListVersion(listVersion);

		for (int i = 0; i < reqListLength; i++) {

			String idTag = list.get(i).getIdTag();
			XMLGregorianCalendar expire = list.get(i).getIdTagInfo()
					.getExpiryDate();

			String parent = list.get(i).getIdTagInfo().getParentIdTag();
			AuthorizationStatus status = list.get(i).getIdTagInfo().getStatus();

			addUser(idTag, parent, expire, status);
		}

		saveLocalAuthorizationList();
	}

	/**
	 * 
	 * @param listVersion
	 * @param list
	 */
	public void differentialLocalAuthorizationListUpdate(
			List<AuthorisationData> reqAuthorizationList) {

		log.info("Starting differentialLocalAuthorizationListUpdate "
				+ reqAuthorizationList.size());

		int reqListLength = reqAuthorizationList.size();

		for (int i = 0; i < reqListLength; i++) {

			AuthorisationData reqAuthorisationData = reqAuthorizationList
					.get(i);

			// Find local user
			User user = getLocalUserData(reqAuthorisationData.getIdTag());

			// Delete existing user
			if (reqAuthorisationData.getIdTagInfo() == null && user != null) {
				deleteUser(user);

				// Update existing user
			} else if (user != null) {
				updateUser(user, reqAuthorisationData);

				// Add new user
			} else if (user == null
					&& reqAuthorisationData.getIdTagInfo() != null) {
				addUser(reqAuthorisationData);
			}

		}

		saveLocalAuthorizationList();

	}

	public void saveLocalAuthorizationList() {

		log.info("Saving Charge Point local authorisationlist");

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}

		List<User> localUserList = getLocalAuthorisationListUserList();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("AuthorizationList");
		doc.appendChild(rootElement);

		// Version elements
		Element versionEl = doc.createElement("Version");
		versionEl.setTextContent(String
				.valueOf(getLocalAuthorisationListVersion()));
		rootElement.appendChild(versionEl);

		// List elements (users)
		Element listEl = doc.createElement("Users");
		rootElement.appendChild(listEl);

		int length = localUserList.size();

		for (int i = 0; i < length; i++) {

			String idTag = localUserList.get(i).getIdTag();
			String parent = localUserList.get(i).getParentIdTag();
			XMLGregorianCalendar cal = localUserList.get(i).getExpiryDate();
			AuthorizationStatus status = AuthorizationStatus
					.fromValue(localUserList.get(i).getIdTagStatus());

			// User element
			Element userEl = doc.createElement("User");
			listEl.appendChild(userEl);

			// IdTag elements
			Element idTagEl = doc.createElement("IdTag");
			idTagEl.setTextContent(idTag);
			userEl.appendChild(idTagEl);

			// ParentIdTag elements
			Element parentIdTagEl = doc.createElement("ParentIdTag");
			if (InputUtils.isNullOrEmpty(parent))
				parentIdTagEl.setTextContent("");
			else
				parentIdTagEl.setTextContent(parent);

			userEl.appendChild(parentIdTagEl);

			// ExpireDate elements
			Element expireEl = doc.createElement("ExpireDate");

			if (cal == null)
				expireEl.setTextContent("");
			else
				expireEl.setTextContent(DateTimeUtils.xmlToString(cal));

			userEl.appendChild(expireEl);

			// IdTagStatus elements
			Element statusEl = doc.createElement("Status");
			if (InputUtils.isNullOrEmpty(status.toString()))
				statusEl.setTextContent("");
			else
				statusEl.setTextContent(status.value());

			userEl.appendChild(statusEl);

			listEl.appendChild(userEl);

		}

		try {
			XMLUtils.saveXML(Constants.BASE_CONFIG_DIR
					+ Constants.CP_LOCAL_AUTHORISATION_LIST, doc);
		} catch (Exception e) {
			log.warning("LocalAuthorisationList has not been saved to file");
		}

	}

	private void addUser(User user) {
		getLocalAuthorisationListUserList().add(user);

	}

	private void addUser(String idTag, String parentIdTag,
			XMLGregorianCalendar expireDate,
			AuthorizationStatus authorizationStatus) {
		getLocalAuthorisationListUserList().add(
				new User(idTag, parentIdTag, expireDate, authorizationStatus
						.value()));

	}

	public ocpp.cs._2012._06.AuthorizationStatus processAuthorization(
			String chargeBoxIdentity, String idTag) {

		ocpp.cs._2012._06.AuthorizationStatus authorizationStatus = ocpp.cs._2012._06.AuthorizationStatus.INVALID;

		// Find the user in the localAuthorisationList
		User user = getLocalUserData(idTag);

		// Prepare and send AuthorizeRequest to Central System
		AuthorizeRequest authorizeReq = csServiceClient
				.prepareAuthorizationReq(chargeBoxIdentity, idTag);
		AuthorizeResponse authorizeResp = csServiceClient.sendAuthorize(
				chargeBoxIdentity, authorizeReq);
		authorizationStatus = authorizeResp.getIdTagInfo().getStatus();

		// User find --> update user data with response informations from
		// central system
		if (user != null) {
			updateUser(user, authorizeResp.getIdTagInfo());

			// User not find --> create and add new user to
			// localAuthorisationList
		} else {

			addUser(idTag, authorizeResp.getIdTagInfo());
		}

		log.info("AuthorizeResponse, idTag: " + idTag + ", status: "
				+ authorizationStatus.value());
		return authorizationStatus;
	}

	private void addUser(AuthorisationData authorisationData) {

		String idTag = authorisationData.getIdTag();
		XMLGregorianCalendar expireDate = null;
		String parentIdTag = "";
		AuthorizationStatus status = null;

		if (authorisationData.getIdTagInfo().getExpiryDate() != null) {
			expireDate = authorisationData.getIdTagInfo().getExpiryDate();
		}

		if (authorisationData.getIdTagInfo().getParentIdTag() != null) {
			parentIdTag = authorisationData.getIdTagInfo().getParentIdTag();
		}

		if (authorisationData.getIdTagInfo().getStatus() != null) {
			status = authorisationData.getIdTagInfo().getStatus();
		}

		getLocalAuthorisationListUserList().add(
				new User(idTag, parentIdTag, expireDate, status.value()));

	}

	private void addUser(String idTag, IdTagInfo idTagInfo) {

		XMLGregorianCalendar expireDate = null;
		String parentIdTag = "";
		String status = "";

		if (idTagInfo.getExpiryDate() != null) {
			expireDate = idTagInfo.getExpiryDate();
		}

		if (idTagInfo.getParentIdTag() != null) {
			parentIdTag = idTagInfo.getParentIdTag();
		}

		if (idTagInfo.getStatus() != null) {
			status = idTagInfo.getStatus().value();
		}

		getLocalAuthorisationListUserList().add(
				new User(idTag, parentIdTag, expireDate, status));

	}

	private boolean deleteUser(User user) {
		return getLocalAuthorisationListUserList().remove(user);
	}

	private void updateUser(User localUser, IdTagInfo idTagInfo) {

		if (idTagInfo.getExpiryDate() != null)
			localUser.setExpiryDate(idTagInfo.getExpiryDate());

		if (idTagInfo.getParentIdTag() != null)
			localUser.setParentIdTag(idTagInfo.getParentIdTag());

		if (idTagInfo.getStatus() != null)
			localUser.setIdTagStatus(idTagInfo.getStatus().value());

		log.info("Updated user with idTag " + localUser.getIdTag());

	}

	private void updateUser(User localUser,
			AuthorisationData reqAuthorisationData) {

		if (reqAuthorisationData.getIdTagInfo().getExpiryDate() != null)
			localUser.setExpiryDate(reqAuthorisationData.getIdTagInfo()
					.getExpiryDate());

		if (reqAuthorisationData.getIdTagInfo().getParentIdTag() != null)
			localUser.setParentIdTag(reqAuthorisationData.getIdTagInfo()
					.getParentIdTag());

		if (reqAuthorisationData.getIdTagInfo().getStatus() != null)
			localUser.setIdTagStatus(reqAuthorisationData.getIdTagInfo()
					.getStatus().value());

		log.info("Updated user with idTag " + localUser.getIdTag());

	}

	private User getLocalUserData(String idTag) {

		List<User> localUserList = getLocalAuthorisationListUserList();
		int localListLenght = localUserList.size();

		for (int i = 0; i < localListLenght; i++) {

			if (localUserList.get(i).getIdTag().equals(idTag)) {
				return localUserList.get(i);
			}
		}

		return null;

	}

	private void clearLocalAuthorisationList() {
		localAuthorisationList = chargePointService.getControlData()
				.getLocalAuthorisationList();

		if (localAuthorisationList.getUserList().size() > 0)
			localAuthorisationList.getUserList().clear();

		localAuthorisationList.setVersion(0);
	}

	private void updateLocalAuthorisationListVersion(int version) {
		localAuthorisationList = chargePointService.getControlData()
				.getLocalAuthorisationList();
		localAuthorisationList.setVersion(version);
	}

	private int getLocalAuthorisationListVersion() {
		localAuthorisationList = chargePointService.getControlData()
				.getLocalAuthorisationList();
		return localAuthorisationList.getVersion();
	}

	private List<User> getLocalAuthorisationListUserList() {
		localAuthorisationList = chargePointService.getControlData()
				.getLocalAuthorisationList();
		return localAuthorisationList.getUserList();
	}
}
