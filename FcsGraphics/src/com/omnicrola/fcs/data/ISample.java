package com.omnicrola.fcs.data;

public interface ISample {

	public abstract void addEvent(byte[] eventData);

	public abstract void clearDataAndUseNewSettings(SampleSettings newSettings);

	public abstract SampleIterator getEventIterator();

	public abstract SampleIterator getEventIterator(Momento eventMomento);

	public abstract int getIndex();

	public abstract ParameterArray getParameterArray();

	public abstract int getTotalEvents();

}