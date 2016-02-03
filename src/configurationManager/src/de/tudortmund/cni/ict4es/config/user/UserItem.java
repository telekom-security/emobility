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
public class UserItem {

	private String userIdTag;
	private String parentIdTag;
	private String expireDate;
	private String status;
	private boolean inTransaction = false;
	private boolean isBlocked = false;

	public UserItem(String userIdTag, String parentIdTag, String expireDate, String status, boolean inTransaction, boolean isBlocked) {
		this.userIdTag = userIdTag;
		this.parentIdTag = parentIdTag;
		this.expireDate = expireDate;
		this.status = status;
		this.inTransaction = inTransaction;
		this.isBlocked = isBlocked;

	}

	public String getUserIdTag() {
		return userIdTag;
	}

	public String getParentIdTag() {
		return parentIdTag;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public String getStatus() {
		return status;
	}

	public boolean isInTransaction() {
		return inTransaction;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

}
