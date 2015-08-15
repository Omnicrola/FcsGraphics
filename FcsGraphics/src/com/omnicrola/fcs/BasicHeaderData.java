package com.omnicrola.fcs;

import java.util.HashMap;

public class BasicHeaderData {

	public static final String REQUIRED = "$";
	public static final String OPTIONAL = "#";

	public static HashMap<String, String> getHeaders(int parameterCount) {
		final HashMap<String, String> headers = new HashMap<>();
		headers.put(REQUIRED + "MODE", "L");
		headers.put(REQUIRED + "PAR", String.valueOf(parameterCount));
		headers.put(REQUIRED + "BYTEORD", "4,3,2,1");
		headers.put(REQUIRED + "DATATYPE", "I");
		headers.put(REQUIRED + "TOT", "0");
		createParameters(parameterCount, headers);

		return headers;
	}

	private static void createParameters(int count, HashMap<String, String> headers) {
		for (int i = 0; i < count; i++) {
			// bytes for this parameters
			headers.put(REQUIRED + "P" + i + "B", "32");

			// amplification type
			headers.put(REQUIRED + "P" + i + "E", "0,0");

			// maximum range for this parameter
			headers.put(REQUIRED + "P" + i + "R", "16777216");

			// Short Name
			headers.put(REQUIRED + "P" + i + "N", "P-" + i);
		}
	}

}
