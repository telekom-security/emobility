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
import java.util.logging.Logger;

import ocpp.cp._2012._06.DataTransferRequest;
import ocpp.cp._2012._06.DataTransferResponse;
import ocpp.cp._2012._06.DataTransferStatus;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.CentralSystemServiceClient;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;

public class DataTransferHandler {

	private static final Logger log = Logger
			.getLogger(DataTransferHandler.class.getName());

	private ChargePoint chargePointService;

	private CentralSystemServiceClient csServiceClient = null;

	public DataTransferHandler(ChargePoint chargePointService) {
		this.chargePointService = chargePointService;
		csServiceClient = chargePointService.getCentralSystemServiceClient();

	}

	public DataTransferResponse handleDataTransferReq(String chargeBoxIdentity,
			DataTransferRequest request) {

		String vendorId = request.getVendorId();

		log.info("DataTransferReq called at ChargeBoxID " + chargeBoxIdentity
				+ ", getVendorId: " + vendorId);

		// Preparing the response
		DataTransferResponse response = new DataTransferResponse();
		DataTransferStatus dataTransferStatus = DataTransferStatus.REJECTED;

		log.info("DataTransferResp | DataTransferStatus: " + dataTransferStatus);
		response.setStatus(dataTransferStatus);
		return response;

	}

	public void processDataTransfer(String chargeBoxIdentity, String vendorID,
			String messageID, String text) {

		log.info("Process DataTransfer");

		ocpp.cs._2012._06.DataTransferRequest request = csServiceClient
				.prepareDataTransferReq(chargeBoxIdentity, vendorID, messageID,
						text);
		csServiceClient.sendDataTransfer(chargeBoxIdentity, request);

	}
}
