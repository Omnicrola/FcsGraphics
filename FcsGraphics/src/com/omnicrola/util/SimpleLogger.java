package com.omnicrola.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {

	private static final String NAME = "Simple";
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss.SSSS");

	public static void log(String message) {
		final String dateStamp = simpleDateFormat.format(new Date());
		final String logMessage = String.format("[%s] %s", dateStamp, message);
		System.out.println(logMessage);
		Logger.getLogger(NAME).log(Level.ALL, logMessage);
	}

	public static void error(Throwable exception) {
		exception.printStackTrace();
		Logger.getLogger(NAME).log(Level.ALL, "Error", exception);
	}

}
