package model;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

public class Track {

	private ArrayList<Coordinate> coordinates;

	public Track(ArrayList<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinate getCoordinate(int index) {
		return coordinates.get(index);
	}

	public ArrayList<Coordinate> getCoordinatesList() {
		return this.coordinates;
	}

}
