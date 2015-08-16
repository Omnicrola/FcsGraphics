package com.omnicrola.fcs.io;

import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.util.SimpleLogger;

public class FcsHeaderDataWriter {

	private static final String EIGHT_SPACES = "        ";
	private static final String FOUR_SPACES = "    ";
	private static final String VERSION = "FCS3.1";

	private static final int BYTES_TAKEN_BY_OFFSETS = 58;
	public static final String HEADER_DELIMITER = "/";

	public void write(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		SimpleLogger.log("FCS version: " + VERSION);
		writeVersion(fileOutputStream);

		SimpleLogger.log("Writing file offsets...");
		writeSpacer(fileOutputStream);
		writeTextSectionOffsets(header, fileOutputStream);
		writeDataSectionOffsets(header, fileOutputStream);
		writeAnalysisSectionOffsets(header, fileOutputStream);

		writeHeaderText(header, fileOutputStream);
	}

	private void writeVersion(FileOutputStream fileOutputStream) throws IOException {
		fileOutputStream.write(VERSION.getBytes());
	}

	private void writeTextSectionOffsets(FcsHeaderDataRead header, FileOutputStream fileOutputStream)
	        throws IOException {
		final int textLength = header.getHeaderLengthInBytes();
		fileOutputStream.write(convertIntToPaddedString(BYTES_TAKEN_BY_OFFSETS));
		fileOutputStream.write(convertIntToPaddedString(textLength + BYTES_TAKEN_BY_OFFSETS));
	}

	private void writeDataSectionOffsets(FcsHeaderDataRead header, FileOutputStream fileOutputStream)
	        throws IOException {
		final int dataStart = header.getHeaderLengthInBytes() + BYTES_TAKEN_BY_OFFSETS + 1;
		final int dataEnd = dataStart + header.getDataLengthInBytes();
		fileOutputStream.write(convertIntToPaddedString(dataStart));
		fileOutputStream.write(convertIntToPaddedString(dataEnd));
	}

	private void writeAnalysisSectionOffsets(FcsHeaderDataRead header, FileOutputStream fileOutputStream)
	        throws IOException {
		fileOutputStream.write(EIGHT_SPACES.getBytes());
		fileOutputStream.write(EIGHT_SPACES.getBytes());
	}

	private void writeHeaderText(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		final byte[] bytes = header.getHeaderText().getBytes();
		SimpleLogger.log("Writing header text (" + bytes.length + "b)");
		fileOutputStream.write(FcsHeaderDataWriter.HEADER_DELIMITER.getBytes());
		fileOutputStream.write(bytes);
	}

	private void writeSpacer(FileOutputStream fileOutputStream) throws IOException {
		fileOutputStream.write(FOUR_SPACES.getBytes());
	}

	private static byte[] convertIntToPaddedString(int number) {
		final byte[] paddedBytes = EIGHT_SPACES.getBytes();
		final byte[] numberBytes = String.valueOf(number).getBytes();
		final int start = paddedBytes.length - numberBytes.length;
		for (int i = start; i < paddedBytes.length; i++) {
			paddedBytes[i] = numberBytes[i - start];
		}

		return paddedBytes;
	}
}
