package com.omnicrola.fcs.io;

import java.util.HashMap;

import com.omnicrola.fcs.BasicHeaderData;
import com.omnicrola.fcs.FcsHeaderData;
import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.fcs.data.Sample;

public class HeaderDataExtractor {

	public FcsHeaderDataRead extract(Sample sample) {

		final HashMap<String, String> headers = BasicHeaderData.getHeaders(sample.getParameterCount());
		final FcsHeaderData fcsHeaderData = new FcsHeaderData(headers);

		return fcsHeaderData;
	}

}
