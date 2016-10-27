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
import java.util.Random;

public class UserUtils {

	public static String generateUserIdTag(int idTagLength) {

		StringBuilder builder = new StringBuilder();
		Random random = new Random();

		for (int j = 0; j < idTagLength; j++) {
			int randomNum = random.nextInt((9) + 1);
			builder.append(String.valueOf(randomNum));
		}

		return builder.toString();
	}

}
