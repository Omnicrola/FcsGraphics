package com.omnicrola.fcs.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsData;
import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.util.SimpleLogger;

public class FcsDataWriter {

	private final String targetFilename;
	private final FcsHeaderDataWriter headerDataWriter;

	public FcsDataWriter(String targetFilename, FcsHeaderDataWriter headerDataWriter) {
		this.targetFilename = targetFilename;
		this.headerDataWriter = headerDataWriter;
	}

	public void write(FcsData fcsData) throws IOException {
		final FileOutputStream fileOutputStream = createFile();
		writeDataToFile(fcsData, fileOutputStream);
	}

	private void writeDataToFile(FcsData fcsData, final FileOutputStream fileOutputStream) throws IOException {
		SimpleLogger.log("Writing to file... ");
		final FcsHeaderDataRead header = fcsData.getHeader();
		this.headerDataWriter.write(header, fileOutputStream);
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
