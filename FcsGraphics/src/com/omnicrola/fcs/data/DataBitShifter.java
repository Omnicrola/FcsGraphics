package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public enum DataBitShifter {
	INSTANCE;

	private static final ByteBuffer buffer = ByteBuffer.allocate(4);

	// private byte[] flipArray(byte[] data) {
	// final byte t = data[0];
	// data[0] = data[1];
	// data[1] = data[2];
	// data[2] = data[3];
	// data[3] = t;
	// return data;
	// }

	public double translateToDouble(byte[] data) {
		System.out.println("doubling");
		return 0;
	}

	public float translateToFloat(byte[] data, ByteOrder byteOrder) {
		final int integerBits = translateToInteger(data, byteOrder);
		final float floatBits = Float.intBitsToFloat(integerBits);
		return floatBits;
	}

	public int translateToInteger(byte[] data, ByteOrder byteOrder) {
		throw new RuntimeException("Invalid method");
		// if (data.length != 4) {
		// throw new RuntimeException("Invalid 32bit byte length (" +
		// data.length + ")");
		// }
		//
		// if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
		// data = flipArray(data);
		// }
		// final int value = data[3] << 24 | (data[2] & 0xFF) << 16 | (data[1] &
		// 0xFF) << 8 | (data[0] & 0xFF);
		//
		// return value;
	}

	public byte[] translateFromInteger(int integer) {
		buffer.clear();
		buffer.putInt(integer);
		final byte[] array = buffer.array();
		return Arrays.copyOf(array, 4);
	}
}
