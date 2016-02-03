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
package de.tudortmund.cni.ict4es.config;

import java.util.Scanner;

import de.tudortmund.cni.ict4es.config.cp.ChargePointGenerator;
import de.tudortmund.cni.ict4es.config.user.UserGenerator;
import de.tudortmund.cni.ict4es.config.utils.DatabaseUtils;

public class MainGenerator {

	private final static String CONFIG_BASE_DIR = "properties/";
	private final static String CONFIG_FILE_NAME = "generator_configuration.properties";

	private GeneratorConfiguration generatorConfiguration = null;

	private boolean loadConfiguration(String propertyFileName) {
		System.out.println("Loading Generator configuration file from " + propertyFileName);
		generatorConfiguration = new GeneratorConfiguration();
		return generatorConfiguration.loadProperties(propertyFileName);
	}

	public static void main(String[] args) {

		System.out.println("Start OCPP Genarator...");

		boolean isConnectToCSDB = false;

		MainGenerator mainGenerator = new MainGenerator();
		if (!mainGenerator.loadConfiguration(CONFIG_BASE_DIR + CONFIG_FILE_NAME))
			return;

		System.out.println("\n----------------OCPP Generator Configuration---------------------------");
		System.out.println("CS: URL: " + mainGenerator.generatorConfiguration.getCentralSystemDBUrl());
		System.out.println("CS: user: " + mainGenerator.generatorConfiguration.getCentralSystemDBUser());
		System.out.println("CS: password: " + mainGenerator.generatorConfiguration.getCentralSystemDBPassword());
		System.out.println();

		System.out.println("CP: enableImport: " + mainGenerator.generatorConfiguration.csEnableCPImport());
		System.out.println("CP: deleteData: " + mainGenerator.generatorConfiguration.csDeleteCPData());
		System.out.println();

		System.out.println("CP: startConfigIndex: " + mainGenerator.generatorConfiguration.getStartConfigIndex());
		System.out.println("CP: numberOfChargePoints: " + mainGenerator.generatorConfiguration.getNumberOfChargePoints());

		System.out.println("CP: chargePointWebServiceIPv4: " + mainGenerator.generatorConfiguration.getChargePointWebServiceIPv4());
		System.out.println("CP: chargePointWebServicePorteConfig: " + mainGenerator.generatorConfiguration.getChargePointWebServicePort());

		System.out.println("CP: centralSystemWebServiceURLConfig: " + mainGenerator.generatorConfiguration.getCentralSystemWebServiceURL());
		System.out.println("CP: centralSystemWebServiceURIConfig: " + mainGenerator.generatorConfiguration.getCentralSystemWebServiceURI());
		System.out.println("CP: centralSystemWebServiceNameConfig: " + mainGenerator.generatorConfiguration.getCentralSystemWebServiceName());

		System.out.println("CP: numberOfConnectors: " + mainGenerator.generatorConfiguration.getNumberOfConnectors());

		System.out.println("CP: chargeBoxIdentityConfig: " + mainGenerator.generatorConfiguration.getChargeBoxIdentity());
		System.out.println("CP: chargeBoxSerialNumberConfig: " + mainGenerator.generatorConfiguration.getChargeBoxSerialnumber());

		System.out.println("CP: chargePointModelConfig: " + mainGenerator.generatorConfiguration.getChargePointModel());
		System.out.println("CP: chargePointVendorConfig: " + mainGenerator.generatorConfiguration.getChargePointVendor());
		System.out.println("CP: chargePointSerialNumberConfig: " + mainGenerator.generatorConfiguration.getChargePointSerialNumber());

		System.out.println("CP: firmwareVersionConfig: " + mainGenerator.generatorConfiguration.getFirmwareVersion());
		System.out.println("CP: iccidConfig: " + mainGenerator.generatorConfiguration.getICCID());
		System.out.println("CP: imsiConfig: " + mainGenerator.generatorConfiguration.getIMSI());
		System.out.println("CP: meterSerialNumberConfig: " + mainGenerator.generatorConfiguration.getMeterSerialNumber());
		System.out.println("CP: meterTypeConfig: " + mainGenerator.generatorConfiguration.getMeterType());

		System.out.println();
		System.out.println("USER: enableImport: " + mainGenerator.generatorConfiguration.csEnableUserImport());
		System.out.println("USER: deleteData: " + mainGenerator.generatorConfiguration.csDeleteUserData());
		System.out.println("USER: numberOfUsers: " + mainGenerator.generatorConfiguration.getNumberOfUsers());
		System.out.println("USER: idTagLength: " + mainGenerator.generatorConfiguration.getUserIdTagLength());
		System.out.println("----------------Generator Configuration---------------------------\n");

		// CP or User DB Import enabled
		if (mainGenerator.generatorConfiguration.csEnableUserImport() || mainGenerator.generatorConfiguration.csEnableCPImport()) {
			System.out.print("DB: Testing Central System DB connection...");
			isConnectToCSDB = DatabaseUtils.dbConnect(mainGenerator.generatorConfiguration.getCentralSystemDBUrl(), mainGenerator.generatorConfiguration.getCentralSystemDBUser(), mainGenerator.generatorConfiguration.getCentralSystemDBPassword());

			if (isConnectToCSDB) {
				System.out.print("-> connection established.\n\n");
			} else {
				System.err.print("-> connection FAILED! -> Check DB connection properties.\n\n");
				return;
			}

		}

		Scanner scanner = new Scanner(System.in);

		ChargePointGenerator chargePointGenerator = new ChargePointGenerator(mainGenerator.generatorConfiguration, scanner);
		chargePointGenerator.generate();

		UserGenerator userGenerator = new UserGenerator(mainGenerator.generatorConfiguration, scanner);
		userGenerator.generate();

		if (isConnectToCSDB) {
			DatabaseUtils.closeDBConnection();
		}

		scanner.close();

	}

}
