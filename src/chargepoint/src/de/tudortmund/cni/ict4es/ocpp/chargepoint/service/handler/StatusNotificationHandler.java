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

import ocpp.cs._2012._06.ChargePointErrorCode;
import ocpp.cs._2012._06.ChargePointStatus;
import ocpp.cs._2012._06.StatusNotificationRequest;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;

public class StatusNotificationHandler {

	private static final Logger log = Logger
			.getLogger(StatusNotificationHandler.class.getName());

	private ChargePoint chargePointService;

	private ControlData controlData = null;

	private CentralSystemServiceClient csServiceClient = null;

	public StatusNotificationHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = chargePointService.getCentralSystemServiceClient();
	}

	public String processStatusNotification(String chargeBoxIdentity,
			int connectorId, ChargePointErrorCode chargePointErrorCode,
			ChargePointStatus chargePointStatus, String info,
			XMLGregorianCalendar xmlCal, String vendorId, String vendorErrorCode) {

		log.info("Process StatusNotification");

		controlData = chargePointService.getControlData();

		List<Connector> connectorList = controlData.getConnectors();
		boolean update = false;

		for (int i = 0; i < connectorList.size(); i++) {

			// Connector known --> update
			if (connectorList.get(i).getConnectorId() == connectorId) {
				Connector connector = connectorList.get(i);
				connector.setErrorCode(chargePointErrorCode.value());
				connector.setStatus(chargePointStatus.value());
				connector.setTimeStamp(xmlCal);
				update = true;
				break;

			}
		}

		// Connector unknown --> add
		if (connectorList.size() == 0 || !update) {
			connectorList.add(new Connector(connectorId, xmlCal,
					chargePointStatus, chargePointErrorCode));
		}

		StatusNotificationRequest request = csServiceClient
				.prepareStatusNotificationReq(chargeBoxIdentity, connectorId,
						chargePointErrorCode, chargePointStatus, info, xmlCal,
						vendorId, vendorErrorCode);

		csServiceClient.sendStatusNotification(chargeBoxIdentity, request);

		return "";
	}
}
