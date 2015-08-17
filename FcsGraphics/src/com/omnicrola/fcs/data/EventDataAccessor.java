package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class EventDataAccessor implements IEventDataAccessor, IEventDataWriter {

	private final byte[] eventData;
	private final DataBitShifter bitShifter;
	private final ParameterArray parameterArray;
	private int writePointer;

	public EventDataAccessor(SampleSettings settings, DataBitShifter bitShifter) {
		this.parameterArray = settings.getParameterArray();
		this.bitShifter = bitShifter;
		final int eventByteSize = settings.getParameterArray().getEventByteSize();
		this.eventData = new byte[eventByteSize];
	}

	@Override
	public byte[] getBytes(Parameter parameter) {
		return sliceData(parameter);
	}

	@Override
	public ByteBuffer getFullBytes() {
		final ByteBuffer buffer = ByteBuffer.allocate(this.eventData.length).put(this.eventData);
		buffer.rewind();
		return buffer;
	}

	@Override
	public double getDouble(Parameter parameter) {
		final byte[] dataSubsection = sliceData(parameter);
		return this.bitShifter.translateToDouble(dataSubsection);
	}

	@Override
	public float getFloat(Parameter parameter) {
		final byte[] dataSubsection = sliceData(parameter);
		return this.bitShifter.translateToFloat(dataSubsection, this.parameterArray.getByteOrder());
	}

	@Override
	public int getInteger(Parameter parameter) {
		final byte[] dataSubsection = sliceData(parameter);
		return this.bitShifter.translateToInteger(dataSubsection, this.parameterArray.getByteOrder());
	}

	@Override
	public void putNextValue(byte value) {
		this.eventData[this.writePointer] = value;
		this.writePointer++;
	}

	@Override
	public void rewind() {
		this.writePointer = 0;
	}

	private byte[] sliceData(Parameter parameter) {
		final int offset = this.parameterArray.getByteOffsetFor(parameter);
		final int byteSize = parameter.getDataType().byteSize();
		final byte[] dataSubsection = Arrays.copyOfRange(this.eventData, offset, offset + byteSize);
		return dataSubsection;
	}
}
