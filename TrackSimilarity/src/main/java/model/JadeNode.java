package model;

import java.util.ArrayList;
import java.util.HashMap;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * The representation of a extracted node.
 * 
 * @author msteidel
 *
 */
public class JadeNode {

	private Coordinate position;

	private JadeNodeType jadeNodeType;

	private String name;

	private ArrayList<String> directNeighbors;

	private HashMap<Double, LengthDistribution> lengthDistribution;

	private HashMap<String, ShiptypeDistribution> shiptypeDistribution;

	private int wptNumber;

	public JadeNode(Coordinate position, JadeNodeType jadeNodeType, String name) {
		this.position = position;
		this.jadeNodeType = jadeNodeType;
		this.name = name;
		this.setShiptypeDistribution(new HashMap<String, ShiptypeDistribution>());
		this.setLengthDistribution(new HashMap<Double, LengthDistribution>());
		this.directNeighbors = new ArrayList<String>();
		if (name.contains("WP")) {
			String sNumber = name.substring(name.length() - 1);
			this.wptNumber = Integer.valueOf(sNumber);
		}
	}

	public Coordinate getCoordinate() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public JadeNodeType getJadeNodeType() {
		return jadeNodeType;
	}

	public void setJadeNodeType(JadeNodeType jadeNodeType) {
		this.jadeNodeType = jadeNodeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, ShiptypeDistribution> getShiptypeDistribution() {
		return shiptypeDistribution;
	}

	public void setShiptypeDistribution(HashMap<String, ShiptypeDistribution> shiptypeDistribution) {
		this.shiptypeDistribution = shiptypeDistribution;
	}

	public HashMap<Double, LengthDistribution> getLengthDistribution() {
		return lengthDistribution;
	}

	public void setLengthDistribution(HashMap<Double, LengthDistribution> lengthDistribution) {
		this.lengthDistribution = lengthDistribution;
	}

	public ArrayList<String> getDirectNeighbors() {
		return directNeighbors;
	}

	public void setDirectNeighbors(ArrayList<String> directNeighbors) {
		this.directNeighbors = directNeighbors;
	}

	/**
	 * Checks whether there are any neighbors or not.
	 * 
	 * @return <code>true</code> if so, otherwise <code>false</code>
	 */
	public boolean hasNeighbors() {
		if (this.directNeighbors.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public int getWptNumber() {
		return wptNumber;
	}

	public void setWptNumber(int wptNumber) {
		this.wptNumber = wptNumber;
	}

}
