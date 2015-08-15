package com.omnicrola.fcs.data;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParameterArray {

	private final Parameter[] parameterArray;
	private int eventByteSize;
	private HashMap<Parameter, Integer> byteOffsetMap;
	private final ByteOrder byteOrder;

	public ParameterArray(List<Parameter> parametersToUse, ByteOrder byteOrder) {
		this.byteOrder = byteOrder;
		this.parameterArray = new Parameter[parametersToUse.size()];
		remap(parametersToUse);
		buildOffsetMap();
	}

	private void buildOffsetMap() {
		this.byteOffsetMap = new HashMap<Parameter, Integer>();
		int offset = 0;
		for (int i = 0; i < this.parameterArray.length; i++) {
			this.byteOffsetMap.put(this.parameterArray[i], offset);
			offset += this.parameterArray[i].getDataType().byteSize();
		}
	}

	public int getByteOffsetFor(Parameter parameter) {
		if (this.byteOffsetMap.containsKey(parameter)) {
			return this.byteOffsetMap.get(parameter);
		}
		return 0;
	}

	public ByteOrder getByteOrder() {
		return this.byteOrder;
	}

	public int getEventByteSize() {
		return this.eventByteSize;
	}

	public List<Parameter> getParameters() {
		return Arrays.asList(this.parameterArray);
	}

	private void remap(List<Parameter> parametersToUse) {
		this.eventByteSize = 0;
		for (final Parameter singleParameter : parametersToUse) {
			final int index = singleParameter.getIndex();
			this.eventByteSize += singleParameter.getDataType().byteSize();
			this.parameterArray[index] = singleParameter;
		}
	}
}
