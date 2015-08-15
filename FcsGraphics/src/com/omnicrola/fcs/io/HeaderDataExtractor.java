package com.omnicrola.fcs.io;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.omnicrola.fcs.BasicHeaderData;
import com.omnicrola.fcs.FcsHeaderData;
import com.omnicrola.fcs.FcsHeaderDataRead;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.ParameterArray;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.util.SimpleLogger;

public class HeaderDataExtractor {

	public FcsHeaderDataRead extract(Sample sample) {

		SimpleLogger.log("Generating header");
		final HashMap<String, String> headers = BasicHeaderData.getHeaders();

		final int totalEvents = sample.getTotalEvents();
		SimpleLogger.log(" - Total Events: " + totalEvents);

		headers.put(BasicHeaderData.TOTAL_EVENTS, String.valueOf(totalEvents));
		headers.put(BasicHeaderData.PARAMETER_COUNT, String.valueOf(sample.getParameterCount()));
		headers.put(BasicHeaderData.DATE, getCurrentDate());
		headers.put(BasicHeaderData.CYTOMETER_NAME, "Image Generated");
		headers.put(BasicHeaderData.BEGIN_TIME, currentTime(0));
		headers.put(BasicHeaderData.END_TIME, currentTime(10_000));

		setParameters(sample, headers);

		final int eventByteSize = sample.getParameterArray().getEventByteSize();
		final int dataSizeInBytes = totalEvents * eventByteSize;
		final FcsHeaderData fcsHeaderData = new FcsHeaderData(headers, dataSizeInBytes);

		return fcsHeaderData;
	}

	private String currentTime(int offset) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss:SS");
		final Date currentTime = new Date();
		currentTime.setTime(currentTime.getTime() + offset);
		return simpleDateFormat.format(currentTime);
	}

	private String getCurrentDate() {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YYYY");
		return simpleDateFormat.format(new Date()).toUpperCase();
	}

	private void setParameters(Sample sample, final HashMap<String, String> headers) {
		final ParameterArray parameterArray = sample.getParameterArray();
		final List<Parameter> parameters = parameterArray.getParameters();
		SimpleLogger.log(" - Parameter Count: " + parameters.size());
		for (final Parameter parameter : parameters) {
			createParameter(parameter, headers);
		}
	}

	private static void createParameter(Parameter parameter, HashMap<String, String> headers) {
		final int parameterId = parameter.getIndex() + 1;
		// bytes for this parameters
		final String bitSize = String.valueOf(parameter.getDataType().byteSize() * 8);
		headers.put(BasicHeaderData.STANDARD + "P" + parameterId + "B", bitSize);

		// amplification type
		headers.put(BasicHeaderData.STANDARD + "P" + parameterId + "E", "0,0");

		// maximum range for this parameter
		final String range = String.valueOf((int) parameter.getMaxRange());
		headers.put(BasicHeaderData.STANDARD + "P" + parameterId + "R", range);

		// Name
		final String shortName = parameter.getShortName();
		headers.put(BasicHeaderData.STANDARD + "P" + parameterId + "N", shortName);
		headers.put(BasicHeaderData.STANDARD + "P" + parameterId + "S", shortName);
	}

}
