package com.omnicrola.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramArguments {

	private final HashMap<Argument, List<String>> arguments;

	public ProgramArguments(HashMap<Argument, List<String>> arguments) {
		this.arguments = arguments;
	}

	public List<String> getAll(Argument argument) {
		return new ArrayList<>(this.arguments.get(argument));
	}

	public boolean isNotPresent(Argument argument) {
		return this.arguments.get(argument).isEmpty();
	}

	public void set(Argument argument, String value) {
		this.arguments.get(argument).add(value);
	}

	public String getSingle(Argument argument) {
		if (isNotPresent(argument)) {
			return "";
		}
		return this.arguments.get(argument).get(0);
	}

}
