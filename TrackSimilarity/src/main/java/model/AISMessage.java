package model;

import com.vividsolutions.jts.geom.Coordinate;

public class AISMessage {

	private Integer mmsi;

	private double heading;

	private double sog;

	private double cog;

	private double lat;

	private double lon;

	private String timestamp;

	private double length;

	private Shiptype shiptype;

	private String filename;

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

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public String getTimestamp() {
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

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setShiptype(String shiptype) {
		String type = shiptype.replaceAll("\"", "");
		if (type.equals("GeneralCargo")) {
			this.shiptype = Shiptype.CARGO;
		} else if (type.equals("Tanker")) {
			this.shiptype = Shiptype.TANKER;
		}
	}

	public Coordinate getPosition() {
		return new Coordinate(this.lat, this.lon);
	}

	@Override
	public String toString() {
		return "AISMessage [mmsi=" + mmsi + ", heading=" + heading + ", sog=" + sog + ", cog=" + cog + ", lat=" + lat
				+ ", lon=" + lon + ", timestamp=" + timestamp + ", length=" + length + ", shiptype=" + shiptype
				+ ", filename=" + filename + "]";
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
