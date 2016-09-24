package de.tudortmund.cni.ict4es.ocpp.chargepoint.service;

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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.AddressingFeature;

import ocpp.cs._2012._06.AuthorizeRequest;
import ocpp.cs._2012._06.AuthorizeResponse;
import ocpp.cs._2012._06.BootNotificationRequest;
import ocpp.cs._2012._06.BootNotificationResponse;
import ocpp.cs._2012._06.CentralSystemService;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.DataTransferRequest;
import ocpp.cs._2012._06.DataTransferResponse;
import ocpp.cs._2012._06.DiagnosticsStatus;
import ocpp.cs._2012._06.DiagnosticsStatusNotificationRequest;
import ocpp.cs._2012._06.DiagnosticsStatusNotificationResponse;
import ocpp.cs._2012._06.FirmwareStatus;
import ocpp.cs._2012._06.FirmwareStatusNotificationRequest;
import ocpp.cs._2012._06.FirmwareStatusNotificationResponse;
import ocpp.cs._2012._06.HeartbeatRequest;
import ocpp.cs._2012._06.HeartbeatResponse;
import ocpp.cs._2012._06.MeterValue;
import ocpp.cs._2012._06.MeterValuesRequest;
import ocpp.cs._2012._06.MeterValuesResponse;
import ocpp.cs._2012._06.StartTransactionRequest;
import ocpp.cs._2012._06.StartTransactionResponse;
import ocpp.cs._2012._06.StatusNotificationRequest;
import ocpp.cs._2012._06.StatusNotificationResponse;
import ocpp.cs._2012._06.StopTransactionRequest;
import ocpp.cs._2012._06.StopTransactionResponse;
import ocpp.cs._2012._06.TransactionData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData.ChargePointServiceMode;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.SOAPHeaderHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.InputUtils;

public class CentralSystemServiceClient {

	private static final Logger log = Logger
			.getLogger(CentralSystemServiceClient.class.getName());

	private final static boolean DEBUG = false;

	private ChargePoint chargePointService;
	private static CentralSystemService csServiceRef = null;
	private URL urlToCSService = null;
	private ControlData controlData = null;

	private String webServiceURL;
	private String webServiceURI;
	private String webServiceName;

	public CentralSystemServiceClient(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		controlData = chargePointService.getControlData();
		this.webServiceURL = controlData.getCentralSystemWebServiceURL();
		this.webServiceURI = controlData.getCentralSystemWebServiceURI();
		this.webServiceName = controlData.getCentralSystemWebServiceName();

	}

	private boolean validateURL(String urlStr) {

		URLConnection urlConnection = null;
		try {
			urlToCSService = new URL(urlStr);
			urlConnection = urlToCSService.openConnection();
			if (urlConnection.getContent() != null) {
				log.info("URL OK");
				return true;
			} else {
				log.warning("BAD URL");
			}
		} catch (MalformedURLException ex) {
			log.info("BAD URL");
		} catch (IOException ex) {
			log.warning("Failed opening connection. Maybe CentralSystemService (WS) is not running at: "
					+ urlConnection.getURL().toExternalForm());
		}

		return false;
	}

	public boolean connectToCSService() {

		QName qname = new QName(webServiceURI, webServiceName);

		if (csServiceRef == null) {

			log.info("Connecting to Central System...");

			if (validateURL(webServiceURL)) {

				Service service = Service.create(urlToCSService, qname);
				WebServiceFeature[] enabledRequiredwsf = { new AddressingFeature(
						true, true) };
				csServiceRef = service.getPort(CentralSystemService.class,
						enabledRequiredwsf);

				// Adding SOAP Handler to modify the WS-Addressing header
				BindingProvider bp = (BindingProvider) csServiceRef;
				Binding binding = bp.getBinding();
				List<Handler> handlerChain = binding.getHandlerChain();
				handlerChain.add(new SOAPHeaderHandler(chargePointService));
				binding.setHandlerChain(handlerChain);
				log.info("Connection to Central System established -> Charge Point Mode = ONLINE");
				controlData
						.setChargePointServiceMode(ChargePointServiceMode.ONLINE);
				return true;

			} else {
				log.info("Connection to Central System NOT established -> Charge Point Mode = OFFLINE");
				controlData
						.setChargePointServiceMode(ChargePointServiceMode.OFFLINE);
				return false;
			}

		} else {
			log.info("ChargePointService already connected to Central System");
			return true;
		}
	}

