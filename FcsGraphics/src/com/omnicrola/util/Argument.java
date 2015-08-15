package com.omnicrola.util;

public enum Argument {
	SOURCE_IMAGE("-image"), TARGET_FILENAME("-fcs");

	private final String commandLineSwitch;

	private Argument(String commandLineSwitch) {
		this.commandLineSwitch = commandLineSwitch;
	}

	public String getCommandLineSwitch() {
		return this.commandLineSwitch;
	}
}
