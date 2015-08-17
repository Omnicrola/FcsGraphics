package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class ImageDataConverter implements IFcsDataProvider {

	private static final int WHITE = 255;
	private final ImageDataReader imageDataReader;
	private final EventGeneratorStrategyBuilder eventGeneratorFactory;

	public ImageDataConverter(ImageDataReader imageDataReader, EventGeneratorStrategyBuilder eventGeneratorFactory) {
		this.imageDataReader = imageDataReader;
		this.eventGeneratorFactory = eventGeneratorFactory;
	}

	public void writeToSample(Sample sample) {
		final ArrayList<BufferedImage> images = this.imageDataReader.read();
		for (final BufferedImage bufferedImage : images) {
			final int width = bufferedImage.getWidth();
			final int height = bufferedImage.getHeight();
			SimpleLogger.log("Loaded image : " + width + " x " + height + " (" + (width * height) + " pixels) ");

			final EventGeneratorStrategy eventStrategy = this.eventGeneratorFactory.buildImageStrategy(width, height);

			createEvents(bufferedImage, width, height, eventStrategy);
		}

	}

	private void createEvents(final BufferedImage image, final int width, final int height,
	        final EventGeneratorStrategy eventStrategy) {
		final WritableRaster raster = image.getRaster();
		final int[] rgba = new int[4];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int[] pixel = raster.getPixel(x, y, rgba);
				final int grayscale = getGrayscale(pixel);
				eventStrategy.createEventAtCoordinate(x, y, grayscale);
			}
		}
	}

	private int getGrayscale(int[] rgb) {
		final int intensity = (int) ((rgb[0] * 0.3f) + (rgb[1] * 0.59f) + (rgb[2] * 0.11f));
		final int invertedGray = WHITE - intensity;
		final int sixteenBitGrayscale = invertedGray / 16;
		return sixteenBitGrayscale;
	}

}
