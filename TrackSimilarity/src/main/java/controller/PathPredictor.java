package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import model.AISMessage;
import model.JadeNode;
import model.LengthDistribution;
import model.Prediction;
import model.ShiptypeDistribution;
import model.knowledgeBase.InnenjadeKnowledgeBase;

public class PathPredictor {
	public static PathPredictor INSTANCE = new PathPredictor();

	private static InnenjadeKnowledgeBase knowledgeBase;

	public PathPredictor() {

	}

	/**
	 * Calculates and predicts the possible path for a given {@link TargetShip}.
	 * 
	 * @param aisMessage
	 * @return
	 */
	public Prediction getPossiblePath(AISMessage aisMessage) {
		Coordinate tsPoCoordinate = aisMessage.getPosition();
		double tsCog = aisMessage.getCog();
		JadeNode nearestNode = knowledgeBase.findNearestNode(tsPoCoordinate);
		ArrayList<Coordinate> predictedTrack = new ArrayList<Coordinate>();
		if (isInMovingDirection(aisMessage, nearestNode)) {
			if (nearestNode.getName().contains("WP")) {
				predictedTrack.add(nearestNode.getCoordinate());
				String direction = checkMovingDirection(tsCog);
				if (direction.equals("NORTH")) {
					return iterateToExit(nearestNode.getWptNumber(), predictedTrack, aisMessage);
				} else if (direction.equals("SOUTH")) {
					JadeNode destination = determineDestination(nearestNode, aisMessage);
					return findPathToDestination(nearestNode, destination, predictedTrack, aisMessage);
				}
			} else {
				return calculatePath(aisMessage, predictedTrack);
			}
		} else {
			// Wp
			if (nearestNode.getName().contains("WP")) {
				ArrayList<JadeNode> possibleNextNodes = new ArrayList<JadeNode>();
				for (String neighborString : nearestNode.getDirectNeighbors()) {
					JadeNode neighborNode = knowledgeBase.getJadeNodeByName(neighborString);
					if (neighborNode == null) {
						System.out.println();
					}
					if (isInMovingDirection(aisMessage, neighborNode)) {
						possibleNextNodes.add(neighborNode);
					}
				}
				return selectNodeWithMinBearing(predictedTrack, possibleNextNodes, aisMessage);
				// Destination
			} else {
				String direction = checkMovingDirection(tsCog);
				if (direction.equals("SOUTH")) {
					JadeNode destination = determineDestination(nearestNode, aisMessage);
					predictedTrack = findAllWpts(aisMessage, destination, predictedTrack);
					predictedTrack.add(destination.getCoordinate());
					return calculatePath(aisMessage, predictedTrack);
				} else {
					JadeNode wp = knowledgeBase.getJadeNodeByName(nearestNode.getDirectNeighbors().get(0));
					return iterateToExit(wp.getWptNumber(), predictedTrack, aisMessage);
				}
			}
		}
		return null;
	}

	/**
	 * Calculates all waypoints in order to reach the destination.
	 * 
	 * @param aisMessage
	 * @param destination
	 * @param predictedTrack
	 * @return
	 */
	private ArrayList<Coordinate> findAllWpts(AISMessage aisMessage, JadeNode destination,
			ArrayList<Coordinate> predictedTrack) {
		JadeNode destinationWpt = knowledgeBase.getJadeNodeByName(destination.getDirectNeighbors().get(0));

		double tsCog = aisMessage.getCog();

		int wptNumber = destinationWpt.getWptNumber();

		boolean finished = false;

		while (!finished) {
			JadeNode wpt = knowledgeBase.getJadeNodeByName("WP" + wptNumber);
			double bearing = GeoUtil.calculateAzimuthInDegrees(aisMessage.getPosition(), wpt.getCoordinate());

			double cogDiff = Math.abs(tsCog - bearing);

			if (cogDiff <= 45) {
				predictedTrack.add(wpt.getCoordinate());
			} else {
				finished = true;
			}

			wptNumber -= 1;

			if (wptNumber == 0) {
				finished = true;
			}

		}

		return predictedTrack;
	}

	/**
	 * 
	 * @param predictedTrack
	 * @param possibleNextNodes
	 * @param aisMessage
	 * @return
	 */
	private Prediction selectNodeWithMinBearing(ArrayList<Coordinate> predictedTrack,
			ArrayList<JadeNode> possibleNextNodes, AISMessage aisMessage) {

		double minBearing = -1;
		JadeNode minNode = null;

		for (JadeNode node : possibleNextNodes) {
			double bearing = GeoUtil.calculateAzimuthInDegrees(aisMessage.getPosition(), node.getCoordinate());
			if (minBearing == -1) {
				minBearing = bearing;
				minNode = node;
			} else if (minBearing > bearing) {
				minBearing = bearing;
				minNode = node;
			}
		}

		if (minNode != null) {
			predictedTrack.add(minNode.getCoordinate());
			String direction = checkMovingDirection(minBearing);
			if (direction.equals("SOUTH")) {
				JadeNode destination = determineDestination(minNode, aisMessage);
				predictedTrack = findAllWpts(aisMessage, destination, predictedTrack);
				predictedTrack.add(destination.getCoordinate());
				return calculatePath(aisMessage, predictedTrack);
			} else if (direction.equals("NORTH")) {
				predictedTrack.add(minNode.getCoordinate());
				return iterateToExit(minNode.getWptNumber(), predictedTrack, aisMessage);
			}
		}

		return null;
	}

