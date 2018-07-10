package model;

/**
 * Represents the length distribution for a certain node.
 * 
 * @author msteidel
 *
 */
public class LengthDistribution {

	private double absolute;
	private double relative;

	public LengthDistribution(double absolute, double relative) {
		this.setAbsolute(absolute);
		this.setRelative(relative);
	}

	public double getRelative() {
		return relative;
	}

	public void setRelative(double relative) {
		this.relative = relative;
	}

	public double getAbsolute() {
		return absolute;
	}

	public void setAbsolute(double absolute) {
		this.absolute = absolute;
	}

}
