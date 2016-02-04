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
import java.util.List;
import java.util.logging.Logger;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.Connector;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data.ControlData;

public class PrintUtils {

	private static final Logger log = Logger.getLogger(PrintUtils.class
			.getName());

	public static void printConnectorStatus(ControlData controlData) {

		List<Connector> connectorList = controlData.getConnectors();
		int size = connectorList.size();

		log.info("CurrentConnectorStatus for " + size + " connectors");

		for (int i = 0; i < size; i++) {

			log.info("\tId: " + connectorList.get(i).getConnectorId());
			log.info("\tStatus: " + connectorList.get(i).getStatus());
			log.info("\tTimestamp: " + connectorList.get(i).getTimeStamp());
			log.info("\tErrorCode: " + connectorList.get(i).getErrorCode());

		}

	}

}
