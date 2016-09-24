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
import java.util.logging.Logger;

import javax.xml.ws.Endpoint;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;

public class WebServiceFactory {

	private static final Logger log = Logger.getLogger(WebServiceFactory.class
			.getName());

	private Endpoint ep = null;
	private String serviceIPv4 = null;
	private Object implementor = null;

	public WebServiceFactory(Object implementor, ChargePoint chargePoint) {
		this.implementor = implementor;
		this.serviceIPv4 = chargePoint.getControlData()
				.getChargePointWebServiceIPv4();
	}

	public boolean createWSEndpoint() {

		String serviceURL = "http://" + serviceIPv4 + "/ocpp/cpservice";

		try {
			ep = Endpoint.create(implementor);
			ep.publish(serviceURL);
			log.info("WebService Endpoint created at " + serviceURL);
		} catch (Exception e) {
			log.warning("Can NOT create WebService Endpoint at " + serviceURL
					+ ", Charge Point Service NOT started, \nException: "
					+ e.toString());
			return false;
		}

		return true;
	}

	public void stopWSEndpoint() {
		if (ep != null) {
			ep.stop();
			log.info("WebService Endpoint stopped at " + serviceIPv4);
		}

	}

}
