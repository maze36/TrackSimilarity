package geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import model.Track;

public class GeoMethods {

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

	private static Coordinate[] createCoordinateArray(Track predictedTrack) {
		Coordinate[] result = new Coordinate[predictedTrack.getCoordinatesList().size()];

		for (int i = 0; i < predictedTrack.getCoordinatesList().size(); i++) {
			result[i] = predictedTrack.getCoordinate(i);
		}

		return result;
	}

	/**
	 * Creates and returns a {@link LineString} based on the given
	 * {@link Coordinate}s.
	 * 
	 * @param coordinates
	 * @param factory
	 * @return
	 */
	private static LineString createLineString(Coordinate[] coordinates, GeometryFactory factory) {

		LineString result = factory.createLineString(coordinates);

		return result;
	}

}
