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

	public EventGenerator(DataBitShifter dataBitShifter, ByteBuffer byteBuffer, Parameter xParam, Parameter yParam,
	        Sample sample) {
		this.dataBitShifter = dataBitShifter;
		this.parameters = sample.getParameterArray().getParameters();
		this.byteBuffer = byteBuffer;
		this.xParam = xParam;
		this.yParam = yParam;
		this.sample = sample;
	}

	public void createEventAtCoordinate(int x, int y) {
		this.byteBuffer.clear();
		final int scale = 100;
		final byte[] yValue = this.dataBitShifter.translateFromInteger(flipYAxis(y * scale));
		final byte[] xValue = this.dataBitShifter.translateFromInteger(x * scale);
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
