package de.tudortmund.cni.ict4es.config.cp;
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
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

import de.tudortmund.cni.ict4es.config.GeneratorConfiguration;
import de.tudortmund.cni.ict4es.config.utils.DatabaseUtils;
import de.tudortmund.cni.ict4es.config.utils.XMLUtils;

public class ChargePointGenerator {

	private static final Logger log = Logger.getLogger(ChargePointGenerator.class.getName());

	private int startConfigIndex;
	private int numberOfChargePoints;

	private String chargePointWebServiceIPv4Config;
	private String chargePointWebServicePortConfig;

	private String centralSystemWebServiceURLConfig;
	private String centralSystemWebServiceURIConfig;
	private String centralSystemWebServiceNameConfig;

	private String numberOfConnectors;

	private String chargeBoxIdentityConfig;
	private String chargeBoxSerialNumberConfig;

	private String chargePointModelConfig;
	private String chargePointVendorConfig;
	private String chargePointSerialNumberConfig;

	private String firmwareVersionConfig;
	private boolean iccidConfig;
	private boolean imsiConfig;
	private String meterSerialNumberConfig;
	private String meterTypeConfig;

	private Random random = null;

	private ChargePointItem chargePointItem = null;

	private String[] mncValues = { "01", "02", "03", "04", "05", "06", "07", "08", "09" };

	private String[] cpIdentity = {"collingstr_2_neuss","moselstr_25_neuss", "moselstr_27_neuss", "franz_schuetz_platz_meerbusch", "hessenweg_23_meerbusch", "an_der_piwipp_86_duesseldorf", "immermannstr_45_duesseldorf", "hoeherweg_101_duesseldorf", "hoeherweg_111_duesseldorf", "metrostr_6_duesseldorf", "metrostr_8_duesseldorf" };
	private boolean csEnableCPImport = false;
	private boolean csDeleteCPData = false;

	private int numberOfCPsInsertedToCS = 0;

	private Scanner scanner = null;

	public ChargePointGenerator(GeneratorConfiguration generatorConfiguration, Scanner scanner) {
		random = new Random();
		this.scanner = scanner;

		csEnableCPImport = generatorConfiguration.csEnableCPImport();
		csDeleteCPData = generatorConfiguration.csDeleteCPData();

		startConfigIndex = generatorConfiguration.getStartConfigIndex();
		numberOfChargePoints = generatorConfiguration.getNumberOfChargePoints();

		chargePointWebServiceIPv4Config = generatorConfiguration.getChargePointWebServiceIPv4();
		chargePointWebServicePortConfig = generatorConfiguration.getChargePointWebServicePort();

		centralSystemWebServiceURLConfig = generatorConfiguration.getCentralSystemWebServiceURL();
		centralSystemWebServiceURIConfig = generatorConfiguration.getCentralSystemWebServiceURI();
		centralSystemWebServiceNameConfig = generatorConfiguration.getCentralSystemWebServiceName();

		numberOfConnectors = generatorConfiguration.getNumberOfConnectors();

		chargeBoxIdentityConfig = generatorConfiguration.getChargeBoxIdentity();
		chargeBoxSerialNumberConfig = generatorConfiguration.getChargeBoxSerialnumber();

		chargePointModelConfig = generatorConfiguration.getChargePointModel();
		chargePointVendorConfig = generatorConfiguration.getChargePointVendor();
		chargePointSerialNumberConfig = generatorConfiguration.getChargePointSerialNumber();

		firmwareVersionConfig = generatorConfiguration.getFirmwareVersion();
		iccidConfig = generatorConfiguration.getICCID();
		imsiConfig = generatorConfiguration.getIMSI();
		meterSerialNumberConfig = generatorConfiguration.getMeterSerialNumber();
		meterTypeConfig = generatorConfiguration.getMeterType();

	}

	private void deleteCSChargePointData() {

		if (csEnableCPImport) {

			if (csDeleteCPData) {

				int dataRows = DatabaseUtils.countData("chargebox");

				if (dataRows == 0) {

					System.out.println("CP: No Charge Point data found in Central System DB -> start import.");

				} else {

					System.out.print("CP: FOUND " + dataRows + " Charge Points in Central System DB -> Delete? [y/n]: ");

					String input = scanner.next();

					if (input.trim().equals("y")) {

						DatabaseUtils.deleteData("chargebox");

					} else if (input.trim().equals("n")) {

						System.out.println("CP: Skip delete Charge Point data.");

					} else {
						System.out.println("CP: Unknown command " + input + ", skip delete Charge Point data.");
					}

				}

			}

		}

	}

