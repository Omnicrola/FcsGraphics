package com.omnicrola.fcs.data;

public enum DataType {
	INTEGER("I", 4), FLOAT("F", 4), DOUBLE("D", 8), ASCII("A", 4);

	public static DataType getByValue(String abbreviation) {
		DataType[] values = DataType.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].value.equals(abbreviation)) {
				return values[i];
			}
		}
		return INTEGER;
	}

	private final String value;

	private final int byteSize;

	private DataType(String value, int byteSize) {
		this.value = value;
		this.byteSize = byteSize;
	}

	public int byteSize() {
		return this.byteSize;
	}
}