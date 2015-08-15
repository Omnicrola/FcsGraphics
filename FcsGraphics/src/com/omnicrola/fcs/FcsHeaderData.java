package com.omnicrola.fcs;

public class FcsHeaderData implements FcsHeaderDataRead {

	@Override
	public int getHeaderLengthInBytes() {
		return 10;
	}

	@Override
	public int getDataLengthInBytes() {
		return 20;
	}

	@Override
	public String getHeaderText() {
		return "I AM \\TEST $$*#&@ DATA";
	}

}
