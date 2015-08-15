package com.omnicrola.fcs.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsData;
import com.omnicrola.util.SimpleLogger;

public class FcsDataWriter {

	private final String targetFilename;

	public FcsDataWriter(String targetFilename) {
		this.targetFilename = targetFilename;
	}

	public void write(FcsData fcsData) throws IOException {
		final FileOutputStream fileOutputStream = createFile();
		writeDataToFile(fileOutputStream);
	}

	private void writeDataToFile(final FileOutputStream fileOutputStream) throws IOException {
		final String hello = "Hello FCS";
		SimpleLogger.log("Writing to file... ");
		fileOutputStream.write(hello.getBytes());
		fileOutputStream.close();
		SimpleLogger.log("Finished writing.");
	}

	private FileOutputStream createFile() throws IOException, FileNotFoundException {
		SimpleLogger.log("Creating file : " + this.targetFilename);
		final File file = new File(this.targetFilename);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		final FileOutputStream fileOutputStream = new FileOutputStream(file);
		return fileOutputStream;
	}

}
