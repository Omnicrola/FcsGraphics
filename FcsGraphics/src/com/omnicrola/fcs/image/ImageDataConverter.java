package com.omnicrola.fcs.image;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.Sample;

public class ImageDataConverter implements IFcsDataProvider {

	private final ImageDataReader imageDataReader;

	public ImageDataConverter(ImageDataReader imageDataReader) {
		this.imageDataReader = imageDataReader;
	}

	public void writeToSample(Sample sample) {
	}

}
