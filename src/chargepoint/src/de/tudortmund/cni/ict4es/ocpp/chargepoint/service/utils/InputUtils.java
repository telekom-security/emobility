package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils;

/**
 * @author Jens Schmutzler <jens.schmutzler@tu-dortmund.de>
 * @author Thomas Grabowski <thomas.grabowski@tu-dortmund.de>
 * @author Mohamad Sbeiti <mohamad.sbeiti@telekom.de>
 * 
 *         This program is free software; you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation; either version 2 of the License, or (at
 *         your option) any later version. For further information see file
 *         COPYING in the top level directory
 * 
 ********************************************************************************
 *         This work is a joint work between Communication Networks Institute
 *         (CNI - Prof. Dr.-Ing. Christian Wietfeld) at Technische Universitaet
 *         Dortmund, Germany and the Deutsche Telekom
 * ********************************************************************************/
public class InputUtils {

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

}
