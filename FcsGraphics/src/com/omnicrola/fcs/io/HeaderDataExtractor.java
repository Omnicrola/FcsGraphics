package com.omnicrola.fcs.io;

import java.util.HashMap;
import java.util.List;

import com.omnicrola.fcs.BasicHeaderData;
import com.omnicrola.fcs.FcsHeaderData;
import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.ParameterArray;
import com.omnicrola.fcs.data.Sample;

public class HeaderDataExtractor {

	public FcsHeaderDataRead extract(Sample sample) {

		final HashMap<String, String> headers = BasicHeaderData.getHeaders();
		final int totalEvents = sample.getTotalEvents();
		headers.put(BasicHeaderData.TOTAL_EVENTS, String.valueOf(totalEvents));
		headers.put(BasicHeaderData.PARAMETER_COUNT, String.valueOf(sample.getParameterCount()));
		final ParameterArray parameterArray = sample.getParameterArray();
		final List<Parameter> parameters = parameterArray.getParameters();
		for (final Parameter parameter : parameters) {
			createParameter(parameter, headers);
		}
		final int eventByteSize = sample.getParameterArray().getEventByteSize();
		final int dataSizeInBytes = totalEvents * eventByteSize;
		final FcsHeaderData fcsHeaderData = new FcsHeaderData(headers, dataSizeInBytes);

		return fcsHeaderData;
	}

	private static void createParameter(Parameter parameter, HashMap<String, String> headers) {
		final int parameterId = parameter.getIndex();
		// bytes for this parameters
		final String bitSize = String.valueOf(parameter.getDataType().byteSize() * 8);
		headers.put(BasicHeaderData.REQUIRED + "P" + parameterId + "B", bitSize);

		// amplification type
		headers.put(BasicHeaderData.REQUIRED + "P" + parameterId + "E", "0,0");

		// maximum range for this parameter
		final String range = String.valueOf(parameter.getMaxRange());
		headers.put(BasicHeaderData.REQUIRED + "P" + parameterId + "R", range);

		// Short Name
		headers.put(BasicHeaderData.REQUIRED + "P" + parameterId + "N", parameter.getShortName());
	}

}
