package model;

import java.util.Date;

import com.vividsolutions.jts.geom.Coordinate;

public class AISMessage {

	private Integer mmsi;

	private double heading;

	private double sog;

	private double cog;

	private double rot;

	private double lat;

	private double lon;

	private CogInterval cogBinned;

	private Date timestamp;

	private double length;

	private Shiptype shiptype;

	public AISMessage() {

	}

	public Integer getMmsi() {
		return mmsi;
	}

	public double getHeading() {
		return heading;
	}

	public double getSog() {
		return sog;
	}

	public double getCog() {
		return cog;
	}

	public double getRot() {
		return rot;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public CogInterval getCogBinned() {
		return cogBinned;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public double getLength() {
		return length;
	}

	public Shiptype getShiptype() {
		return shiptype;
	}

	public void setMmsi(Integer mmsi) {
		this.mmsi = mmsi;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public void setSog(double sog) {
		this.sog = sog;
	}

	public void setCog(double cog) {
		this.cog = cog;
	}

	public void setRot(double rot) {
		this.rot = rot;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setCogBinned(CogInterval cogBinned) {
		this.cogBinned = cogBinned;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setShiptype(Shiptype shiptype) {
		this.shiptype = shiptype;
	}

	public Coordinate getPosition() {
		return new Coordinate(this.lat, this.lon);
	}

}
