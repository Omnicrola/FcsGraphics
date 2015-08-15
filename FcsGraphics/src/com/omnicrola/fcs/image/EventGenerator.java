package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;

public class EventGenerator {
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
		for (final Parameter parameter : this.parameters) {
			if (parameter.equals(this.xParam)) {
				this.byteBuffer.put(this.dataBitShifter.translateFromInteger(x * scale));
			} else if (parameter.equals(this.yParam)) {
				this.byteBuffer.put(this.dataBitShifter.translateFromInteger(y * scale));
			} else {
				this.byteBuffer.put(this.dataBitShifter.translateFromInteger(0));
			}
		}
		this.sample.addEvent(this.byteBuffer.array());
	}

}
