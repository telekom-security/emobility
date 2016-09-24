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
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.ChargePoint;

public class SOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private String chargePointServiceURL = null;
	private String chargePointServiceProxyURL = null;

	public SOAPHeaderHandler(ChargePoint chargePointService) {
		chargePointServiceURL = chargePointService.getControlData()
				.getChargePointWebServiceIPv4();
		chargePointServiceProxyURL = chargePointService.getControlData()
				.getChargePointWebServiceProxyIPv4();
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		Boolean isRequest = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		try {
			if (isRequest) {

				// Adding From and Address element to WS addressing header
				SOAPMessage soapMsg = context.getMessage();
				SOAPHeader header = soapMsg.getSOAPHeader();

				QName qNameFrom = new QName(
						"http://www.w3.org/2005/08/addressing", "From");
				SOAPHeaderElement From = header.addHeaderElement(qNameFrom);

				QName qNameAddress = new QName(
						"http://www.w3.org/2005/08/addressing", "Address");

				if (chargePointServiceProxyURL != null
						&& !chargePointServiceProxyURL.equals(""))
					chargePointServiceURL = chargePointServiceProxyURL;

				From.addChildElement(qNameAddress).setTextContent(
						"http://" + chargePointServiceURL + "/ocpp/cpservice");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
}