package com.omnicrola.fcs;

public class FcsHeaderData implements FcsHeaderDataRead {

	@Override
	public int getLengthInBytes() {
		return 10;
	}

}
