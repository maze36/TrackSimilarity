package model;

import java.util.ArrayList;

public class Track {

	private ArrayList<AISMessage> message;

	private int trackId;

	public Track() {
		this.message = new ArrayList<AISMessage>();
	}

	public ArrayList<AISMessage> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<AISMessage> message) {
		this.message = message;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

}
