package com.omnicrola.fcs.data;

public class Momento {

	public static final Momento ZERO = new Momento(0);
	private final int pointer;

	public Momento(int pointer) {
		this.pointer = pointer;
	}

	public int getPointer() {
		return this.pointer;
	}

	@Override
	public String toString() {
		return "Momento[" + this.pointer + "]";
	}

}
