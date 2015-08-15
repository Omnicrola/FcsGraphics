package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;

public class Sample implements ISample {

	private final MemoryAllocator allocator;
	private final int index;
	private ByteBuffer memoryBuffer;
	private SampleSettings settings;
	private ParameterArray parameterList;
	private int totalEvents;

	public Sample(MemoryAllocator allocator, int index) {
		this.index = index;
		this.allocator = allocator;
		this.memoryBuffer = ByteBuffer.allocate(1);
		clearDataAndUseNewSettings(SampleSettings.DEFAULT);
	}

	@Override
	public void addEvent(byte[] eventData) {
		for (int i = 0; i < eventData.length; i++) {
			final byte b = eventData[i];
			this.memoryBuffer.put(b);
		}
		this.totalEvents++;
	}

	@Override
	public void clearDataAndUseNewSettings(SampleSettings newSettings) {
		this.settings = newSettings;
		this.totalEvents = 0;
		this.memoryBuffer.rewind();
		this.memoryBuffer.clear();
		this.parameterList = newSettings.getParameterArray();

		reallocate(newSettings);
	}

	@Override
	public SampleIterator getEventIterator() {
		final EventDataAccessor accessor = new EventDataAccessor(this.settings, DataBitShifter.INSTANCE);
		return new SampleIterator(this.memoryBuffer.asReadOnlyBuffer(), this.totalEvents, this.settings, accessor);
	}

	@Override
	public SampleIterator getEventIterator(Momento eventMomento) {
		final EventDataAccessor accessor = new EventDataAccessor(this.settings, DataBitShifter.INSTANCE);
		return new SampleIterator(this.memoryBuffer.asReadOnlyBuffer(), this.settings, accessor, eventMomento,
		        this.totalEvents);
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public ParameterArray getParameterArray() {
		return this.parameterList;
	}

	@Override
	public int getTotalEvents() {
		return this.totalEvents;
	}

	private void reallocate(SampleSettings newSettings) {
		final int eventCapacity = newSettings.getEventCapacity();
		final int eventByteSize = newSettings.getParameterArray().getEventByteSize();
		final int bufferSize = eventCapacity * eventByteSize;
		this.memoryBuffer = this.allocator.allocate(bufferSize);
	}

	public int getParameterCount() {
		return this.parameterList.getParameters().size();
	}

}
