package com.omnicrola.fcs.image;

import com.omnicrola.fcs.IFcsDataProvider;

public class ImageDataConverter implements IFcsDataProvider {

	private final ImageDataReader imageDataReader;

	public ImageDataConverter(ImageDataReader imageDataReader) {
		this.imageDataReader = imageDataReader;
	}

}
