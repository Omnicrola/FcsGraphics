package com.omnicrola.fcs.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class FcsDataWriter {

	private final String targetFilename;
	private final FcsHeaderDataWriter headerDataWriter;
	private final HeaderDataExtractor headerExtractor;
	private final FcsEventDataWriter eventDataWriter;

	public FcsDataWriter(String targetFilename, FcsHeaderDataWriter headerDataWriter,
	        HeaderDataExtractor headerDataExtractor, FcsEventDataWriter eventDataWriter) {
		this.targetFilename = targetFilename;
		this.headerDataWriter = headerDataWriter;
		this.headerExtractor = headerDataExtractor;
		this.eventDataWriter = eventDataWriter;
	}

	public void write(Sample sample) throws IOException {
		final FileOutputStream fileOutputStream = createFile();
		writeDataToFile(sample, fileOutputStream);
	}

	private void writeDataToFile(Sample sample, final FileOutputStream fileOutputStream) throws IOException {
		SimpleLogger.log("Writing to FCS file... ");

		final FcsHeaderDataRead header = this.headerExtractor.extract(sample);
		this.headerDataWriter.write(header, fileOutputStream);
		this.eventDataWriter.write(sample, fileOutputStream);
		fileOutputStream.close();
		SimpleLogger.log("Finished writing FCS.");
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
