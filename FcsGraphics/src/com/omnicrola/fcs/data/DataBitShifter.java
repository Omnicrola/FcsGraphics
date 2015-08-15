package com.omnicrola.fcs.data;

import java.nio.ByteOrder;

public enum DataBitShifter {
	INSTANCE;

	private byte[] flipArray(byte[] data) {
		byte t = data[0];
		data[0] = data[1];
		data[1] = data[2];
		data[2] = data[3];
		data[3] = t;
		return data;
	}

	public double translateToDouble(byte[] data) {
		System.out.println("doubling");
		return 0;
	}

	public float translateToFloat(byte[] data, ByteOrder byteOrder) {
		int integerBits = translateToInteger(data, byteOrder);
		float floatBits = Float.intBitsToFloat(integerBits);
		return floatBits;
	}

	public int translateToInteger(byte[] data, ByteOrder byteOrder) {
		if (data.length != 4) {
			throw new RuntimeException("Invalid 32bit byte  length (" + data.length + ")");
		}

		if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
			data = flipArray(data);
		}
		int value = data[3] << 24 | (data[2] & 0xFF) << 16 | (data[1] & 0xFF) << 8
				| (data[0] & 0xFF);

		return value;
	}
}
