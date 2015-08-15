package com.omnicrola.util;

import java.io.File;

import javax.swing.JFileChooser;

public class FileFinder {

	public static String findFile(String fileDescription) {
		final JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setDialogTitle(fileDescription);
		if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = jFileChooser.getSelectedFile();
			return selectedFile.getAbsolutePath();
		}
		throw new RuntimeException("A valid " + fileDescription + " file is required");
	}

}
