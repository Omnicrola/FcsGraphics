package com.omnicrola.fcs;

import java.util.HashMap;
import java.util.Map.Entry;

import com.omnicrola.fcs.io.FcsHeaderDataWriter;

public class FcsHeaderData implements FcsHeaderDataRead {
	private final HashMap<String, String> headers;
	private final int dataLengthInBytes;

	public FcsHeaderData(HashMap<String, String> headers, int dataLengthInBytes) {
		this.headers = headers;
		this.dataLengthInBytes = dataLengthInBytes;
	}

	@Override
	public int getHeaderLengthInBytes() {
		return getHeaderText().getBytes().length;
	}

	@Override
	public int getDataLengthInBytes() {
		return this.dataLengthInBytes;
	}

	@Override
	public String getHeaderText() {
		final StringBuilder stringBuilder = new StringBuilder();
		for (final Entry<String, String> entry : this.headers.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append(FcsHeaderDataWriter.HEADER_DELIMITER);
			stringBuilder.append(entry.getValue());
			stringBuilder.append(FcsHeaderDataWriter.HEADER_DELIMITER);
		}
		return stringBuilder.toString();
	}

}
