package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;

public class SampleIterator {

	private final ByteBuffer readOnlyBuffer;
	private final EventDataAccessor accessor;
	private final int eventByteSize;
	private final int totalEvents;

	public SampleIterator(ByteBuffer readOnlyBuffer, int totalEvents, SampleSettings settings,
	        EventDataAccessor accessor) {
		this.readOnlyBuffer = readOnlyBuffer;
		this.totalEvents = totalEvents;
		this.accessor = accessor;
		this.eventByteSize = settings.getParameterArray().getEventByteSize();
		this.readOnlyBuffer.clear();
	}

	public SampleIterator(ByteBuffer asReadOnlyBuffer, SampleSettings settings, EventDataAccessor accessor,
	        Momento eventMomento, int totalEvents) {
		this(asReadOnlyBuffer, totalEvents, settings, accessor);
		this.readOnlyBuffer.position(eventMomento.getPointer());
	}

	public Momento getMomento() {
		return new Momento(this.readOnlyBuffer.position());
	}

	public boolean hasNext() {
		final boolean hasNext = this.readOnlyBuffer.position() < this.totalEvents * this.eventByteSize;
		return hasNext;
	}

	public IEventDataAccessor next() {
		this.accessor.rewind();
		for (int i = 0; i < this.eventByteSize; i++) {
			final byte value = this.readOnlyBuffer.get();
			this.accessor.put(value);
		}
		return this.accessor;
	}
}
