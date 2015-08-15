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

	public FcsDataWriter(String targetFilename, FcsHeaderDataWriter headerDataWriter,
	        HeaderDataExtractor headerDataExtractor) {
		this.targetFilename = targetFilename;
		this.headerDataWriter = headerDataWriter;
		this.headerExtractor = headerDataExtractor;
	}

	public void write(Sample sample) throws IOException {
		final FileOutputStream fileOutputStream = createFile();
		writeDataToFile(sample, fileOutputStream);
	}

	private void writeDataToFile(Sample sample, final FileOutputStream fileOutputStream) throws IOException {
		SimpleLogger.log("Writing to file... ");

		final FcsHeaderDataRead header = getHeaderData(sample);
		this.headerDataWriter.write(header, fileOutputStream);
		fileOutputStream.close();
		SimpleLogger.log("Finished writing.");
	}

	private FcsHeaderDataRead getHeaderData(Sample sample) {
		return this.headerExtractor.extract(sample);
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