	/**
	 * Finds the path to the given destination {@link JadeNode} from the nearest way
	 * point.
	 * 
	 * @param nearestWp
	 * @param destination
	 * @param predictedTrack
	 * @param aisMessage
	 * @return
	 */
	private Prediction findPathToDestination(JadeNode nearestWp, JadeNode destination,
			ArrayList<Coordinate> predictedTrack, AISMessage aisMessage) {

		int wptNumber = nearestWp.getWptNumber();
		int wptNumberDestination = knowledgeBase.getJadeNodeByName(destination.getDirectNeighbors().get(0))
				.getWptNumber();

		if (wptNumberDestination == wptNumber) {
			predictedTrack.add(destination.getCoordinate());
		} else if (wptNumberDestination < wptNumber) {
			wptNumber -= 1;
			predictedTrack.add(knowledgeBase.getJadeNodeByName("WP" + wptNumber).getCoordinate());
		}

		return calculatePath(aisMessage, predictedTrack);
	}

	/**
	 * Determines the possible destination {@link JadeNode} for incoming
	 * {@link TargetShip} based either on the {@link ShiptypeDistribution} or the
	 * {@link LengthDistribution}. If there are no observation for either one, a
	 * random destination is picked.
	 * 
	 * @param nearestNode
	 * @param aisMessage
	 * @return
	 */
	private JadeNode determineDestination(JadeNode nearestNode, AISMessage aisMessage) {

		int wptNumber = nearestNode.getWptNumber();

		if (wptNumber == 0) {
			JadeNode wpt = knowledgeBase.getJadeNodeByName(nearestNode.getDirectNeighbors().get(0));
			wptNumber = wpt.getWptNumber();
			if (!isInMovingDirection(aisMessage, wpt)) {
				wptNumber += 1;
			}
		}

		ArrayList<String> possibleDestinationsStrings = knowledgeBase
				.getPossibleDestinationsByShiptype(aisMessage.getShiptype());
		ArrayList<JadeNode> possibleDestinationsNode = new ArrayList<JadeNode>();

		for (String neighborString : possibleDestinationsStrings) {
			JadeNode neighbor = knowledgeBase.getJadeNodeByName(neighborString);
			if (neighbor != null) {

				for (String neighbors : neighbor.getDirectNeighbors()) {
					JadeNode n = knowledgeBase.getJadeNodeByName(neighbors);
					if (wptNumber <= n.getWptNumber()) {
						possibleDestinationsNode.add(neighbor);
					}
				}
			}
		}

		// Ship type is first option
		HashMap<JadeNode, Double> concreteDestinations = new HashMap<JadeNode, Double>();
		for (JadeNode possibleDestination : possibleDestinationsNode) {
			ShiptypeDistribution shiptypeDistribution = possibleDestination.getShiptypeDistribution()
					.get(aisMessage.getShiptype());
			if (shiptypeDistribution != null) {
				concreteDestinations.put(possibleDestination, shiptypeDistribution.getRelative());
			}
		}

		// Length is fall back
		if (concreteDestinations.isEmpty()) {
			for (JadeNode possibleDestination : possibleDestinationsNode) {
				LengthDistribution lengthDistribution = possibleDestination.getLengthDistribution()
						.get(aisMessage.getLength());
				if (lengthDistribution != null) {
					concreteDestinations.put(possibleDestination, lengthDistribution.getRelative());
				}
			}
		}

		if (!concreteDestinations.isEmpty()) {
			return findMaxRelative(concreteDestinations);
		} else {

			if (possibleDestinationsNode.isEmpty()) {
				int randomDestination = GeoUtil.generateRandomNumber(0, possibleDestinationsStrings.size() - 1);
				return knowledgeBase.getJadeNodeByName(possibleDestinationsStrings.get(randomDestination));
			} else {
				int randomDestination = GeoUtil.generateRandomNumber(0, possibleDestinationsNode.size() - 1);
				return possibleDestinationsNode.get(randomDestination);
			}

		}
	}

