package model;

public class CogInterval {

	private double min;

	private double max;

	public CogInterval(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public boolean isInInterval(double value) {
		if (value >= min && value < max) {
			return true;
		} else {
			return false;
		}
	}
}
