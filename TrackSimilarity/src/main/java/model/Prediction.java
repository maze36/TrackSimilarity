package model;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

public class Prediction {

	private Integer mmsi;

	private Shiptype shiptype;

	private Double vesselLength;

	private ArrayList<Coordinate> prediction;

	public Prediction(Integer mmsi, Shiptype shiptype, Double vesselLength, ArrayList<Coordinate> prediction) {
		this.mmsi = mmsi;
		this.shiptype = shiptype;
		this.vesselLength = vesselLength;
		this.prediction = prediction;
	}

	public Prediction() {
		this.prediction = new ArrayList<Coordinate>();
	}

	public Integer getMmsi() {
		return mmsi;
	}

	public Shiptype getShiptype() {
		return shiptype;
	}

	public Double getVesselLength() {
		return vesselLength;
	}

	public ArrayList<Coordinate> getPrediction() {
		return prediction;
	}

	public void setMmsi(Integer mmsi) {
		this.mmsi = mmsi;
	}

	public void setShiptype(Shiptype shiptype) {
		this.shiptype = shiptype;
	}

	public void setVesselLength(Double vesselLength) {
		this.vesselLength = vesselLength;
	}

	public void setPrediction(ArrayList<Coordinate> prediction) {
		this.prediction = prediction;
	}

}
