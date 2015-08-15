package com.omnicrola.fcs.data;

import java.nio.ByteOrder;
import java.util.ArrayList;

public class SampleSettings {

	public static final SampleSettings DEFAULT = buildDefault();

	private static SampleSettings buildDefault() {
		final int capacity = 100_000;
		final ArrayList<Parameter> parameterList = new ArrayList<Parameter>();
		for (int i = 0; i < 14; i++) {
			parameterList.add(Parameter.makeDefault(i));
		}
		final ParameterArray arrayOfNulls = new ParameterArray(parameterList, ByteOrder.BIG_ENDIAN);
		return new SampleSettings(capacity, arrayOfNulls);
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
