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
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.xml.datatype.XMLGregorianCalendar;

import ocpp.cs._2012._06.AuthorizationStatus;
import ocpp.cs._2012._06.BootNotificationResponse;
import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.DiagnosticsStatus;
import ocpp.cs._2012._06.FirmwareStatus;
import ocpp.cs._2012._06.MeterValue;
import ocpp.cs._2012._06.StartTransactionResponse;
import ocpp.cs._2012._06.StopTransactionResponse;

import org.w3c.dom.Document;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.AuthorisationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.BootNotificationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ChangeAvailabilityHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ConfigurationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.DataTransferHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.DiagnosticHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.FirmwareHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.HeartbeatHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.MeterValuesHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.RemoteTransactionHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ReservationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ResetHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.StatusNotificationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.TransactionHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.UnlockConnectorHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargeBoxController;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces.IChargePointService;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.Constants;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DateTimeUtils;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.DialogUI;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.WebServiceFactory;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.XMLUtils;

public class ChargePoint implements IChargePointService {

	private static final Logger log = Logger.getLogger(ChargePoint.class
			.getName());

	private ControlData controlData = null;

	private CentralSystemServiceClient centralSystemServiceClient = null;
	private WebServiceFactory webServiceFactory = null;
	private IChargeBoxController chargeBoxController = null;
	private ChargePointServiceImpl chargePointServiceImpl = null;

	private Document chargePointServiceXMLConf = null;

	private ConfigurationHandler configurationHandler = null;
	private HeartbeatHandler heartbeatHandler = null;
	private AuthorisationHandler authorisationHandler = null;
	private BootNotificationHandler bootNotificationHandler = null;
	private StatusNotificationHandler statusNotificationHandler = null;
	private TransactionHandler transactionHandler = null;
	private MeterValuesHandler meterValuesHandler = null;
	private DiagnosticHandler diagnosticHandler = null;
	private FirmwareHandler firmwareHandler = null;
	private DataTransferHandler dataTransferHandler = null;
	private ChangeAvailabilityHandler changeAvailabilityHandler = null;
	private UnlockConnectorHandler unlockConnectorHandler = null;
	private ResetHandler resetHandler = null;
	private RemoteTransactionHandler remoteTransactionHandler = null;
	private ReservationHandler reservationHandler = null;

	private DialogUI dialogUI = null;

	public ChargePoint() {
		controlData = new ControlData();
	}

	public boolean init(String pathAndConfigFile,
			String chargePointServiceProxyURL) {

		log.info("Init ChargePointService...");

		if (loadChargePointInfoFromXML(pathAndConfigFile)) {

			System.out
					.println("\n----------- Charge Point Service Configuration ---------------------");

			System.out.println("CP WebService IPv4\t -> "
					+ controlData.getChargePointWebServiceIPv4());

			System.out.println("CS WebService URL\t -> "
					+ controlData.getCentralSystemWebServiceURL());
			System.out.println("CS WebService URI\t -> "
					+ controlData.getCentralSystemWebServiceURI());
			System.out.println("CS WebService Name\t -> "
					+ controlData.getCentralSystemWebServiceName());

			System.out.println("CP NumberOfConn.\t -> "
					+ controlData.getNumberOfConnectors());
			System.out.println("CP ChargeBoxId.\t\t -> "
					+ controlData.getChargeBoxIdentity());
			System.out.println("CP Model\t\t -> "
					+ controlData.getChargePointModel());
			System.out.println("CP Vendor\t\t -> "
					+ controlData.getChargePointVendor());
			System.out.println("CP SerialNum.\t\t -> "
					+ controlData.getChargePointSerialNumber());
			System.out.println("CP ChargeBoxSerialNum.\t -> "
					+ controlData.getChargeBoxSerialNumber());

			System.out.println("FirmwareVersion\t\t -> "
					+ controlData.getFirmwareVersion());

			System.out.println("ICCID\t\t\t -> " + controlData.getIccid());
			System.out.println("IMSI\t\t\t -> " + controlData.getImsi());
			System.out.println("MeterSerialNum.\t\t -> "
					+ controlData.getMeterSerialNumber());
			System.out
					.println("MeterType\t\t -> " + controlData.getMeterType());

			System.out
					.println("--------------------------------------------------------------------\n");

		} else {
			return false;
		}

		if (chargePointServiceProxyURL != null
				&& !chargePointServiceProxyURL.equals(""))
			controlData
					.setChargePointWebServiceProxyURL(chargePointServiceProxyURL);

		return true;

	}

	public void showAboutDialog(JFrame parent) {
		dialogUI = new DialogUI(parent, "About");
		dialogUI.showAboutDialog();
	}

