package com.omnicrola.fcs.render;

import com.omnicrola.fcs.FcsData;
import com.omnicrola.fcs.FcsEventCollection;
import com.omnicrola.fcs.FcsHeaderData;
import com.omnicrola.fcs.image.ImageDataConverter;
import com.omnicrola.fcs.image.ImageDataReader;
import com.omnicrola.fcs.io.FcsDataWriter;
import com.omnicrola.util.Argument;
import com.omnicrola.util.ArgumentParser;
import com.omnicrola.util.ProgramArguments;
import com.omnicrola.util.SimpleLogger;

public class RenderFcs {
	public static void main(String[] args) {
		SimpleLogger.log("Starting...");

		final ProgramArguments arguments = parseArguments(args);
		final FcsData fcsData = createFcsDataModel();
		convertImageToFcsData(arguments, fcsData);
		writeFcsDataToFile(arguments, fcsData);

		SimpleLogger.log("Done.");
	}

	private static ProgramArguments parseArguments(String[] args) {
		final ProgramArguments arguments = ArgumentParser.parse(args);
		arguments.assertPresent(Argument.SOURCE_IMAGE, Argument.TARGET_FILENAME);
		return arguments;
	}

	private static FcsData createFcsDataModel() {
		final FcsHeaderData fcsHeaderData = new FcsHeaderData();
		final FcsEventCollection fcsEventCollection = new FcsEventCollection();
		final FcsData fcsData = new FcsData(fcsHeaderData, fcsEventCollection);
		return fcsData;
	}

	private static void convertImageToFcsData(final ProgramArguments arguments, final FcsData fcsData) {
		final ImageDataReader imageDataReader = new ImageDataReader(arguments.get(Argument.SOURCE_IMAGE));
		final ImageDataConverter imageDataConverter = new ImageDataConverter(imageDataReader);
		fcsData.setData(imageDataConverter);
	}

	private static void writeFcsDataToFile(final ProgramArguments arguments, final FcsData fcsData) {
		final FcsDataWriter fcsDataWriter = new FcsDataWriter(arguments.get(Argument.TARGET_FILENAME));
		fcsDataWriter.write(fcsData);
	}

}
