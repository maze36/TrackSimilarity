package controller;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import org.geotools.referencing.GeodeticCalculator;

import com.vividsolutions.jts.geom.Coordinate;

import model.LengthUnit;
import model.MathematicalOperation;

/**
 * 
 * @author Matthias Steidel
 *
 */
public class GeoUtil {

	/**
	 * Calculates a new position based on a given starting point, a distance and a
	 * direction.
	 * 
	 * @param start
	 *            The start {@link Coordinate} for the calculation.
	 * @param distance
	 *            The distance in NM of the new position based on the start.
	 * @param direction
	 *            The direction in which the new position shall be located.
	 * @return The new position as a {@link Point2D}.
	 */
	public static Point2D calculateNewPosition(Coordinate start, double distance, double direction) {

		GeodeticCalculator calculator = new GeodeticCalculator();

		calculator.setStartingGeographicPoint(start.y, start.x);

		distance = distance * 1852;

		calculator.setDirection(direction, distance);

		Point2D result = calculator.getDestinationGeographicPoint();
		result.setLocation(result.getY(), result.getX());

		return result;
	}

	/**
	 * Adds or subtracts a value to/from a given COG correctly.
	 * 
	 * @param cog
	 *            The given COG.
	 * @param value
	 *            The value which has to be added/subtracted.
	 * @param operation
	 *            The {@link MathematicalOperation}.
	 * @return The resulting COG
	 */
	public static double addOrSubtractAzimuth(double cog, double value, MathematicalOperation operation) {
		switch (operation) {
		case ADDING:
			if ((cog + value) > 360) {
				double rest = 360 - cog;
				rest = value - rest;
				double result = 0 + rest;
				return result;
			} else {
				double result = cog + value;
				return result;
			}
		case SUBTRACTING:
			if ((cog - value) < 0) {
				double rest = value - cog;
				double result = 360 - rest;
				return result;
			} else {
				double result = cog - value;
				return result;
			}
		}
		return 0;
	}

	/**
	 * Calculates the distance between a two given points and returns the result in
	 * the desired unit.
	 * 
	 * @param start
	 *            The start {@link Coordinate}.
	 * @param end
	 *            The target {@link Coordinate}.
	 * @param unit
	 *            The desired {@link LengthUnit} of the result.
	 * @return The distance in the desired {@link LengthUnit}.
	 */
	public static double calculateDistance(Coordinate start, Coordinate end, LengthUnit unit) {
		GeodeticCalculator calculator = new GeodeticCalculator();
		calculator.setStartingGeographicPoint(start.y, start.x);
		calculator.setDestinationGeographicPoint(end.y, end.x);
		double distance = calculator.getOrthodromicDistance();

		switch (unit) {
		case NAUTICALMILES:
			distance = distance * 0.000539957;
			return distance;
		case METER:
			return distance;
		}

		return distance;
	}

	/**
	 * Calculates the azimuth in degrees between two given points.
	 * 
	 * @param start
	 *            The start {@link Coordinate}.
	 * @param end
	 *            The target {@link Coordinate}.
	 * @return The azimuth in degrees.
	 */
	public static double calculateAzimuthInDegrees(Coordinate start, Coordinate end) {
		GeodeticCalculator calculator = new GeodeticCalculator();
		calculator.setStartingGeographicPoint(start.y, start.x);
		calculator.setDestinationGeographicPoint(end.y, end.x);
		double azimuth = calculator.getAzimuth();

		calculator.getCoordinateReferenceSystem();

		double azimuthInDegrees = 0;

		if (azimuth < 0.0) {
			azimuthInDegrees = 360.0 + azimuth;
		} else {
			azimuthInDegrees = azimuth;
		}

		return azimuthInDegrees;

	}

	/**
	 * Calculates a random number between given min and max. Uses
	 * {@link ThreadLocalRandom}.
	 * 
	 * @param min
	 * @param max
	 * @return The random Number
	 */
	public static int generateRandomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static Coordinate calculateNewPositionAsCoordinate(Coordinate start, double distance, double direction) {

		GeodeticCalculator calculator = new GeodeticCalculator();

		calculator.setStartingGeographicPoint(start.y, start.x);

		distance = distance * 1852;

		calculator.setDirection(direction, distance);

		Point2D point = calculator.getDestinationGeographicPoint();
		point.setLocation(point.getY(), point.getX());

		return new Coordinate(point.getX(), point.getY());
	}

}
