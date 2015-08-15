package com.omnicrola.fcs;

import java.util.HashMap;

public class BasicHeaderData {

	public static final String REQUIRED = "$";
	public static final String OPTIONAL = "#";
	public static final String TOTAL_EVENTS = REQUIRED + "TOT";
	public static final String PARAMETER_COUNT = REQUIRED + "PAR";

	public static HashMap<String, String> getHeaders() {
		final HashMap<String, String> headers = new HashMap<>();
		headers.put(REQUIRED + "MODE", "L");
		headers.put(PARAMETER_COUNT, "0");
		headers.put(REQUIRED + "BYTEORD", "4,3,2,1");
		headers.put(REQUIRED + "DATATYPE", "I");
		headers.put(TOTAL_EVENTS, "0");

		return headers;
	}

}
