package com.omnicrola.fcs.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.omnicrola.fcs.data.IEventDataAccessor;
import com.omnicrola.fcs.data.Parameter;
import com.omnicrola.fcs.data.ParameterArray;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.fcs.data.SampleIterator;
import com.omnicrola.util.SimpleLogger;

public class FcsEventDataWriter {

	public void write(Sample sample, FileOutputStream fileOutputStream) throws IOException {
		final ParameterArray parameterArray = sample.getParameterArray();
		final List<Parameter> parameters = parameterArray.getParameters();
		final SampleIterator eventIterator = sample.getEventIterator();
		SimpleLogger.log(" - Begin writing event data");
		while (eventIterator.hasNext()) {
			final IEventDataAccessor event = eventIterator.next();
			writeParameterValues(event, parameters, fileOutputStream);
		}
		SimpleLogger.log(" - End writing event data");
	}

	private void writeParameterValues(IEventDataAccessor event, List<Parameter> parameters,
	        FileOutputStream fileOutputStream) throws IOException {
		for (final Parameter parameter : parameters) {
			final byte[] parameterValue = event.getBytes(parameter);
			fileOutputStream.write(parameterValue);
		}
	}

}
