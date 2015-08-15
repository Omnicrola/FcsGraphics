package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class EventGeneratorFactory {

	private static final int BYTES_PER_INTEGER = 4;
	private final DataBitShifter dataBitShifter;

	public EventGeneratorFactory(DataBitShifter dataBitShifter) {
		this.dataBitShifter = dataBitShifter;
	}

	public EventGenerator build(Sample sample, int imageWidth, int imageHeight) {
		final ByteBuffer byteBuffer = ByteBuffer.allocate(BYTES_PER_INTEGER * sample.getParameterCount());
		final List<Parameter> parameters = sample.getParameterArray().getParameters();
		final Parameter xParam = parameters.get(0);
		final Parameter yParam = parameters.get(1);
		SimpleLogger.log("Rendering using parameter '" + xParam.getShortName() + "' as X axis and '"
		        + yParam.getShortName() + "' as Y axis");
		return new EventGenerator(this.dataBitShifter, byteBuffer, xParam, yParam, sample);
	}

}
