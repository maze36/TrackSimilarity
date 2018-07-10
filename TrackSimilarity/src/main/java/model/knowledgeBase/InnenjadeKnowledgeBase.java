package model.knowledgeBase;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import input.CSVReader;
import model.InnenjadeQuadtree;
import model.JadeNode;
import model.Shiptype;

public class InnenjadeKnowledgeBase {

	private InnenjadeQuadtree historicInnenjadeNodes;

	private Envelope vesselMovementArea;

	private Polygon innenjadeGeometry;

	private ArrayList<String> possibleDestinationsTanker;

	private ArrayList<String> possibleDestinationsGeneralCargo;

	private ArrayList<String> possibleDestinationsPassenger;

	/**
	 * {@link InnenjadeKnowledgeBase} will be initialized.
	 */
	public InnenjadeKnowledgeBase() {
		initKnowledgeBase();
		initDestinationListTanker();
		initDestinationListGeneralCargo();
		initDestinationListPassenger();
	}

	private void initDestinationListPassenger() {
		this.possibleDestinationsPassenger = new ArrayList<String>();
		this.possibleDestinationsPassenger.add("Nassauhafen");
		this.possibleDestinationsPassenger.add("Helgolandkai");
		this.possibleDestinationsPassenger.add("Ost-West-Mole");
	}

	private void initDestinationListGeneralCargo() {
		this.possibleDestinationsGeneralCargo = new ArrayList<String>();
		this.possibleDestinationsGeneralCargo.add("JadeWeser Port");
		this.possibleDestinationsGeneralCargo.add("Niedersachsenbruecke");
		this.possibleDestinationsGeneralCargo.add("Ost-West-Mole");
	}

	private void initDestinationListTanker() {
		this.possibleDestinationsTanker = new ArrayList<String>();
		this.possibleDestinationsTanker.add("JadeWeserPort");
		this.possibleDestinationsTanker.add("Ost-West-Mole");
		this.possibleDestinationsTanker.add("NWO-Oelhafen");
		this.possibleDestinationsTanker.add("Niedersachsenbruecke");
		this.possibleDestinationsTanker.add("Umschlaglager Voslapper Goden");
		this.possibleDestinationsTanker.add("WRG Tankerumschlag Inselanleger");
	}

	public ArrayList<String> getPossibleDestinationsByShiptype(Shiptype shiptype) {
		switch (shiptype) {
		case TANKER:
			return this.possibleDestinationsTanker;
		case TANKER2:
			return this.possibleDestinationsTanker;
		case TANKER_HAZARD_A:
			return this.possibleDestinationsTanker;
		case TANKER_HAZARD_B:
			return this.possibleDestinationsTanker;
		case TANKER_HAZARD_C:
			return this.possibleDestinationsTanker;
		case TANKER_HAZARD_D:
			return this.possibleDestinationsTanker;
		case CARGO:
			return this.possibleDestinationsGeneralCargo;
		case CARGO2:
			return this.possibleDestinationsGeneralCargo;
		case CARGO_HAZARD_A:
			return this.possibleDestinationsGeneralCargo;
		case CARGO_HAZARD_B:
			return this.possibleDestinationsGeneralCargo;
		case CARGO_HAZARD_C:
			return this.possibleDestinationsGeneralCargo;
		case CARGO_HAZARD_D:
			return this.possibleDestinationsGeneralCargo;
		case PASSENGER:
			return this.possibleDestinationsPassenger;
		default:
			return null;
		}
	}

	/**
	 * Init-Method. Will read in CSV-files which contain all required node
	 * information.
	 */
	private void initKnowledgeBase() {
		this.historicInnenjadeNodes = CSVReader.readJadeNodes("jadeNodes/jadeNodes.csv");
		this.historicInnenjadeNodes = CSVReader.readStatisticalDistribution("jadeNodes/statisticalDistribution",
				this.historicInnenjadeNodes);

		Coordinate[] coordinates = new Coordinate[6];
		coordinates[0] = new Coordinate(53.388522, 8.097305);
		coordinates[1] = new Coordinate(53.829375, 7.820190);
		coordinates[2] = new Coordinate(53.859352, 8.145842);
		coordinates[3] = new Coordinate(53.614136, 8.286728);
		coordinates[4] = new Coordinate(53.385398, 8.350241);
		coordinates[5] = new Coordinate(53.388522, 8.097305);

		GeometryFactory factory = new GeometryFactory();
		this.innenjadeGeometry = factory.createPolygon(coordinates);

	}

	public InnenjadeQuadtree getHistoricJadeNodes() {
		return historicInnenjadeNodes;
	}

	public Envelope getVesselMovementArea() {
		return vesselMovementArea;
	}

	public void setHistoricJadeNodes(InnenjadeQuadtree historicJadeNodes) {
		this.historicInnenjadeNodes = historicJadeNodes;
	}

	public void setVesselMovementArea(Envelope vesselMovementArea) {
		this.vesselMovementArea = vesselMovementArea;
	}

	public Polygon getInnenjadeGeometry() {
		return innenjadeGeometry;
	}

	public JadeNode findNearestNode(Coordinate tsPosition) {
		return this.historicInnenjadeNodes.findNearestJadeNode(tsPosition);
	}

	/**
	 * Finds and return the {@link JadeNode} object by the given {@link String}.
	 * 
	 * @param name
	 *            The name of the {@link JadeNode}.
	 * @return The appropriate {@link JadeNode}.
	 */
	public JadeNode getJadeNodeByName(String name) {
		ArrayList<JadeNode> allNodes = this.historicInnenjadeNodes.getAllElements();
		for (JadeNode node : allNodes) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Returns all neighbors of a given {@link JadeNode}.
	 * 
	 * @param jadeNode
	 *            The {@link JadeNode} which neighbors has to be returned.
	 * @return An {@link ArrayList} containing the neighbors as {@link JadeNode}
	 *         objects.
	 */
	public ArrayList<JadeNode> getAllNeighborsOfJadeNode(JadeNode jadeNode) {
		ArrayList<JadeNode> result = new ArrayList<JadeNode>();
		for (String name : jadeNode.getDirectNeighbors()) {
			for (JadeNode node : historicInnenjadeNodes.getAllElements()) {
				if (node.getName().contains(name)) {
					result.add(node);
				}
			}
		}
		return result;
	}

	public boolean isInInnenjade(Coordinate position) {
		GeometryFactory factory = new GeometryFactory();
		Point p = factory.createPoint(position);
		if (this.innenjadeGeometry.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

	public JadeNode findNodeByCoordinate(Coordinate coordinate) {
		for (JadeNode jadeNode : historicInnenjadeNodes.getAllElements()) {
			if ((coordinate.x == jadeNode.getCoordinate().x) && (coordinate.y == jadeNode.getCoordinate().y)) {
				return jadeNode;
			}
		}

		return null;
	}
}