	public void generate() {

		System.out.println("CP: Start to generate " + numberOfChargePoints + " Charge Point configurations. Central System insert?=" + csEnableCPImport + ".");

		deleteCSChargePointData();

		int port = Integer.valueOf(chargePointWebServicePortConfig);
		int chargeBoxId = Integer.valueOf(chargeBoxIdentityConfig);

		for (int i = 0; i < numberOfChargePoints; i++) {

			String cpServiceWebServiceIPv4 = "10.0.1." + (i+10)+ ":" + port;
			String numOfConnectors = setNumberOfConnectors(numberOfConnectors);
			String cbIdentity = cpIdentity[i];
			//String cbIdentity = String.valueOf(chargeBoxId);
			String cbSerialNumber = generateSerialNumber(chargeBoxSerialNumberConfig);
			String cpModel = setValue(chargePointModelConfig);
			String cpVendor = setValue(chargePointVendorConfig);
			String cpSerialNumber = generateSerialNumber(chargePointSerialNumberConfig);
			String firmwareVersion = setFirmwareVersion(firmwareVersionConfig);
			String meterSerialNumber = generateSerialNumber(meterSerialNumberConfig);
			String meterType = setValue(meterTypeConfig);
			String iccid = generateICCID(iccidConfig);
			String imsi = generateIMSI(imsiConfig);

			chargePointItem = new ChargePointItem(cpServiceWebServiceIPv4, centralSystemWebServiceURLConfig, centralSystemWebServiceURIConfig, centralSystemWebServiceNameConfig, numOfConnectors, cbIdentity, cbSerialNumber, cpModel, cpVendor, cpSerialNumber, firmwareVersion, iccid, imsi,
					meterSerialNumber, meterType);

			XMLUtils.createChargePointConfigXMLFile(chargePointItem, startConfigIndex);

			if (csEnableCPImport) {

				if (DatabaseUtils.query("INSERT INTO chargebox(chargeBoxId) VALUES" + "('"+cbIdentity+"')")) {
					System.out.println("CP: Charge Point with ChargeBoxId " + cbIdentity + " was insert to Central System DB");
					numberOfCPsInsertedToCS++;
				} else {
					System.err.println("CP: Charge Point with ChargeBoxId " + cbIdentity + " was NOT insert to Central System DB");
				}
			}

			startConfigIndex++;
			//port++;
			chargeBoxId++;

		}

		System.out.println("CP: Generated " + numberOfChargePoints + " Charge Point configurations.");

		if (csEnableCPImport)
			System.out.println("CP: Inserted " + numberOfCPsInsertedToCS + " Charge Point to Central System DB.");

	}

	private String setNumberOfConnectors(String numberOfConnectorsConfig) {

		String numberOfConnectors = "";

		if (numberOfConnectorsConfig.contains("-")) {

			String delims = "[-]";
			String[] values = numberOfConnectorsConfig.split(delims);

			int min = Integer.valueOf(values[0]);
			int max = Integer.valueOf(values[1]);

			numberOfConnectors = String.valueOf(getRandomInteger(max + 1, min));

		} else if (numberOfConnectorsConfig.contains(";")) {

			String delims = "[;]";
			String[] values = numberOfConnectorsConfig.split(delims);

			int ran = random.nextInt((values.length - 1) + 1);
			numberOfConnectors = values[ran];

		} else {

			numberOfConnectors = numberOfConnectorsConfig;
		}

		return numberOfConnectors;
	}

	private String generateSerialNumber(String serialNumberConfig) {

		String serialNumber = "";

		if (serialNumberConfig.contains("-")) {

			String delims = "[-]";
			String[] values = serialNumberConfig.split(delims);

			int start = Integer.valueOf(values[0]);
			int stop = Integer.valueOf(values[1]);

			int ran = random.nextInt((stop - start) + 1);
			serialNumber = String.valueOf(ran);
		}

		return serialNumber;

	}

	private String setValue(String config) {

		String model = "";

		if (config.contains(";")) {

			String delims = "[;]";
			String[] values = config.split(delims);

			int ran = random.nextInt((values.length - 1) + 1);
			model = values[ran];

		} else {

			model = config;
		}

		return model;

	}

	private String setFirmwareVersion(String firmwareVersionConfig) {

		String version = "";

		if (firmwareVersionConfig.contains("-")) {

			String delims = "[-]";
			String[] values = firmwareVersionConfig.split(delims);

			double start = Double.valueOf(values[0]);
			double end = Double.valueOf(values[1]);

			double random = new Random().nextDouble();
			double result = start + (random * (end - start));
			version = String.valueOf(result);

		} else {
			version = firmwareVersionConfig;
		}

		return version.substring(0, 4);

	}

	private String generateIMSI(boolean generate) {

		String imsi = "262019876543210";

		if (generate) {

			int mncRandomIndex = random.nextInt((mncValues.length - 1) + 1);
			String mnc = mncValues[mncRandomIndex];

			int msinRandom = random.nextInt((999999 - 1000) + 1);

			imsi = "262" + mnc + String.valueOf(msinRandom);

		}

		return imsi;

	}

	private String generateICCID(boolean generate) {

		String iccid = "8927603123456789000";

		if (generate) {

			StringBuilder builder = new StringBuilder("8927603");

			for (int i = 0; i < 12; i++) {
				int randomNum = random.nextInt((9 - 1) + 1);
				builder.append(String.valueOf(randomNum));
			}

			iccid = builder.toString();

		}

		return iccid;

	}

	public static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

}
