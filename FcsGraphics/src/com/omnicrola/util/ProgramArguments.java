package com.omnicrola.util;

import java.util.HashMap;

public class ProgramArguments {

	private final HashMap<Argument, String> arguments;

	public ProgramArguments(HashMap<Argument, String> arguments) {
		this.arguments = arguments;
	}

	public String get(Argument argument) {
		return this.arguments.get(argument);
	}

	public boolean isNotPresent(Argument argument) {
		return this.arguments.get(argument) == null;
	}

	public void set(Argument argument, String value) {
		this.arguments.put(argument, value);
	}

}
