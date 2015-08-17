package com.omnicrola.fcs.render;

import java.io.IOException;
import java.util.List;

import com.omnicrola.fcs.data.DataBitShifter;
import com.omnicrola.fcs.data.MemoryAllocator;
import com.omnicrola.fcs.data.Sample;
import com.omnicrola.fcs.image.EventGeneratorStrategyBuilder;
import com.omnicrola.fcs.image.ImageDataConverter;
import com.omnicrola.fcs.image.ImageDataReader;
import com.omnicrola.fcs.io.FcsDataWriter;
import com.omnicrola.fcs.io.FcsEventDataWriter;
import com.omnicrola.fcs.io.FcsHeaderDataWriter;
import com.omnicrola.fcs.io.HeaderDataExtractor;
import com.omnicrola.util.Argument;
import com.omnicrola.util.ArgumentParser;
import com.omnicrola.util.FileFinder;
import com.omnicrola.util.ProgramArguments;
import com.omnicrola.util.SimpleLogger;

public class RenderFcs {
	public static void main(String[] args) {
		SimpleLogger.log("Starting...");
		final long startTime = System.nanoTime();

		final ProgramArguments arguments = parseArguments(args);
		final Sample sample = convertImageToFcsData(arguments);
		writeFcsDataToFile(arguments, sample);

		final float elapsed = (System.nanoTime() - startTime) / 1_000_000f;
		SimpleLogger.log("Done (" + elapsed + "ms)");
	}

	private static ProgramArguments parseArguments(String[] args) {
		final ProgramArguments arguments = ArgumentParser.parse(args);
		if (arguments.isNotPresent(Argument.SOURCE_IMAGE)) {
			arguments.set(Argument.SOURCE_IMAGE, FileFinder.findFile("Source Image"));
		}
		if (arguments.isNotPresent(Argument.TARGET_FILENAME)) {
			arguments.set(Argument.TARGET_FILENAME, FileFinder.findFile("Target FCS Filename"));
		}
		return arguments;
	}

	private static Sample convertImageToFcsData(final ProgramArguments arguments) {
		final List<String> imageFilenames = arguments.getAll(Argument.SOURCE_IMAGE);
		final ImageDataReader imageDataReader = new ImageDataReader(imageFilenames);
		final Sample sample = new Sample(new MemoryAllocator(), 0);
		final EventGeneratorStrategyBuilder eventGeneratorFactory = new EventGeneratorStrategyBuilder(DataBitShifter.INSTANCE, sample);
		final ImageDataConverter imageDataConverter = new ImageDataConverter(imageDataReader, eventGeneratorFactory);

		imageDataConverter.writeToSample(sample);
		return sample;
	}

	private static void writeFcsDataToFile(final ProgramArguments arguments, final Sample sample) {
		try {
			final String targetFilename = arguments.getSingle(Argument.TARGET_FILENAME);
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
