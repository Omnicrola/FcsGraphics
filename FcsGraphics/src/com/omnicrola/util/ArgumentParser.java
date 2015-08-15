package com.omnicrola.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentParser {
	private static final HashMap<String, Argument> switchMap = getSwitchmap();
	private static final List<String> possibleArguments = getValidArguments();

	private static List<String> getValidArguments() {
		//@formatter:off
		return Arrays.asList(Argument.values()).stream()
					.map(v -> v.getCommandLineSwitch())
					.collect(Collectors.toList());
		//@formatter:on
	}

	private static HashMap<String, Argument> getSwitchmap() {
		final HashMap<String, Argument> hashMap = new HashMap<>();
		final List<Argument> asList = Arrays.asList(Argument.values());
		for (final Argument argument : asList) {
			hashMap.put(argument.getCommandLineSwitch(), argument);
		}
		return hashMap;
	}

	public static ProgramArguments parse(String[] args) {
		final HashMap<Argument, String> arguments = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			final String arg = args[i];
			if (isArgument(arg)) {
				final Argument argument = switchMap.get(arg);
				String val = "";
				if (args.length >= i + 1) {
					val = args[i + 1];
				}
				arguments.put(argument, val);
			}
		}
		return new ProgramArguments(arguments);
	}

	private static boolean isArgument(String string) {
		return possibleArguments.contains(string);
	}

}
