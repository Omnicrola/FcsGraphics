package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class ImageDataConverter implements IFcsDataProvider {

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
		// createRandomEvents(1, eventGenerator);

	}

	private void createRandomEvents(int count, EventGenerator eventGenerator) {
		for (int i = 0; i < count; i++) {
			eventGenerator.createEventAtCoordinate(i + 100, 100);
		}
	}

	private void createEvents(final BufferedImage image, final int width, final int height,
	        final EventGenerator eventGenerator) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int rgb = image.getRGB(x, y);
				if (rgb != -1) {
					eventGenerator.createEventAtCoordinate(x, y);
				}
			}
		}
	}

}
