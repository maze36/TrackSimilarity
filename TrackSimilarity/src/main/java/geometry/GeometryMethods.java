package geometry;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import model.AISMessage;
import model.Prediction;
import model.Track;

public class GeometryMethods {

	private GeometryFactory geoFactory;

	public GeometryMethods() {
		this.geoFactory = new GeometryFactory();
	}

	/**
	 * 
	 * @param predictedTrack
	 * @param historicTrack
	 */
	public static double calculateOverallDistance(Track predictedTrack, Track historicTrack) {
		GeometryFactory factory = new GeometryFactory();
		LineString historicLine = createLineString(createCoordinateArray(historicTrack), factory);

		double overallDistance = 0;

		for (Coordinate coord : predictedTrack.getCoordinatesList()) {
			Point p = factory.createPoint(coord);
			overallDistance = overallDistance + p.distance(historicLine);
		}

		return overallDistance;
	}

	public double calculateOverallDistance(LineString lineString, )

	public LineString createLineString(Prediction prediction) {
		Coordinate[] coordinates = new Coordinate[prediction.getPrediction().size()];

		for (int i = 0; i < prediction.getPrediction().size(); i++) {
			coordinates[i] = prediction.getPrediction().get(i);
		}

		LineString lineString = this.geoFactory.createLineString(coordinates);

		return lineString;
	}

	public LineString createLineString(ArrayList<AISMessage> aisMessages, int index) {

		Coordinate[] coordinates = new Coordinate[aisMessages.size() - index];

		for (int i = index; i < aisMessages.size(); i++) {
			coordinates[i] = aisMessages.get(i).getPosition();
		}

		LineString lineString = this.geoFactory.createLineString(coordinates);

		return lineString;
	}
}
