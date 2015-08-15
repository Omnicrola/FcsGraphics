package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class ImageDataConverter implements IFcsDataProvider {

	private static final int WHITE = 255;
	private final ImageDataReader imageDataReader;
	private final EventGeneratorFactory eventGeneratorFactory;

	public ImageDataConverter(ImageDataReader imageDataReader, EventGeneratorFactory eventGeneratorFactory) {
		this.imageDataReader = imageDataReader;
		this.eventGeneratorFactory = eventGeneratorFactory;
	}

	public void writeToSample(Sample sample) {
		final BufferedImage image = this.imageDataReader.read();
		final int width = image.getWidth();
		final int height = image.getHeight();
		SimpleLogger.log("Loaded image : " + width + " x " + height + " (" + (width * height) + " pixels) ");

		final EventGenerator eventGenerator = this.eventGeneratorFactory.build(sample, width, height);

		createEvents(image, width, height, eventGenerator);

	}

	private void createEvents(final BufferedImage image, final int width, final int height,
	        final EventGenerator eventGenerator) {
		final WritableRaster raster = image.getRaster();
		final int[] rgba = new int[4];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int[] pixel = raster.getPixel(x, y, rgba);
				final int grayscale = getGrayscale(pixel);
				if (grayscale < WHITE) {
					eventGenerator.createEventAtCoordinate(x, y, grayscale);
				}
			}
		}
	}

	private int getGrayscale(int[] rgb) {
		return (int) ((rgb[0] * 0.3f) + (rgb[1] * 0.59f) + (rgb[2] * 0.11f));
	}

}
