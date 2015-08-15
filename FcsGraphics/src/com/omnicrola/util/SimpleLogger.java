package com.omnicrola.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {

	private static final String NAME = "Simple";

	public static void log(String message) {
		System.out.println(message);
		Logger.getLogger(NAME).log(Level.ALL, message);
	}

}
