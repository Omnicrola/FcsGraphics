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
	private final Parameter xParam;
	private final Parameter yParam;
	private final List<Parameter> parameters;
	private final float xScale;
	private final float yScale;

	public EventGenerator(DataBitShifter dataBitShifter, ByteBuffer byteBuffer, Parameter xParam, Parameter yParam,
	        Sample sample, float xScale, float yScale) {
		this.dataBitShifter = dataBitShifter;
		this.xScale = xScale;
		this.yScale = yScale;
		this.parameters = sample.getParameterArray().getParameters();
		this.byteBuffer = byteBuffer;
		this.xParam = xParam;
		this.yParam = yParam;
		this.sample = sample;
	}

	public void createEventAtCoordinate(int imageX, int imageY, int density) {
		if (density < 0 || density > 16) {
			throw new IllegalArgumentException("Event density cannot must be within 0-16 (was " + density + ")");
		}

		final int dataX = (int) (imageX * this.xScale);
		final int dataY = flipYAxis((int) (imageY * this.yScale));

		int x = 0;
		int y = 0;
		final int wrap = (int) Math.sqrt(density);
		for (int i = 0; i < density; i++) {
			createSingleEvent(dataX + x, dataY + y);
			x++;
			if (x >= wrap) {
				x = 0;
				y++;
			}
		}

	}

	private void createSingleEvent(int xPosition, int yPosition) {
		final byte[] xValue = this.dataBitShifter.translateFromInteger(xPosition);
		final byte[] yValue = this.dataBitShifter.translateFromInteger(yPosition);
		this.byteBuffer.clear();
		for (final Parameter parameter : this.parameters) {
			if (parameter.equals(this.xParam)) {
				this.byteBuffer.put(xValue);
			} else if (parameter.equals(this.yParam)) {
				this.byteBuffer.put(yValue);
			} else {
				this.byteBuffer.put(ONE);
			}
		}
		this.sample.addEvent(this.byteBuffer.array());
	}

	private int flipYAxis(int value) {
		return (int) (this.yParam.getMaxRange() - value);
	}

}
