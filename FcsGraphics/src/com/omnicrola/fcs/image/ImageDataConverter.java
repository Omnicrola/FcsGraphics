package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.omnicrola.fcs.IFcsDataProvider;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class ImageDataConverter implements IFcsDataProvider {

	private final ImageDataReader imageDataReader;
	private final EventGeneratorStrategyBuilder eventGeneratorFactory;

	public ImageDataConverter(ImageDataReader imageDataReader, EventGeneratorStrategyBuilder eventGeneratorFactory) {
		this.imageDataReader = imageDataReader;
		this.eventGeneratorFactory = eventGeneratorFactory;
	}

	public void writeToSample(Sample sample) {
		final ArrayList<BufferedImage> images = this.imageDataReader.read();
		final Map<BufferedImage, EventGeneratorStrategy> eventStrategies = createEventStrategiesForEachImage(images);
		createEvents(eventStrategies);

	}

	private void createEvents(Map<BufferedImage, EventGeneratorStrategy> eventStrategies) {
		for (final Entry<BufferedImage, EventGeneratorStrategy> entry : eventStrategies.entrySet()) {
			final BufferedImage image = entry.getKey();
			final EventGeneratorStrategy strategy = entry.getValue();
			strategy.generate(image);
		}
	}

	private Map<BufferedImage, EventGeneratorStrategy> createEventStrategiesForEachImage(
	        final ArrayList<BufferedImage> images) {
		final Map<BufferedImage, EventGeneratorStrategy> eventStrategies = new HashMap<>();
		for (final BufferedImage bufferedImage : images) {
			final int width = bufferedImage.getWidth();
			final int height = bufferedImage.getHeight();
			SimpleLogger.log("Loaded image : " + width + " x " + height + " (" + (width * height) + " pixels) ");
			eventStrategies.put(bufferedImage, this.eventGeneratorFactory.buildImageStrategy(width, height));
		}
		return eventStrategies;
	}

}
