package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.IEventDataAccessor;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.fcs.data.SampleIterator;

public class EventGenerator {

	private static final byte[] ONE = createOne();

	private static byte[] createOne() {
		final byte[] bytes = new byte[4];
		bytes[3] = 1;
		return bytes;
	}

	private final Sample sample;
	private final DataBitShifter dataBitShifter;
	private final ByteBuffer byteBuffer;
	private final List<Parameter> parameters;
	private SampleIterator eventIterator;

	public EventGenerator(DataBitShifter dataBitShifter, ByteBuffer byteBuffer, Sample sample) {
		this.dataBitShifter = dataBitShifter;
		this.parameters = sample.getParameterArray().getParameters();
		this.byteBuffer = byteBuffer;
		this.sample = sample;
		rewind();
	}

	public void rewind() {
		this.eventIterator = this.sample.getEventIterator();
	}

	public void createEventAtCoordinate(EventGeneratorStrategy strategy, int imageX, int imageY, int density) {
		assertDensity(density);

		final int dataX = (int) (imageX * strategy.getXScale());
		final int dataY = flipYAxis(strategy.getYParam(), (int) (imageY * strategy.getYScale()));
		createOneEventPerLevelOfDensity(strategy, density, dataX, dataY);
	}

	private void createOneEventPerLevelOfDensity(EventGeneratorStrategy strategy, int density, final int dataX,
	        final int dataY) {
		int x = 0;
		int y = 0;
		final int wrap = (int) Math.sqrt(density);
		for (int i = 0; i < density; i++) {
			createEventForPixel(strategy, dataX + x, dataY + y);
			x++;
			if (x >= wrap) {
				x = 0;
				y++;
			}
		}
	}

	private void createEventForPixel(EventGeneratorStrategy strategy, int xPosition, int yPosition) {
		final byte[] xValue = this.dataBitShifter.translateFromInteger(xPosition);
		final byte[] yValue = this.dataBitShifter.translateFromInteger(yPosition);
		final Parameter xParam = strategy.getXParam();
		final Parameter yParam = strategy.getYParam();
		final ByteBuffer sourceBuffer = getBytesForExistingEvent();
		final byte[] existingValue = new byte[4];
		this.byteBuffer.clear();
		for (final Parameter parameter : this.parameters) {
			sourceBuffer.get(existingValue);
			if (parameter.equals(xParam)) {
				this.byteBuffer.put(xValue);
			} else if (parameter.equals(yParam)) {
				this.byteBuffer.put(yValue);
			} else {
				this.byteBuffer.put(existingValue);
			}
		}
		this.sample.addEvent(this.byteBuffer.array());
	}

	private ByteBuffer getBytesForExistingEvent() {
		if (this.eventIterator.hasNext()) {
			final IEventDataAccessor event = this.eventIterator.next();
			final ByteBuffer buffer = event.getFullBytes();
			return buffer;
		} else {
			final int eventByteSize = 4 * this.sample.getParameterCount();
			final ByteBuffer buffer = ByteBuffer.allocate(eventByteSize).put(new byte[eventByteSize]);
			buffer.rewind();
			return buffer;
		}
	}

	private int flipYAxis(Parameter parameter, int value) {
		return (int) (parameter.getMaxRange() - value);
	}

	private static void assertDensity(int density) {
		if (density < 0 || density > 16) {
			throw new IllegalArgumentException("Event density cannot must be within 0-16 (was " + density + ")");
		}
	}

}
