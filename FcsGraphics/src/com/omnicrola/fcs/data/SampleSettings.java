package com.omnicrola.fcs.data;

import java.nio.ByteOrder;
import java.util.ArrayList;

public class SampleSettings {

	private static final int THREE_MILLION = 3_000_000;
	private static final int PARAMETER_COUNT = 14;
	private static final String[] defaultnames = buildDefaultNames();
	public static final SampleSettings DEFAULT = buildDefault();

	private static SampleSettings buildDefault() {
		final int capacity = THREE_MILLION;
		final ArrayList<Parameter> parameterList = new ArrayList<Parameter>();
		for (int i = 0; i < PARAMETER_COUNT; i++) {
			parameterList.add(Parameter.makeDefault(i, defaultnames[i]));
		}
		final ParameterArray arrayOfNulls = new ParameterArray(parameterList, ByteOrder.BIG_ENDIAN);
		return new SampleSettings(capacity, arrayOfNulls);
	}

	private static String[] buildDefaultNames() {
		final String[] names = new String[PARAMETER_COUNT];
		names[0] = "FSC-A";
		names[1] = "SSC-A";
		names[2] = "FL1-A";
		names[3] = "FL2-A";
		names[4] = "FL3-A";
		names[5] = "FL4-A";

		names[6] = "FSC-H";
		names[7] = "SSC-H";
		names[8] = "FL1-H";
		names[9] = "FL2-H";
		names[10] = "FL3-H";
		names[11] = "FL4-H";

		names[12] = "Width";
		names[13] = "Time";

		return names;
	}

	private final ParameterArray parameterArray;

	private final int eventCapacity;

	public SampleSettings(int eventCapacity, ParameterArray parameterArray) {
		this.eventCapacity = eventCapacity;
		this.parameterArray = parameterArray;
	}

	public int getEventCapacity() {
		return this.eventCapacity;
	}

	public ParameterArray getParameterArray() {
		return this.parameterArray;
	}
}
