package com.omnicrola.util;

import java.util.ArrayList;
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
		final HashMap<Argument, List<String>> arguments = buildMap();

		for (int i = 0; i < args.length; i++) {
			getArgument(args, arguments, i);
		}
		return new ProgramArguments(arguments);
	}

	private static void getArgument(String[] args, final HashMap<Argument, List<String>> arguments, int index) {
		final String arg = args[index];
		if (isArgument(arg)) {
			saveArgument(args, arguments, index, arg);
		}
	}

	private static void saveArgument(String[] args, final HashMap<Argument, List<String>> arguments, int index,
	        final String arg) {
		final Argument argument = switchMap.get(arg);
		String val = "";
		final List<String> argumentValues = arguments.get(argument);
		for (int j = index + 1; j < args.length; j++) {
			val = args[j];
			if (val.substring(0, 1).equals("-")) {
				break;
			}
			argumentValues.add(val);

		}
	}

	private static HashMap<Argument, List<String>> buildMap() {
		final HashMap<Argument, List<String>> hashMap = new HashMap<>();
		for (final Argument argument : Argument.values()) {
			hashMap.put(argument, new ArrayList<>());
		}
		return hashMap;
	}

	private static boolean isArgument(String string) {
		return possibleArguments.contains(string);
	}

}
