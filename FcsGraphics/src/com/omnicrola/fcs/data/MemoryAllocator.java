package com.omnicrola.fcs.data;

import java.nio.ByteBuffer;

public class MemoryAllocator {

	public ByteBuffer allocate(int size) {
		return ByteBuffer.allocate(size);
	}
}
