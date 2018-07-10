package model;

/**
 * Represents the shiptype distribution for a certain node.
 * 
 * @author msteidel
 *
 */
public class ShiptypeDistribution {
	private double absolute;
	private double relative;

	public ShiptypeDistribution(double absolute, double relative) {
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
