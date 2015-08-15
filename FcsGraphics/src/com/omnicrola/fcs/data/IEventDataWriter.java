package com.omnicrola.fcs.data;

public interface IEventDataWriter {

	public abstract void putNextValue(byte value);

	public abstract void rewind();

}