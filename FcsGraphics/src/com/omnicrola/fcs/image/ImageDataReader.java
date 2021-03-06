package com.omnicrola.fcs.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.omnicrola.util.SimpleLogger;

public class ImageDataReader {

	private final List<String> filenames;

	public ImageDataReader(List<String> images) {
		this.filenames = images;
	}

	public ArrayList<BufferedImage> read() {
		final ArrayList<BufferedImage> images = new ArrayList<>();
		try {
			for (final String filename : this.filenames) {
				final File file = new File(filename);
				if (file.exists()) {
					images.add(ImageIO.read(file));
				} else {
					SimpleLogger.error("File not found : " + filename);
				}
			}
		} catch (final IOException e) {
			SimpleLogger.error(e);
		}
		return images;
	}

}
