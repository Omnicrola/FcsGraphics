package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;

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

	public EventGenerator(DataBitShifter dataBitShifter, ByteBuffer byteBuffer, Sample sample) {
		this.dataBitShifter = dataBitShifter;
		this.parameters = sample.getParameterArray().getParameters();
		this.byteBuffer = byteBuffer;
		this.sample = sample;
	}

	public void createEventAtCoordinate(EventGeneratorStrategy strategy, int imageX, int imageY, int density) {
		if (density < 0 || density > 16) {
			throw new IllegalArgumentException("Event density cannot must be within 0-16 (was " + density + ")");
		}

		final int dataX = (int) (imageX * strategy.getXScale());
		final int dataY = flipYAxis(strategy.getYParam(), (int) (imageY * strategy.getYScale()));

		int x = 0;
		int y = 0;
		final int wrap = (int) Math.sqrt(density);
		for (int i = 0; i < density; i++) {
			createSingleEvent(strategy, dataX + x, dataY + y);
			x++;
			if (x >= wrap) {
				x = 0;
				y++;
			}
		}
	}

	private void createSingleEvent(EventGeneratorStrategy strategy, int xPosition, int yPosition) {
		final byte[] xValue = this.dataBitShifter.translateFromInteger(xPosition);
		final byte[] yValue = this.dataBitShifter.translateFromInteger(yPosition);
		final Parameter xParam = strategy.getXParam();
		final Parameter yParam = strategy.getYParam();
		this.byteBuffer.clear();
		for (final Parameter parameter : this.parameters) {
			if (parameter.equals(xParam)) {
				this.byteBuffer.put(xValue);
			} else if (parameter.equals(yParam)) {
				this.byteBuffer.put(yValue);
			} else {
				this.byteBuffer.put(ONE);
			}
		}
		this.sample.addEvent(this.byteBuffer.array());
	}

	private int flipYAxis(Parameter parameter, int value) {
		return (int) (parameter.getMaxRange() - value);
	}

}
