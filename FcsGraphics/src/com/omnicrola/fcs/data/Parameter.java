package com.omnicrola.fcs.data;

public class Parameter {

	public static final Parameter BLANK = new Parameter("", "", 0, false, DataType.INTEGER, 10);

	public static Parameter makeDefault(int index) {
		return new Parameter("NONE", "NONE", index, false, DataType.INTEGER, 10);
	}

	private final int index;
	private final String shortName;
	private final boolean isFlourescent;
	private final DataType dataType;
	private final double maxRange;
	private final String longName;

	public Parameter(String shortName, String longName, int index, boolean isFlourescent, DataType dataType,
	        double maxRange) {
		this.shortName = shortName;
		this.longName = longName;
		this.index = index;
		this.isFlourescent = isFlourescent;
		this.dataType = dataType;
		this.maxRange = maxRange;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public int getIndex() {
		return this.index;
	}

	public String getLongName() {
		return this.longName;
	}

	public double getMaxRange() {
		return this.maxRange;
	}

	public String getShortName() {
		return this.shortName;
	}

	public boolean isFlourescent() {
		return this.isFlourescent;
	}
}
