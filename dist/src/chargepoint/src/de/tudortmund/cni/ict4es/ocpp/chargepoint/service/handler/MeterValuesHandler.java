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

import ocpp.cs._2012._06.MeterValue;
import ocpp.cs._2012._06.MeterValuesRequest;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils.ConnectorUtils;

public class MeterValuesHandler {

	private static final Logger log = Logger.getLogger(MeterValuesHandler.class
			.getName());

	private ControlData controlData = null;

	private CentralSystemServiceClient csServiceClient = null;

	public MeterValuesHandler(ChargePoint chargePointService) {
		csServiceClient = chargePointService.getCentralSystemServiceClient();
		controlData = chargePointService.getControlData();
	}

	public void processMeterValues(String chargeBoxIdentity, int connectorID,
			int transactionID, List<MeterValue> meterValueList) {

		log.info("Process MeterValues");

		// Save current meter values in the Connector
		Connector connector = ConnectorUtils.getConnector(
				controlData.getConnectors(), connectorID);
		String currentMeterTmp = meterValueList.get(0).getValue().get(0)
				.getValue();
		double d = Double.parseDouble(currentMeterTmp);
		int currentMeter = (int) d;
		int connectorMetervalue = connector.getMeterValue();
		connector.setMeterValue(connectorMetervalue + currentMeter);

		MeterValuesRequest request = csServiceClient.prepareMeterValuesReq(
				chargeBoxIdentity, connectorID, transactionID, meterValueList);
		csServiceClient.sendMeterValues(chargeBoxIdentity, request);

	}

}
