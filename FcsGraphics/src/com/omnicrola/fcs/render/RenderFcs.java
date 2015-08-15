package com.omnicrola.fcs.render;

import java.io.IOException;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.MemoryAllocator;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.fcs.image.ImageDataConverter;
import com.omnicrola.fcs.image.ImageDataReader;
import com.omnicrola.fcs.io.FcsDataWriter;
import com.omnicrola.fcs.io.FcsEventDataWriter;
import com.omnicrola.fcs.io.FcsHeaderDataWriter;
import com.omnicrola.fcs.io.HeaderDataExtractor;
import com.omnicrola.util.Argument;
import com.omnicrola.util.ArgumentParser;
import com.omnicrola.util.ProgramArguments;
import com.omnicrola.util.SimpleLogger;

public class RenderFcs {
	public static void main(String[] args) {
		SimpleLogger.log("Starting...");

		final ProgramArguments arguments = parseArguments(args);
		final Sample sample = convertImageToFcsData(arguments);
		writeFcsDataToFile(arguments, sample);

		SimpleLogger.log("Done.");
	}

	private static ProgramArguments parseArguments(String[] args) {
		final ProgramArguments arguments = ArgumentParser.parse(args);
		arguments.assertPresent(Argument.SOURCE_IMAGE, Argument.TARGET_FILENAME);
		return arguments;
	}

	private static Sample convertImageToFcsData(final ProgramArguments arguments) {
		final ImageDataReader imageDataReader = new ImageDataReader(arguments.get(Argument.SOURCE_IMAGE));
		final ImageDataConverter imageDataConverter = new ImageDataConverter(DataBitShifter.INSTANCE, imageDataReader);
		final Sample sample = new Sample(new MemoryAllocator(), 0);
		imageDataConverter.writeToSample(sample);
		return sample;
	}

	private static void writeFcsDataToFile(final ProgramArguments arguments, final Sample sample) {
		try {
			final String targetFilename = arguments.get(Argument.TARGET_FILENAME);
			final FcsDataWriter fcsDataWriter = createDataWriter(targetFilename);
			fcsDataWriter.write(sample);
		} catch (final IOException e) {
			SimpleLogger.error(e);
		}
	}

	private static FcsDataWriter createDataWriter(final String targetFilename) {
		final FcsHeaderDataWriter headerDataWriter = new FcsHeaderDataWriter();
		final HeaderDataExtractor headerDataExtractor = new HeaderDataExtractor();
		final FcsEventDataWriter eventDataWriter = new FcsEventDataWriter();
		final FcsDataWriter fcsDataWriter = new FcsDataWriter(targetFilename, headerDataWriter, headerDataExtractor,
		        eventDataWriter);
		return fcsDataWriter;
	}

}
