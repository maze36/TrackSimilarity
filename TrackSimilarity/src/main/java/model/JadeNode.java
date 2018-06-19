package model;

import java.util.ArrayList;

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

	public JadeNode(Coordinate position, JadeNodeType jadeNodeType, String name) {
		this.position = position;
		this.jadeNodeType = jadeNodeType;
		this.name = name;
		this.directNeighbors = new ArrayList<String>();
	}

	public JadeNode(Coordinate position2, String name2) {
		this.position = position2;
		this.name = name2;
	}

	public Coordinate getPosition() {
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

}
