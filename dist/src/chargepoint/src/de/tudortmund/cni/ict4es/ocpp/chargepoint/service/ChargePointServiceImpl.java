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
import java.util.logging.Logger;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.Addressing;

import ocpp.cp._2012._06.CancelReservationRequest;
import ocpp.cp._2012._06.CancelReservationResponse;
import ocpp.cp._2012._06.ChangeAvailabilityRequest;
import ocpp.cp._2012._06.ChangeAvailabilityResponse;
import ocpp.cp._2012._06.ChangeConfigurationRequest;
import ocpp.cp._2012._06.ChangeConfigurationResponse;
import ocpp.cp._2012._06.ChargePointService;
import ocpp.cp._2012._06.ClearCacheRequest;
import ocpp.cp._2012._06.ClearCacheResponse;
import ocpp.cp._2012._06.DataTransferRequest;
import ocpp.cp._2012._06.DataTransferResponse;
import ocpp.cp._2012._06.GetConfigurationRequest;
import ocpp.cp._2012._06.GetConfigurationResponse;
import ocpp.cp._2012._06.GetDiagnosticsRequest;
import ocpp.cp._2012._06.GetDiagnosticsResponse;
import ocpp.cp._2012._06.GetLocalListVersionRequest;
import ocpp.cp._2012._06.GetLocalListVersionResponse;
import ocpp.cp._2012._06.RemoteStartTransactionRequest;
import ocpp.cp._2012._06.RemoteStartTransactionResponse;
import ocpp.cp._2012._06.RemoteStopTransactionRequest;
import ocpp.cp._2012._06.RemoteStopTransactionResponse;
import ocpp.cp._2012._06.ReserveNowRequest;
import ocpp.cp._2012._06.ReserveNowResponse;
import ocpp.cp._2012._06.ResetRequest;
import ocpp.cp._2012._06.ResetResponse;
import ocpp.cp._2012._06.SendLocalListRequest;
import ocpp.cp._2012._06.SendLocalListResponse;
import ocpp.cp._2012._06.UnlockConnectorRequest;
import ocpp.cp._2012._06.UnlockConnectorResponse;
import ocpp.cp._2012._06.UpdateFirmwareRequest;
import ocpp.cp._2012._06.UpdateFirmwareResponse;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.AuthorisationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ChangeAvailabilityHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ConfigurationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.DataTransferHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.DiagnosticHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.FirmwareHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.RemoteTransactionHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ReservationHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.ResetHandler;
import de.tudortmund.cni.ict4es.ocpp.chargepoint.service.handler.UnlockConnectorHandler;

