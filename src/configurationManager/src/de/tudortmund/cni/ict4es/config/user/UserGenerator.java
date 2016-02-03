package de.tudortmund.cni.ict4es.config.user;
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
import java.util.Scanner;

import de.tudortmund.cni.ict4es.config.GeneratorConfiguration;
import de.tudortmund.cni.ict4es.config.utils.DatabaseUtils;
import de.tudortmund.cni.ict4es.config.utils.XMLUtils;

public class UserGenerator {

	private LocalAuthorisationList localAuthorisationList = null;
	private UserItem userItem = null;

	private boolean csEnableUserImport = false;
	private boolean csDeleteUserData = false;
	private int idTagLength = 0;
	private int numberOfUsers = 0;
	private int numberOfUsersInsertedToCS = 0;

	private Scanner scanner = null;
	private String[] usIdentity = { "aguenes", "afriedman","akitzmann","bgeorg","bschimanzki","cmueller", "cmueller2","cposchmann", "cschroer","cwilliams","dbecker","dfleischer","dneubauer","eajjour", "ebosch", "eoffergeld", "jbida","jwinkel","jsommer","mclemens","mhertzog","moswald","mschwab","msilbermann","srosenthal", "wknez","wpauli","wtran"};
	
	public UserGenerator(GeneratorConfiguration generatorConfiguration, Scanner scanner) {
		csEnableUserImport = generatorConfiguration.csEnableUserImport();
		csDeleteUserData = generatorConfiguration.csDeleteUserData();
		idTagLength = generatorConfiguration.getUserIdTagLength();
		numberOfUsers = generatorConfiguration.getNumberOfUsers();
		localAuthorisationList = new LocalAuthorisationList();
		this.scanner = scanner;
	}

	public void generate() {

		System.out.println("\nUSER: Start to generate " + numberOfUsers + " Users. Central System insert?=" + csEnableUserImport + ".");

		deleteCSUserData();
		localAuthorisationList.setVersion(111);

		for (int i = 0; i < numberOfUsers; i++) {

		//	userItem = new UserItem(UserUtils.generateUserIdTag(idTagLength), "", "", "", false, false);
			userItem = new UserItem(usIdentity[i], "", "", "", false, false);
			localAuthorisationList.getUserList().add(userItem);

			if (csEnableUserImport)
				insertUserToCSDB(userItem);

		}

		XMLUtils.createLocalAuthorisationListXMLFile(localAuthorisationList);

		System.out.println("USER: Generated " + numberOfUsers + " Users.");

		if (csEnableUserImport)
			System.out.println("USER: Inserted " + numberOfUsersInsertedToCS + " Users to Central System DB.");

	}

	private void insertUserToCSDB(UserItem userItem) {

		String inTransaction = (userItem.isInTransaction()) ? "1" : "0";
		String isBlocked = (userItem.isBlocked()) ? "1" : "0";

		if (DatabaseUtils.query("INSERT INTO user (idTag, inTransaction, blocked) VALUES (" + "'"+userItem.getUserIdTag()+"'" + "," + inTransaction + "," + isBlocked + ")")) {
			System.out.println("USER: User with idTag " + userItem.getUserIdTag() + " was inserted to Central System DB");
			numberOfUsersInsertedToCS++;
		} else {
			System.err.println("USER: User with idTag " + userItem.getUserIdTag() + " was NOT inserted to Central System DB");
		}

	}

	public void deleteCSUserData() {

		if (csEnableUserImport) {

			if (csDeleteUserData) {

				int dataRows = DatabaseUtils.countData("user");

				if (dataRows == 0) {
					System.out.println("USER: No User data found in Central System DB -> start import.");

				} else {

					System.out.print("USER: FOUND " + dataRows + " Users in Central System DB -> Delete? [y/n]: ");

					String input = scanner.next();

					if (input.toLowerCase().trim().equals("y")) {

						DatabaseUtils.deleteData("user");

					} else if (input.toLowerCase().trim().equals("n")) {

						System.out.println("USER: Skip delete User data.");

					} else {
						System.out.println("USER: Unknown command " + input + ", skip delete User data.");
					}

				}

			}

		}

	}

}
