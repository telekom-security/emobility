package de.tudortmund.cni.ict4es.config.utils;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DatabaseUtils {

	private static Connection conn = null;

	public static boolean dbConnect(String url, String user, String password) {

		try {
			// create a mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + url;
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl.trim(), user.trim(), password.trim());
		} catch (Exception e) {
			System.err.println("Exception! " + e.toString());
			return false;
		}
		return true;
	}

	public static void closeDBConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean query(String sqlQuery) {

		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate(sqlQuery);

		} catch (SQLException e) {
			System.err.println("Exception! " + e.toString());
			return false;
		}

		return true;

	}

	public static int countData(String tableName) {

		int rowcount = 0;
		ResultSet resultSet = null;

		try {
			Statement st = (Statement) conn.createStatement();
			resultSet = st.executeQuery("SELECT COUNT(*) FROM " + tableName);
			resultSet.next();
			rowcount = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowcount;

	}

	public static void deleteData(String tableName) {

		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("DELETE FROM " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