@javax.jws.WebService(serviceName = "ChargePointService", targetNamespace = "urn://Ocpp/Cp/2012/06/", endpointInterface = "ocpp.cp._2012._06.ChargePointService")
@Addressing(enabled = false, required = false)
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class ChargePointServiceImpl implements ChargePointService {

	private static final Logger log = Logger
			.getLogger(ChargePointServiceImpl.class.getName());

	private ChargePoint chargePoint = null;

	public ChargePointServiceImpl(ChargePoint chargePoint) {
		this.chargePoint = chargePoint;
	}

	@Override
	public ChangeAvailabilityResponse changeAvailability(
			ChangeAvailabilityRequest parameters, String chargeBoxIdentity) {

		log.info("Received changeAvailability at {} " + chargeBoxIdentity);
		ChangeAvailabilityHandler handler = chargePoint
				.getChangeAvailabilityHandler();
		ChangeAvailabilityResponse response = handler
				.handleChangeAvailabilityReq(chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public UnlockConnectorResponse unlockConnector(
			UnlockConnectorRequest parameters, String chargeBoxIdentity) {
		log.info("Received UnlockConnectorResponse at {} " + chargeBoxIdentity);
		UnlockConnectorHandler handler = chargePoint
				.getUnlockConnectorHandler();
		UnlockConnectorResponse response = handler.handleUnlockConnectorReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public ResetResponse reset(ResetRequest parameters, String chargeBoxIdentity) {
		log.info("Received ResetRequest at {} " + chargeBoxIdentity);
		ResetHandler handler = chargePoint.getResetHandler();
		ResetResponse response = handler.handleResetReq(chargeBoxIdentity,
				parameters);
		return response;
	}

	@Override
	public GetDiagnosticsResponse getDiagnostics(
			GetDiagnosticsRequest parameters, String chargeBoxIdentity) {
		log.info("Received GetDiagnosticsResponse at {} " + chargeBoxIdentity);
		DiagnosticHandler handler = chargePoint.getDiagnosticHandler();
		GetDiagnosticsResponse response = handler.handleGetDiagnosticsReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public ClearCacheResponse clearCache(ClearCacheRequest parameters,
			String chargeBoxIdentity) {
		log.info("Received ClearCacheRequest at {} " + chargeBoxIdentity);
		AuthorisationHandler authorisationHandler = chargePoint
				.getAuthorisationHandler();
		ClearCacheResponse response = authorisationHandler.handleClearCacheReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public UpdateFirmwareResponse updateFirmware(
			UpdateFirmwareRequest parameters, String chargeBoxIdentity) {
		log.info("Received UpdateFirmwareRequest at {} " + chargeBoxIdentity);
		FirmwareHandler handler = chargePoint.getFirmwareHandler();
		UpdateFirmwareResponse response = handler.handleUpdateFirmwareReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public ChangeConfigurationResponse changeConfiguration(
			ChangeConfigurationRequest parameters, String chargeBoxIdentity) {
		log.info("Received ChangeConfigurationRequest at {} "
				+ chargeBoxIdentity);
		ConfigurationHandler handler = chargePoint.getConfigurationHandler();
		ChangeConfigurationResponse response = handler.handleChangeConfigReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public RemoteStartTransactionResponse remoteStartTransaction(
			RemoteStartTransactionRequest parameters, String chargeBoxIdentity) {
		log.info("Received RemoteStartTransactionRequest at {} "
				+ chargeBoxIdentity);
		RemoteTransactionHandler handler = chargePoint
				.getRemoteTransactionHandler();
		RemoteStartTransactionResponse response = handler
				.handleRemoteStartTransactionReq(chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public RemoteStopTransactionResponse remoteStopTransaction(
			RemoteStopTransactionRequest parameters, String chargeBoxIdentity) {
		log.info("Received RemoteStopTransactionRequest at {} "
				+ chargeBoxIdentity);
		RemoteTransactionHandler handler = chargePoint
				.getRemoteTransactionHandler();
		RemoteStopTransactionResponse response = handler
				.handleRemoteStopTransactionReq(chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public CancelReservationResponse cancelReservation(
			CancelReservationRequest parameters, String chargeBoxIdentity) {
		log.info("Received CancelReservationRequest at {} " + chargeBoxIdentity);
		ReservationHandler handler = chargePoint.getReservationHandler();
		CancelReservationResponse response = handler
				.handleCancelReservationReq(chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public DataTransferResponse dataTransfer(DataTransferRequest parameters,
			String chargeBoxIdentity) {
		log.info("Received DataTransferRequest at {} " + chargeBoxIdentity);
		DataTransferHandler handler = chargePoint.getDataTransferHandler();
		DataTransferResponse response = handler.handleDataTransferReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public GetConfigurationResponse getConfiguration(
			GetConfigurationRequest parameters, String chargeBoxIdentity) {
		log.info("Received GetConfigurationRequest at {} " + chargeBoxIdentity);
		ConfigurationHandler handler = chargePoint.getConfigurationHandler();
		GetConfigurationResponse response = handler.handleGetConfigReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public GetLocalListVersionResponse getLocalListVersion(
			GetLocalListVersionRequest parameters, String chargeBoxIdentity) {
		log.info("Received GetLocalListVersionRequest at {} "
				+ chargeBoxIdentity);
		AuthorisationHandler handler = chargePoint.getAuthorisationHandler();
		GetLocalListVersionResponse response = handler
				.handleGetLocalListVersionReq(chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public ReserveNowResponse reserveNow(ReserveNowRequest parameters,
			String chargeBoxIdentity) {
		log.info("Received ReserveNowRequest at {} " + chargeBoxIdentity);
		ReservationHandler handler = chargePoint.getReservationHandler();
		ReserveNowResponse response = handler.handleReserveNowReq(
				chargeBoxIdentity, parameters);
		return response;
	}

	@Override
	public SendLocalListResponse sendLocalList(SendLocalListRequest parameters,
			String chargeBoxIdentity) {
		log.info("Received SendLocalListRequest at {} " + chargeBoxIdentity);
		AuthorisationHandler handler = chargePoint.getAuthorisationHandler();
		SendLocalListResponse response = handler.handleSendLocalListReq(
				chargeBoxIdentity, parameters);
		return response;
	}
}
