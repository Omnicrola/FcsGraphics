package com.omnicrola.fcs.data;

public interface IEventDataWriter {

	public abstract void put(byte value);

	public abstract void rewind();

}