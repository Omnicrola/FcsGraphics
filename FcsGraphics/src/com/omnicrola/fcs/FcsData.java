package com.omnicrola.fcs;

public class FcsData {

	private IFcsDataProvider fcsDataProvider;
	private final FcsHeaderData fcsHeaderData;
	private final FcsEventCollection fcsEventCollection;

	public FcsData(FcsHeaderData fcsHeaderData, FcsEventCollection fcsEventCollection) {
		this.fcsHeaderData = fcsHeaderData;
		this.fcsEventCollection = fcsEventCollection;
	}

	public void setData(IFcsDataProvider fcsDataProvider) {
		this.fcsDataProvider = fcsDataProvider;
	}

	public FcsHeaderDataRead getHeader() {
		return this.fcsHeaderData;
	}

}
