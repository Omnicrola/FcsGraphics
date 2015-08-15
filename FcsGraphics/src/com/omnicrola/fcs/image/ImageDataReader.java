package com.omnicrola.fcs.image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.omnicrola.util.SimpleLogger;

public class ImageDataReader {

	private final String argumentValue;

	public ImageDataReader(String argumentValue) {
		this.argumentValue = argumentValue;
	}

	public void read() {
		try {
			ImageIO.read(new File(this.argumentValue));
		} catch (final IOException e) {
			SimpleLogger.error(e);
		}
	}

}
