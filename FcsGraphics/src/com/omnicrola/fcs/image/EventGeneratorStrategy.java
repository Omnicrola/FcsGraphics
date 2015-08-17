package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.omnicrola.fcs.data.Parameter;

public class EventGeneratorStrategy {

	private static final int WHITE = 255;

	private final Parameter xParam;
	private final Parameter yParam;
	private final float xScale;
	private final float yScale;
	private final EventGenerator eventGenerator;

	public EventGeneratorStrategy(EventGenerator eventGenerator, Parameter xParam, Parameter yParam, float xScale,
	        float yScale) {
		this.eventGenerator = eventGenerator;
		this.xParam = xParam;
		this.yParam = yParam;
		this.xScale = xScale;
		this.yScale = yScale;
	}

	public void createEventAtCoordinate(int x, int y, int density) {
		this.eventGenerator.createEventAtCoordinate(this, x, y, density);
	}

	public Parameter getXParam() {
		return this.xParam;
	}

	public float getXScale() {
		return this.xScale;
	}

	public Parameter getYParam() {
		return this.yParam;
	}

	public float getYScale() {
		return this.yScale;
	}

	public void generate(BufferedImage image) {
		final int width = image.getWidth();
		final int height = image.getHeight();
		this.eventGenerator.rewind();
		final WritableRaster raster = image.getRaster();
		final int[] rgba = new int[4];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int[] pixel = raster.getPixel(x, y, rgba);
				final int grayscale = getGrayscale(pixel);
				this.eventGenerator.createEventAtCoordinate(this, x, y, grayscale);
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
