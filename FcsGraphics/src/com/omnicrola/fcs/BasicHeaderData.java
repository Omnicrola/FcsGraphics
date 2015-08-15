package com.omnicrola.fcs;

import java.util.HashMap;

public class BasicHeaderData {

	public static final String STANDARD = "$";
	public static final String CUSTOM = "#";
	public static final String TOTAL_EVENTS = STANDARD + "TOT";
	public static final String PARAMETER_COUNT = STANDARD + "PAR";
	public static final String DATE = STANDARD + "DATE";
	public static final String CYTOMETER_NAME = STANDARD + "CYT";
	public static final String BEGIN_TIME = STANDARD + "BTIM";
	public static final String END_TIME = STANDARD + "ETIM";

	public static HashMap<String, String> getHeaders() {
		final HashMap<String, String> headers = new HashMap<>();
		headers.put(STANDARD + "MODE", "L");
		headers.put(PARAMETER_COUNT, "0");
		headers.put(STANDARD + "BYTEORD", "4,3,2,1");
		headers.put(STANDARD + "DATATYPE", "I");
		headers.put(STANDARD + "VOL", "5150");
		headers.put(STANDARD + "TIMESTEP", "0.01");
		headers.put(TOTAL_EVENTS, "0");

		return headers;
	}

}
