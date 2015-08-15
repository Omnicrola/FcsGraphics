package com.omnicrola.util;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramArguments {

	private final HashMap<Argument, String> arguments;

	public ProgramArguments(HashMap<Argument, String> arguments) {
		this.arguments = arguments;
	}

	public void assertPresent(Argument... arguments) {
		final ArrayList<String> errors = new ArrayList<>();
		for (final Argument argument : arguments) {
			if (this.arguments.get(argument) == null) {
				errors.add("Argument missing : " + argument);
			}
		}
		if (errors.size() > 0) {
			for (final String error : errors) {
				System.err.println(error);
			}
			System.exit(10);
		}
	}

	public String get(Argument argument) {
		return this.arguments.get(argument);
	}

}