	/**
	 * Returns the {@link JadeNode}
	 * 
	 * @param concreteDestinations
	 * @return
	 */
	private JadeNode findMaxRelative(HashMap<JadeNode, Double> concreteDestinations) {
		Map.Entry<JadeNode, Double> maxEntry = null;

		for (Map.Entry<JadeNode, Double> entry : concreteDestinations.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		return maxEntry.getKey();
	}

	private Prediction iterateToExit(int wptNumber, ArrayList<Coordinate> predictedTrack, AISMessage aisMessage) {
		if (wptNumber == 1) {
			JadeNode exitNode = knowledgeBase.getJadeNodeByName("Exit");
			predictedTrack.add(exitNode.getCoordinate());
			return calculatePath(aisMessage, predictedTrack);
		} else {
			while (wptNumber > 1) {
				wptNumber -= 1;
				predictedTrack.add(knowledgeBase.getJadeNodeByName("WP" + wptNumber).getCoordinate());
			}
			JadeNode exitNode = knowledgeBase.getJadeNodeByName("Exit");
			predictedTrack.add(exitNode.getCoordinate());
			return calculatePath(aisMessage, predictedTrack);
		}
	}

	/**
	 * Calculates the path between the current position of a {@link TargetShip} and
	 * the next {@link JadeNode} in the given {@link ArrayList}.
	 * 
	 * @param aisMessage
	 * @param points
	 * @return The path is stored in a {@link SimpleBehaviorPrediction}-object.
	 */
	private Prediction calculatePath(AISMessage aisMessage, ArrayList<Coordinate> points) {

		if (points.isEmpty()) {
			return null;
		}

		double bearingToNextPoint = GeoUtil.calculateAzimuthInDegrees(aisMessage.getPosition(), points.get(0));
		ArrayList<Coordinate> lastCoordinates = new ArrayList<Coordinate>();
		lastCoordinates.addAll(points);
		points.clear();

		double rot = determineRot(aisMessage);
		double cogDiff = Math.abs((aisMessage.getCog() - bearingToNextPoint) % 360);
		double sog = aisMessage.getSog();
		double direction = aisMessage.getCog();
		Coordinate start = aisMessage.getPosition();
		points.add(start);
		double distance = sog / 3600;
		int counter = 0;
		if (cogDiff > 0) {
			while (cogDiff > 1) {
				start = GeoUtil.calculateNewPositionAsCoordinate(start, distance, direction);
				if (counter % 10 == 0) {
					points.add(start);
				}
				bearingToNextPoint = GeoUtil.calculateAzimuthInDegrees(start, lastCoordinates.get(0));
				direction = direction - rot;
				cogDiff = (direction - bearingToNextPoint) % 360;
				counter++;
			}
		} else if (cogDiff < 0) {
			while (cogDiff < -1) {
				start = GeoUtil.calculateNewPositionAsCoordinate(start, distance, direction);
				if (counter % 10 == 0) {
					points.add(start);
				}
				bearingToNextPoint = GeoUtil.calculateAzimuthInDegrees(start, lastCoordinates.get(0));
				direction = direction + rot;
				cogDiff = (direction - bearingToNextPoint) % 360;
				counter++;
			}
		}

		points.addAll(lastCoordinates);

		return new Prediction(aisMessage.getMmsi(), aisMessage.getShiptype(), aisMessage.getLength(), points);
	}

	/**
	 * Return the rate of turn for the given {@link TargetShip}.
	 * 
	 * @param targetShip
	 * @return 1 for <code>length <= 80</code>, 0.75 for
	 *         <code>length > 80 && length <= 230</code> and 0.5 for
	 *         <code>length > 230</code>.
	 */
	@SuppressWarnings("unused")
	private double determineRot(AISMessage aisMessage) {
		Double length = aisMessage.getLength();

		if (length != null) {
			if (length > 80 && length <= 230) {
				return 0.75;
			} else if (length > 230) {
				return 0.5;
			} else {
				return 1;
			}
		} else {
			return 0.5;
		}
	}

	private boolean isInMovingDirection(AISMessage aisMessage, JadeNode node) {
		double bearing = GeoUtil.calculateAzimuthInDegrees(aisMessage.getPosition(), node.getCoordinate());
		double cogBearingDiff = Math.abs(aisMessage.getCog() - bearing) % 360;
		if (cogBearingDiff <= 45) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines with the given course over ground if the vessel is going "NORTH"
	 * or "SOUTH". If nothing is fulfilled, "TBD" is returned.
	 * 
	 * @param cog
	 * @return
	 */
	private String checkMovingDirection(double cog) {
		if ((cog >= 120 && cog <= 215.6)) {
			return "SOUTH";
		} else if ((cog >= 300 && cog <= 360) || (cog >= 0 && cog <= 71.2)) {
			return "NORTH";
		} else {
			return "TBD";
		}
	}

	public void init() {
		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Initialzing " + this.getClass().getName());
		knowledgeBase = new InnenjadeKnowledgeBase();
	}
}
