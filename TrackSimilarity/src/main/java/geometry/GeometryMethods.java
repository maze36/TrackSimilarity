package geometry;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import model.AISMessage;
import model.Prediction;

public class GeometryMethods {

	private GeometryFactory geoFactory;

	public GeometryMethods() {
		this.geoFactory = new GeometryFactory();
	}

	public double calculateOverallDistance(LineString lineString, ArrayList<Coordinate> coordinates) {
		double result = 0;

		for (Coordinate coordinate : coordinates) {
			Point p = geoFactory.createPoint(coordinate);
			double distance = p.distance(lineString);
			result += distance;
		}

		return result;

	}

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

		int j = 0;

		for (int i = index; i < aisMessages.size(); i++) {
			coordinates[j] = aisMessages.get(i).getPosition();
			j++;
		}

		LineString lineString = this.geoFactory.createLineString(coordinates);

		return lineString;
	}
}