	public boolean startService() {

		log.info("Start ChargePointService...");

		chargePointServiceImpl = new ChargePointServiceImpl(this);
		webServiceFactory = new WebServiceFactory(chargePointServiceImpl, this);

		if (!webServiceFactory.createWSEndpoint())
			return false;

		// Connect to Central System Service and start Heartbeat
		centralSystemServiceClient = new CentralSystemServiceClient(this);

		if (centralSystemServiceClient.connectToCSService()) {

			configurationHandler = new ConfigurationHandler(this);
			bootNotificationHandler = new BootNotificationHandler(this);
			authorisationHandler = new AuthorisationHandler(this);
			statusNotificationHandler = new StatusNotificationHandler(this);
			transactionHandler = new TransactionHandler(this);
			meterValuesHandler = new MeterValuesHandler(this);
			diagnosticHandler = new DiagnosticHandler(this);
			firmwareHandler = new FirmwareHandler(this);
			dataTransferHandler = new DataTransferHandler(this);

			changeAvailabilityHandler = new ChangeAvailabilityHandler(this);
			unlockConnectorHandler = new UnlockConnectorHandler(this);
			resetHandler = new ResetHandler(this);
			remoteTransactionHandler = new RemoteTransactionHandler(this);
			reservationHandler = new ReservationHandler(this);

			initConnectors(Integer.valueOf(controlData.getNumberOfConnectors()));

			if (!configurationHandler.init(Constants.BASE_CONFIG_DIR
					+ Constants.CP_CONFIGURATION))
				return false;

			// Init Local AuthorisationsList
			if (!authorisationHandler.init(Constants.BASE_CONFIG_DIR
					+ Constants.CP_LOCAL_AUTHORISATION_LIST))
				return false;

			heartbeatHandler = new HeartbeatHandler(this);
			heartbeatHandler.init(controlData.getChargeBoxIdentity(),
					controlData.getConfiguration().getHeartBeatInterval());
			heartbeatHandler.startHeartBeat();
		}

		return true;
	}

	public void stopService() {
		log.info("Stop ChargePointService...");
		centralSystemServiceClient = null;
		webServiceFactory.stopWSEndpoint();
		webServiceFactory = null;
		configurationHandler = null;
		authorisationHandler = null;

		if (heartbeatHandler != null) {
			if (heartbeatHandler.isRunning())
				heartbeatHandler.stopHeartBeat();

			heartbeatHandler = null;
		}

	}

	public void registerChargeBoxController(
			IChargeBoxController chargeBoxController) {
		this.chargeBoxController = chargeBoxController;
	}

