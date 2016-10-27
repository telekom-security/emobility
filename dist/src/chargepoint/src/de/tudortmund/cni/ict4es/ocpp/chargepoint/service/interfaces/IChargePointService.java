package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.interfaces;

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

public interface IChargePointService {

	public void registerConnector(int connectorId, XMLGregorianCalendar xmlCal,
			ChargePointStatus chargePointStatus,
			ChargePointErrorCode chargePointErrorCode);

	public BootNotificationResponse doBootNotification(
			String chargeBoxIdentity, String chargePointModel,
			String chargePointVendor, String chargePointSerialNumber,
			String chargeBoxSerialNumber, String firmwareVersion, String iccid,
			String imsi, String meterSerialNumber, String meterType);

	public AuthorizationStatus doAuthorization(String chargeBoxIdentity,
			String idTag);

	public String doStatusNotification(String chargeBoxIdentity,
			int connectorId, ChargePointErrorCode chargePointErrorCode,
			ChargePointStatus chargePointStatus, String info,
			XMLGregorianCalendar xmlCal, String vendorId, String vendorErrorCode);

	public StartTransactionResponse doStartTransaction(
			String chargeBoxIdentity, int connectorID, String idTag,
			int meterStart, XMLGregorianCalendar xmlCalTimestampStart,
			int reservationId);

	public StopTransactionResponse doStopTransaction(String chargeBoxIdentity,
			int meterStop, XMLGregorianCalendar xmlCalTimestampStop,
			int transactionID, String idTag, List<MeterValue> meterValueList,
			int connectorID);

	public void doMeterValues(String chargeBoxIdentity, int connectorID,
			int transactionID, List<MeterValue> meterValueList);

	public void doDiagnosticStatusNotification(String chargeBoxIdentity,
			DiagnosticsStatus diagnosticsStatus);

	public void doFirmwareStatusNotification(String chargeBoxIdentity,
			FirmwareStatus firmwareStatus);

	public void doDataTransfer(String chargeBoxIdentity, String vendorID,
			String messageID, String text);
}
