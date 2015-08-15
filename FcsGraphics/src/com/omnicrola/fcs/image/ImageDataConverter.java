package com.omnicrola.fcs.image;

import java.nio.ByteBuffer;
import java.util.Random;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.Sample;

public class ImageDataConverter implements IFcsDataProvider {

	private final ImageDataReader imageDataReader;
	private final DataBitShifter dataBitShifter;
	private final Random random;

	public ImageDataConverter(DataBitShifter dataBitShifter, ImageDataReader imageDataReader) {
		this.dataBitShifter = dataBitShifter;
		this.imageDataReader = imageDataReader;
		this.random = new Random(901);
	}

	public void writeToSample(Sample sample) {
		// TODO : actually do stuff

		final int parameterCount = sample.getParameterCount();
		for (int i = 0; i < 100; i++) {
			final ByteBuffer byteBuffer = ByteBuffer.allocate(4 * parameterCount);
			for (int p = 0; p < parameterCount; p++) {
				byteBuffer.put(this.dataBitShifter.translateFromInteger(randI()));
			}
			sample.addEvent(byteBuffer.array());
		}
	}

	private int randI() {
		final int nextInt = this.random.nextInt(16777214);
		System.out.println(nextInt);
		return nextInt;
	}

}
