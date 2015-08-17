package com.omnicrola.util;

public enum Argument {
	//@formatter:off
	SOURCE_IMAGE("-image"),
	TARGET_FILENAME("-fcs");

	//@formatter:on

	private final String commandLineSwitch;

	private Argument(String commandLineSwitch) {
		this.commandLineSwitch = commandLineSwitch;
	}

	public String getCommandLineSwitch() {
		return this.commandLineSwitch;
	}
}
