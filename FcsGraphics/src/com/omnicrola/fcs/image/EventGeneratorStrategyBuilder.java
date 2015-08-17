package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class EventGeneratorStrategyBuilder {

	private static final int BYTES_PER_INTEGER = 4;
	private final DataBitShifter dataBitShifter;
	private final Sample sample;
	private final List<Parameter> parameters;
	private int usedParams;
	private final EventGenerator eventGenerator;

	public EventGeneratorStrategyBuilder(DataBitShifter dataBitShifter, Sample sample) {
		this.dataBitShifter = dataBitShifter;
		this.sample = sample;
		this.parameters = sample.getParameterArray().getParameters();
		this.usedParams = 0;
		final ByteBuffer byteBuffer = ByteBuffer.allocate(BYTES_PER_INTEGER * this.sample.getParameterCount());
		this.eventGenerator = new EventGenerator(this.dataBitShifter, byteBuffer, this.sample);
	}

	public EventGeneratorStrategy buildImageStrategy(int imageWidth, int imageHeight) {
		if (this.parameters.size() > this.usedParams) {
			final Parameter xParam = this.parameters.get(this.usedParams + 1);
			final Parameter yParam = this.parameters.get(this.usedParams + 2);
			final float[] scale = calculateScale(imageWidth, imageHeight, xParam, yParam);
			SimpleLogger.log("Rendering using parameter '" + xParam.getShortName() + "' as X axis and '"
			        + yParam.getShortName() + "' as Y axis");
			this.usedParams += 2;
			return new EventGeneratorStrategy(this.eventGenerator, xParam, yParam, scale[0], scale[1]);
		} else {
			SimpleLogger.error("Attempted to add more images than than are possible. Maximum images : "
			        + this.parameters.size() / 2);
			System.exit(48);
			return null;
		}
	}

	private float[] calculateScale(float imageWidth, float imageHeight, Parameter xParam, Parameter yParam) {
		final float xRange = (float) xParam.getMaxRange();
		final float yRange = (float) yParam.getMaxRange();
		final float[] scale = new float[2];
		scale[0] = xRange / imageWidth;
		scale[1] = yRange / imageHeight;

		return scale;
	}

}