	// /// Create OCPP Request Payloads /////

	public AuthorizeRequest prepareAuthorizationReq(String chargeBoxIdentity,
			String idTag) {

		// Prepare AuthorizeRequest
		if (DEBUG) {
			log.info("Prepare Authorization Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tIdTag: " + idTag);
		}

		AuthorizeRequest request = new AuthorizeRequest();

		// Mandatory values:
		request.setIdTag(idTag);

		return request;
	}

	public BootNotificationRequest prepareBootNotificationReq(
			String chargeBoxIdentity, String chargePointModel,
			String chargePointVendor, String chargePointSerialNumber,
			String chargeBoxSerialNumber, String firmwareVersion, String iccid,
			String imsi, String meterSerialNumber, String meterType) {

		// Prepare BootNotificationRequest

		if (DEBUG) {
			log.info("Prepare BootNotification Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tChargePointModel: " + chargePointModel);
			log.info("\tChargePointVendor: " + chargePointVendor);
		}

		BootNotificationRequest request = new BootNotificationRequest();

		// Mandatory values:
		request.setChargePointModel(chargePointModel);
		request.setChargePointVendor(chargePointVendor);

		// Optional values:
		if (!InputUtils.isNullOrEmpty(chargeBoxSerialNumber)) {

			if (DEBUG)
				log.info("\tChargeBoxSerialNumber: " + chargeBoxSerialNumber);

			request.setChargeBoxSerialNumber(chargeBoxSerialNumber);
		}

		if (!InputUtils.isNullOrEmpty(chargePointSerialNumber)) {

			if (DEBUG)
				log.info("\tChargePointSerialNumber: "
						+ chargePointSerialNumber);

			request.setChargePointSerialNumber(chargePointSerialNumber);
		}

		if (!InputUtils.isNullOrEmpty(firmwareVersion)) {

			if (DEBUG)
				log.info("\tFirmwareVersion: " + firmwareVersion);

			request.setFirmwareVersion(firmwareVersion);
		}

		if (!InputUtils.isNullOrEmpty(iccid)) {

			if (DEBUG)
				log.info("\tICCID: " + iccid);

			request.setIccid(iccid);
		}

		if (!InputUtils.isNullOrEmpty(imsi)) {

			if (DEBUG)
				log.info("\tIMSI: " + imsi);

			request.setImsi(imsi);
		}

		if (!InputUtils.isNullOrEmpty(meterSerialNumber)) {

			if (DEBUG)
				log.info("\tMeterSerialNumber: " + meterSerialNumber);

			request.setMeterSerialNumber(meterSerialNumber);
		}

		if (!InputUtils.isNullOrEmpty(meterType)) {

			if (DEBUG)
				log.info("\tMeterType: " + meterType);

			request.setMeterType(meterType);
		}

		return request;
	}

	public StatusNotificationRequest prepareStatusNotificationReq(
			String chargeBoxIdentity, int connectorID,
			ChargePointErrorCode cpErrorCode, ChargePointStatus cpStatus,
			String info, XMLGregorianCalendar xmlCal, String vendorId,
			String vendorErrorCode) {

		// Prepare StatusNotificationRequest

		if (DEBUG) {
			log.info("Prepare StatusNotification Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tConnectorID: " + connectorID);
			log.info("\tChargePointErrorCode: " + cpErrorCode);
			log.info("\tChargePointStatus: " + cpStatus);
		}

		StatusNotificationRequest request = new StatusNotificationRequest();

		// Mandatory values:
		request.setConnectorId(connectorID);
		request.setErrorCode(cpErrorCode);
		request.setStatus(cpStatus);

		// Optional values:
		if (!InputUtils.isNullOrEmpty(info)) {

			if (DEBUG)
				log.info("\tInfo: " + info);

			request.setInfo(info);
		}

		if (xmlCal != null) {

			if (DEBUG)
				log.info("\tTimeStamp: " + xmlCal);

			request.setTimestamp(xmlCal);
		}

		if (!InputUtils.isNullOrEmpty(vendorId)) {

			if (DEBUG)
				log.info("\tVendorID: " + vendorId);

			request.setVendorId(vendorId);
		}

		if (!InputUtils.isNullOrEmpty(vendorErrorCode)) {

			if (DEBUG)
				log.info("\tVendorErrorCode: " + vendorErrorCode);

			request.setVendorErrorCode(vendorErrorCode);
		}

		return request;
	}

