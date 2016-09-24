package de.tudortmund.cni.ict4es.charge.simulation;

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
import java.util.List;
import java.util.Random;

public class Utils {

	private static Random randomGenerator = null;

	public static Object getRandomItemFromList(List<?> list) {
		randomGenerator = new Random();
		int index = randomGenerator.nextInt(list.size());
		Object object = list.get(index);
		return object;

	}

	public static int getRandomValueFromConfigProperty(String configurationValue) {
		randomGenerator = new Random();

		int value = 0;

		if (configurationValue.contains("-")) {

			String delims = "[-]";
			String[] values = configurationValue.split(delims);

			int min = Integer.valueOf(values[0]);
			int max = Integer.valueOf(values[1]);
			value = randomGenerator.nextInt(max - min) + min;

		} else {
			value = Integer.valueOf(configurationValue);
		}

		return value;
	}

}
