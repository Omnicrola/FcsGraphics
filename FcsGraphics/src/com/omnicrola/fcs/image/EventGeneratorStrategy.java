package com.omnicrola.fcs.image;

import com.omnicrola.fcs.data.Parameter;

public class EventGeneratorStrategy {

	private final Parameter xParam;
	private final Parameter yParam;
	private final float xScale;
	private final float yScale;
	private final EventGenerator eventGenerator;

	public EventGeneratorStrategy(EventGenerator eventGenerator, Parameter xParam, Parameter yParam, float xScale,
	        float yScale) {
		this.eventGenerator = eventGenerator;
		this.xParam = xParam;
		this.yParam = yParam;
		this.xScale = xScale;
		this.yScale = yScale;
	}

	public void createEventAtCoordinate(int x, int y, int density) {
		this.eventGenerator.createEventAtCoordinate(this, x, y, density);
	}

	public Parameter getXParam() {
		return this.xParam;
	}

	public float getXScale() {
		return this.xScale;
	}

	public Parameter getYParam() {
		return this.yParam;
	}

	public float getYScale() {
		return this.yScale;
	}

}
