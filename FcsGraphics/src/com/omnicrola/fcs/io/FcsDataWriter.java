package com.omnicrola.fcs.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class FcsDataWriter {

	private static final int TOTAL_MAXIMUM_SIZE = 9999_9999;
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
		final float sizeInMb = sample.getSampleSizeInBytes() / 1024f / 1024f;
		SimpleLogger.log("Data size: " + sizeInMb + "MB");

		final FcsHeaderDataRead header = this.headerExtractor.extract(sample);
		checkBounds(header);
		this.headerDataWriter.write(header, fileOutputStream);
		this.eventDataWriter.write(sample, fileOutputStream);
		fileOutputStream.close();
		SimpleLogger.log("Finished writing FCS.");
	}

	private static void checkBounds(FcsHeaderDataRead header) {
		final long dataLengthInBytes = header.getDataLengthInBytes();
		final long headerLengthInBytes = header.getHeaderLengthInBytes();
		final long potentialDataOffset = FcsHeaderDataWriter.BYTES_TAKEN_BY_OFFSETS + headerLengthInBytes
		        + dataLengthInBytes;
		if (potentialDataOffset > TOTAL_MAXIMUM_SIZE) {
			final NumberFormat integerFormater = NumberFormat.getIntegerInstance();

			final String actualSize = integerFormater.format(dataLengthInBytes + headerLengthInBytes);
			final String maximumSize = integerFormater
			        .format(TOTAL_MAXIMUM_SIZE - FcsHeaderDataWriter.BYTES_TAKEN_BY_OFFSETS);
			final String message = "Bytes taken by header + data exceeds maximum number allowed by FCS specification.\nMaximum:"
			        + maximumSize + "\nActual:" + actualSize;
			SimpleLogger.error(message);
			System.exit(50);
		}
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
