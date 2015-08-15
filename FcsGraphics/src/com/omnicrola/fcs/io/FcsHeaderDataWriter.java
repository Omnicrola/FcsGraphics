package com.omnicrola.fcs.io;

import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsHeaderDataRead;

public class FcsHeaderDataWriter {

	private static final String EIGHT_SPACES = "        ";
	private static final String FOUR_SPACES = "    ";
	private static final String VERSION = "FCS3.1";

	private static final int TEXT_START = 58;

	public void write(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		writeVersion(fileOutputStream);
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
		fileOutputStream.write(convertIntToPaddedString(TEXT_START));
		fileOutputStream.write(convertIntToPaddedString(textLength + TEXT_START));
	}

	private void writeDataSectionOffsets(FcsHeaderDataRead header, FileOutputStream fileOutputStream)
	        throws IOException {
		final int dataStart = header.getHeaderLengthInBytes() + TEXT_START;
		final int dataEnd = header.getDataLengthInBytes() + dataStart;
		fileOutputStream.write(convertIntToPaddedString(dataStart));
		fileOutputStream.write(convertIntToPaddedString(dataEnd));
	}

	private void writeAnalysisSectionOffsets(FcsHeaderDataRead header, FileOutputStream fileOutputStream)
	        throws IOException {
		fileOutputStream.write(EIGHT_SPACES.getBytes());
		fileOutputStream.write(EIGHT_SPACES.getBytes());
	}

	private void writeHeaderText(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		final String headerText = header.getHeaderText();
		fileOutputStream.write(headerText.getBytes());
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
