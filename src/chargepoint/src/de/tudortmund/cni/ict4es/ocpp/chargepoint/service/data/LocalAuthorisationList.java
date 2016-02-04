package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.data;

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
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @created 27-Jan-2015 16:03:00
 */
public class LocalAuthorisationList {

	private int version;
	private List<User> userList = new ArrayList<User>();

	public LocalAuthorisationList() {

	}

	public void finalize() throws Throwable {

	}

	public int getVersion() {
		return version;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setVersion(int newVal) {
		version = newVal;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}// end LocalAuthorisationList