	private boolean loadChargePointInfoFromXML(String configPath) {

		// log.info("Loading initial ChargePointService configuration...");

		chargePointServiceXMLConf = XMLUtils.loadXML(configPath);

		if (chargePointServiceXMLConf != null) {

			// log.info("Loading initial ChargePointService configuration SUCCESSFUL -> set ControlData.");

			controlData.setChargePointWebServiceIPv4(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CP_WEBSERVICE_IPv4));

			controlData.setCentralSystemWebServiceURL(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CS_WEBSERVICE_URL));
			controlData.setCentralSystemWebServiceURI(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CS_WEBSERVICE_URI));
			controlData.setCentralSystemWebServiceName(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CS_WEBSERVICE_NAME));

			controlData.setNumberOfConnectors(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.NUMBER_OF_CONNECTORS));
			controlData.setChargeBoxIdentity(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CHARGEBOX_IDENTITY));
			controlData.setChargePointModel(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CP_MODEL));
			controlData.setChargePointVendor(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CP_VENDOR));
			controlData.setChargePointSerialNumber(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.CP_SERIAL_NUMBER));
			controlData.setChargeBoxSerialNumber(XMLUtils.getKeyValue(
					chargePointServiceXMLConf,
					Constants.CHARGEBOX_SERIAL_NUMBER));
			controlData.setFirmwareVersion(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.FIRMWARE_VERSION));
			controlData.setIccid(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.ICCID));
			controlData.setImsi(XMLUtils.getKeyValue(chargePointServiceXMLConf,
					Constants.IMSI));
			controlData.setMeterSerialNumber(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.METER_SERIAL_NUMBER));
			controlData.setMeterType(XMLUtils.getKeyValue(
					chargePointServiceXMLConf, Constants.METER_TYPE));

			return true;

		} else {

			log.info("Loading initial ChargePointService configuration FAILED -> "
					+ configPath + " not existing?");
			return false;
		}
	}

	@Override
	public BootNotificationResponse doBootNotification(
			String chargeBoxIdentity, String chargePointModel,
			String chargePointVendor, String chargePointSerialNumber,
			String chargeBoxSerialNumber, String firmwareVersion, String iccid,
			String imsi, String meterSerialNumber, String meterType) {
		return bootNotificationHandler.processBootNotification(
				chargeBoxIdentity, chargePointModel, chargePointVendor,
				chargePointSerialNumber, chargeBoxSerialNumber,
				firmwareVersion, iccid, imsi, meterSerialNumber, meterType);
	}

	@Override
	public AuthorizationStatus doAuthorization(String chargeBoxIdentity,
			String idTag) {
		return authorisationHandler.processAuthorization(chargeBoxIdentity,
				idTag);
	}

	@Override
	public String doStatusNotification(String chargeBoxIdentity,
			int connectorId, ChargePointErrorCode chargePointErrorCode,
			ChargePointStatus chargePointStatus, String info,
			XMLGregorianCalendar xmlCal, String vendorId, String vendorErrorCode) {
		return statusNotificationHandler.processStatusNotification(
				chargeBoxIdentity, connectorId, chargePointErrorCode,
				chargePointStatus, info, xmlCal, vendorId, vendorErrorCode);
	}

	@Override
	public StartTransactionResponse doStartTransaction(
			String chargeBoxIdentity, int connectorID, String idTag,
			int meterStart, XMLGregorianCalendar xmlCalTimestampStart,
			int reservationId) {
		return transactionHandler.processStartTransaction(chargeBoxIdentity,
				connectorID, idTag, meterStart, xmlCalTimestampStart,
				reservationId);
	}

	@Override
	public StopTransactionResponse doStopTransaction(String chargeBoxIdentity,
			int meterStop, XMLGregorianCalendar xmlCalTimestampStop,
			int transactionID, String idTag, List<MeterValue> meterValueList,
			int connectorID) {
		return transactionHandler.processStopTransaction(chargeBoxIdentity,
				meterStop, xmlCalTimestampStop, transactionID, idTag,
				meterValueList, connectorID);
	}

	@Override
	public void doMeterValues(String chargeBoxIdentity, int connectorID,
			int transactionID, List<MeterValue> meterValueList) {
		meterValuesHandler.processMeterValues(chargeBoxIdentity, connectorID,
				transactionID, meterValueList);
	}

	@Override
	public void doDiagnosticStatusNotification(String chargeBoxIdentity,
			DiagnosticsStatus diagnosticsStatus) {
		diagnosticHandler.processDiagnosticStatusNotification(
				chargeBoxIdentity, diagnosticsStatus);
	}

	@Override
	public void doFirmwareStatusNotification(String chargeBoxIdentity,
			FirmwareStatus firmwareStatus) {
		firmwareHandler.processFirmwareStatusNotification(chargeBoxIdentity,
				firmwareStatus);
	}

	@Override
	public void doDataTransfer(String chargeBoxIdentity, String vendorID,
			String messageID, String text) {
		dataTransferHandler.processDataTransfer(chargeBoxIdentity, vendorID,
				messageID, text);
	}

	@Override
	public void registerConnector(int connectorId, XMLGregorianCalendar xmlCal,
			ChargePointStatus chargePointStatus,
			ChargePointErrorCode chargePointErrorCode) {
		controlData.getConnectors().add(
				new Connector(connectorId, DateTimeUtils
						.getCurrentDateTimeAsXML(),
						ChargePointStatus.AVAILABLE,
						ChargePointErrorCode.NO_ERROR));
	}

	public ControlData getControlData() {
		return controlData;
	}

	public void setControlData(ControlData data) {
		controlData = data;
	}

	public CentralSystemServiceClient getCentralSystemServiceClient() {
		return centralSystemServiceClient;
	}

	public HeartbeatHandler getHeartbeatHandler() {
		return heartbeatHandler;
	}

	public IChargeBoxController getChargeBoxController() {
		return chargeBoxController;
	}

	public static Logger getLog() {
		return log;
	}

	public Document getChargePointServiceXMLConf() {
		return chargePointServiceXMLConf;
	}

	public WebServiceFactory getChargePointServiceEP() {
		return webServiceFactory;
	}

	public ConfigurationHandler getConfigurationHandler() {
		return configurationHandler;
	}

	public AuthorisationHandler getAuthorisationHandler() {
		return authorisationHandler;
	}

	public BootNotificationHandler getBootNotificationHandler() {
		return bootNotificationHandler;
	}

	public StatusNotificationHandler getStatusNotificationHandler() {
		return statusNotificationHandler;
	}

	public TransactionHandler getTransactionHandler() {
		return transactionHandler;
	}

	public MeterValuesHandler getMeterValuesHandler() {
		return meterValuesHandler;
	}

	public DiagnosticHandler getDiagnosticHandler() {
		return diagnosticHandler;
	}

	public FirmwareHandler getFirmwareHandler() {
		return firmwareHandler;
	}

	public DataTransferHandler getDataTransferHandler() {
		return dataTransferHandler;
	}

	public DialogUI getDialogUI() {
		return dialogUI;
	}

	public ChangeAvailabilityHandler getChangeAvailabilityHandler() {
		return changeAvailabilityHandler;
	}

	public UnlockConnectorHandler getUnlockConnectorHandler() {
		return unlockConnectorHandler;
	}

	public ResetHandler getResetHandler() {
		return resetHandler;
	}

	public RemoteTransactionHandler getRemoteTransactionHandler() {
		return remoteTransactionHandler;
	}

	public ReservationHandler getReservationHandler() {
		return reservationHandler;
	}

	private void initConnectors(int numberOfConnectors) {
		// TODO: ChargePointStatus should not be AVAILABLE by default. It should
		// acquire corresponding status information from the charge box
		// controller.
		for (int i = 1; i <= Integer.valueOf(numberOfConnectors); i++) {
			registerConnector(i, DateTimeUtils.getCurrentDateTimeAsXML(),
					ChargePointStatus.AVAILABLE, ChargePointErrorCode.NO_ERROR);
		}
	}

}