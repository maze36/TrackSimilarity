package geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class GeoMethods {

	/**
	 * Creates and returns a {@link LineString} based on the given
	 * {@link Coordinate}s.
	 * 
	 * @param coordinates
	 * @return
	 */
	public static LineString createLineString(Coordinate[] coordinates) {

		GeometryFactory factory = new GeometryFactory();

		LineString result = factory.createLineString(coordinates);

		return result;
	}

}