	public StartTransactionRequest prepareStartTransactionReq(
			String chargeBoxIdentity, int connectorID, String idTag,
			int meterStart, XMLGregorianCalendar xmlCalTimestampStart,
			int reservationId) {

		// Prepare StartTransactionRequest

		if (DEBUG) {
			log.info("Prepare StartTransaction Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tConnectorID: " + connectorID);
			log.info("\tIdTag: " + idTag);
			log.info("\tMeterStart: " + meterStart);
			log.info("\tTimeStamp: "
					+ DateTimeUtils.xmlToString(xmlCalTimestampStart));
		}

		StartTransactionRequest request = new StartTransactionRequest();

		// Mandatory values:
		request.setConnectorId(connectorID);
		request.setIdTag(idTag);
		request.setMeterStart(meterStart);
		request.setTimestamp(xmlCalTimestampStart);

		// Optional values:
		if (reservationId != -1) {

			if (DEBUG)
				log.info("\tReservationId: " + reservationId);

			request.setReservationId(reservationId);
		}

		return request;
	}

	public StopTransactionRequest prepareStopTransactionReq(
			String chargeBoxIdentity, int meterStop,
			XMLGregorianCalendar xmlCalTimestampStop, int transactionID,
			String idTag, List<MeterValue> meterValueList, int connectorID) {

		// Prepare StopTransactionRequest

		if (DEBUG) {
			log.info("Prepare StopTransaction Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tMeterStop: " + meterStop);
			log.info("\tTimeStamp: "
					+ DateTimeUtils.xmlToString(xmlCalTimestampStop));
			log.info("\tTransactionId: " + transactionID);
		}

		StopTransactionRequest request = new StopTransactionRequest();

		// Mandatory values:
		request.setMeterStop(meterStop);
		request.setTimestamp(xmlCalTimestampStop);
		request.setTransactionId(transactionID);

		// Optional values:
		if (!InputUtils.isNullOrEmpty(idTag)) {

			if (DEBUG)
				log.info("\tIdTag: " + idTag);

			request.setIdTag(idTag);
		}

		if (meterValueList != null && meterValueList.size() > 1) {

			if (DEBUG)
				log.info("\tMeterValues size : " + meterValueList.size());

			TransactionData transData = new TransactionData();
			for (MeterValue value : meterValueList) {
				transData.getValues().add(value);
			}

			request.getTransactionData().add(transData);
		}

		return request;
	}

	public MeterValuesRequest prepareMeterValuesReq(String chargeBoxIdentity,
			int connectorID, int transactionID, List<MeterValue> meterValueList) {

		// Prepare MeterValuesRequest

		if (DEBUG) {
			log.info("Prepare MeterValues Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tConnectorID: " + connectorID);
		}

		MeterValuesRequest request = new MeterValuesRequest();

		// Mandatory values:
		request.setConnectorId(connectorID);

		// Optional values:
		if (transactionID != -1) {

			if (DEBUG)
				log.info("\tTransactionId: " + transactionID);

			request.setTransactionId(transactionID);
		}

		if (meterValueList != null && meterValueList.size() > 1) {

			if (DEBUG)
				log.info("\tMeterValues size : " + meterValueList.size());

			for (MeterValue value : meterValueList) {
				request.getValues().add(value);
			}
		}

		return request;
	}

	public DiagnosticsStatusNotificationRequest prepareDiagnosticStatusNotificationReq(
			String chargeBoxIdentity, DiagnosticsStatus status) {

		// Prepare DiagnosticsStatusNotificationRequest

		if (DEBUG) {
			log.info("Prepare DiagnosticStatusNotification Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tStatus: " + status);
		}

		DiagnosticsStatusNotificationRequest request = new DiagnosticsStatusNotificationRequest();

		// Mandatory values:
		request.setStatus(status);

		return request;
	}

