package com.omnicrola.fcs.data;

public interface IEventDataAccessor {

	public abstract byte[] getBytes(Parameter parameter);

	public abstract double getDouble(Parameter parameter);

	public abstract float getFloat(Parameter parameter);

	public abstract int getInteger(Parameter parameter);

}