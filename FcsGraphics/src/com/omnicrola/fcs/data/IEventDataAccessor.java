package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;

public interface IEventDataAccessor {

	public abstract byte[] getBytes(Parameter parameter);

	public abstract double getDouble(Parameter parameter);

	public abstract float getFloat(Parameter parameter);

	public abstract int getInteger(Parameter parameter);

	public abstract ByteBuffer getFullBytes();

}