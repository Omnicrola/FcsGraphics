package com.omnicrola.fcs.io;

import java.io.FileOutputStream;
import java.io.IOException;

import com.omnicrola.fcs.FcsHeaderDataRead;

public class FcsHeaderDataWriter {

	private static final String EIGHT_SPACES = "        ";
	private static final int VERSION_OFFSET = 0;
	private static final int VERSION_LENGTH = 6;

	private static final int SPACER_OFFSET = 6;
	private static final int SPACER_LENGTH = 4;

	private static final int TEXT_START = 58;
	private static final int TEXT_END_OFFSET = 18;

	private static final int DATA_START_OFFSET = 26;
	private static final int DATA_END_OFFSET = 34;

	private static final int ANALYSIS_START_OFFSET = 42;
	private static final int ANALYSIS_END_OFFSET = 50;

	private static final int OTHER_START_OFFSET = 58;
	private static final String VERSION = "FCS3.1";

	public void write(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		writeVersion(fileOutputStream);
		writeSpacer(fileOutputStream);
		writeTextSection(header, fileOutputStream);
	}

	private void writeTextSection(FcsHeaderDataRead header, FileOutputStream fileOutputStream) throws IOException {
		final int textLength = header.getLengthInBytes();
		fileOutputStream.write(convertIntToPaddedString(TEXT_START));
		fileOutputStream.write(convertIntToPaddedString(textLength + TEXT_START));
	}

	private void writeVersion(FileOutputStream fileOutputStream) throws IOException {
		fileOutputStream.write(VERSION.getBytes(), 0, VERSION_LENGTH);
	}

	private void writeSpacer(FileOutputStream fileOutputStream) throws IOException {
		final byte[] spacer = "      ".getBytes();
		fileOutputStream.write(spacer, 0, SPACER_LENGTH);
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
