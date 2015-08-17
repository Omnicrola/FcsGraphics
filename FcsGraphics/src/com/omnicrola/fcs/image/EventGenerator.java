package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.IEventDataAccessor;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.fcs.data.SampleIterator;
import com.omnicrola.fcs.data.SampleSettings;

public class EventGenerator {

	private Sample currentSample;
	private final DataBitShifter dataBitShifter;
	private final ByteBuffer currentEvent;
	private final List<Parameter> parameters;
	private SampleIterator eventIterator;
	private Sample readSample;

	public EventGenerator(DataBitShifter dataBitShifter, ByteBuffer byteBuffer, Sample currentSample,
	        Sample readSample) {
		this.dataBitShifter = dataBitShifter;
		this.parameters = currentSample.getParameterArray().getParameters();
		this.currentEvent = byteBuffer;
		this.currentSample = currentSample;
		this.readSample = readSample;
		rewind();
	}

	public void rewind() {

		final Sample temp = this.currentSample;
		this.currentSample = this.readSample;
		this.readSample = temp;
		this.eventIterator = this.readSample.getEventIterator();
		this.currentSample.clearDataAndUseNewSettings(SampleSettings.DEFAULT);
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
		final ByteBuffer sourceBuffer;
		if (this.eventIterator.hasNext()) {
			final IEventDataAccessor event = this.eventIterator.next();
			sourceBuffer = event.getFullBytes();
		} else {
			final int eventByteSize = 4 * this.currentSample.getParameterCount();
			sourceBuffer = ByteBuffer.allocate(eventByteSize).put(new byte[eventByteSize]);
			sourceBuffer.rewind();
		}
		interleaveBytes(xValue, yValue, xParam, yParam, sourceBuffer);
		this.currentSample.addEvent(this.currentEvent.array());

	}

	private void interleaveBytes(final byte[] xValue, final byte[] yValue, final Parameter xParam,
	        final Parameter yParam, final ByteBuffer sourceBuffer) {
		final byte[] existingValue = new byte[4];
		this.currentEvent.clear();
		for (final Parameter parameter : this.parameters) {
			sourceBuffer.get(existingValue);
			if (parameter.equals(xParam)) {
				this.currentEvent.put(xValue);
			} else if (parameter.equals(yParam)) {
				this.currentEvent.put(yValue);
			} else {
				this.currentEvent.put(existingValue);
			}
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