	public FirmwareStatusNotificationRequest prepareFirmwareStatusNotificationReq(
			String chargeBoxIdentity, FirmwareStatus status) {

		// Prepare FirmwareStatusNotificationRequest
		if (DEBUG) {
			log.info("Prepare FirmwareStatusNotification Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tStatus: " + status);
		}

		FirmwareStatusNotificationRequest request = new FirmwareStatusNotificationRequest();

		// Mandatory values:
		request.setStatus(status);

		return request;
	}

	public DataTransferRequest prepareDataTransferReq(String chargeBoxIdentity,
			String vendorID, String messageID, String text) {

		// Prepare DataTransferRequest
		if (DEBUG) {
			log.info("Prepare DataTransfer Req:");
			log.info("\tChargeBoxIdentity: " + chargeBoxIdentity);
			log.info("\tVendorID: " + vendorID);
		}

		DataTransferRequest request = new DataTransferRequest();

		// Mandatory values:
		request.setVendorId(vendorID);

		if (!InputUtils.isNullOrEmpty(messageID)) {

			if (DEBUG)
				log.info("\tMessageID: " + messageID);

			request.setMessageId(messageID);
		}

		if (!InputUtils.isNullOrEmpty(text)) {
			if (DEBUG)
				log.info("\tData: " + text);

			request.setData(text);
		}

		return request;
	}

	// /// Send OCPP Requests and return corresponding Responses /////

	public BootNotificationResponse sendBootNotification(
			String chargeBoxIdentity, BootNotificationRequest request) {

		log.info("Sending bootNotificationReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		BootNotificationResponse response = csServiceRef.bootNotification(
				request, chargeBoxIdentity);
		return response;
	}

	public AuthorizeResponse sendAuthorize(String chargeBoxIdentity,
			AuthorizeRequest request) {

		log.info("Sending authorizeReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		AuthorizeResponse response = csServiceRef.authorize(request,
				chargeBoxIdentity);
		return response;
	}

	public StartTransactionResponse sendStartTransaction(
			String chargeBoxIdentity, StartTransactionRequest request) {

		log.info("Sending startTransactionReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		StartTransactionResponse response = csServiceRef.startTransaction(
				request, chargeBoxIdentity);
		return response;
	}

	public StopTransactionResponse sendStopTransaction(
			String chargeBoxIdentity, StopTransactionRequest request) {

		log.info("Sending stopTransactionReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		StopTransactionResponse response = csServiceRef.stopTransaction(
				request, chargeBoxIdentity);
		return response;
	}

	public HeartbeatResponse sendHeartbeat(String chargeBoxIdentity,
			HeartbeatRequest request) {

		log.info("Sending heartbeatReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		HeartbeatResponse response = csServiceRef.heartbeat(request,
				chargeBoxIdentity);
		return response;
	}

	public MeterValuesResponse sendMeterValues(String chargeBoxIdentity,
			MeterValuesRequest request) {

		log.info("Sending meterValuesReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		MeterValuesResponse response = csServiceRef.meterValues(request,
				chargeBoxIdentity);
		return response;
	}

	public DiagnosticsStatusNotificationResponse sendDiagnosticStatusNotification(
			String chargeBoxIdentity,
			DiagnosticsStatusNotificationRequest request) {

		log.info("Sending diagnosticStatusNotificationReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		DiagnosticsStatusNotificationResponse response = csServiceRef
				.diagnosticsStatusNotification(request, chargeBoxIdentity);
		return response;
	}

	public StatusNotificationResponse sendStatusNotification(
			String chargeBoxIdentity, StatusNotificationRequest request) {

		log.info("Sending statusNotificationReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		StatusNotificationResponse response = csServiceRef.statusNotification(
				request, chargeBoxIdentity);
		return response;
	}

	public DataTransferResponse sendDataTransfer(String chargeBoxIdentity,
			DataTransferRequest request) {

		log.info("Sending dataTransferReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		DataTransferResponse response = csServiceRef.dataTransfer(request,
				chargeBoxIdentity);
		return response;
	}

	public FirmwareStatusNotificationResponse sendFirmwareStatusNotification(
			String chargeBoxIdentity, FirmwareStatusNotificationRequest request) {

		log.info("Sending firmwareStatusNotificationReq from chargeBoxIdentity = "
				+ chargeBoxIdentity);
		FirmwareStatusNotificationResponse response = csServiceRef
				.firmwareStatusNotification(request, chargeBoxIdentity);
		return response;
	}
}
