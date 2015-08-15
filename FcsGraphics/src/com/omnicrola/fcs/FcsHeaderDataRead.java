package com.omnicrola.fcs;

public interface FcsHeaderDataRead {

	int getHeaderLengthInBytes();

	int getDataLengthInBytes();

	String getHeaderText();

}
