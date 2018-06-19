package model;

import java.util.ArrayList;
import java.util.List;

import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

public class InnenjadeQuadtree {

	private InnenjadeQuadtreeNode root;
	private ArrayList<JadeNode> store;
	private int maxLevel;
	private int maxElementsPerNode;

	public InnenjadeQuadtree() {
		super();
	}

	/**
	 * 
	 * @param env
	 *            area that is represented by the FeatureStoreImpl(Quadtree)
	 * @param maxLevel
	 *            represents the maximum depth of the tree
	 * @param maxElementsPerNode
	 *            represents the maximum of elements in one Node before there
	 *            will be a splitting
	 */
	public InnenjadeQuadtree(Envelope env, int maxLevel, int maxElementsPerNode) {
		this.root = new InnenjadeQuadtreeNode(env, this);
		this.maxLevel = maxLevel;
		this.maxElementsPerNode = maxElementsPerNode;
		this.store = new ArrayList<JadeNode>();
	}

	/**
	 * 
	 * @param node
	 *            will be inserted into roadNetworkQuadtree
	 */
	public void insert(JadeNode node) {

		if (store.contains(node) || !root.intersectRoadNetworkNode(node)) {
			return;
		}
		store.add(node);
		traverseTree(root, node);
	}

	public boolean delete(Node node) {
		// Bei Bedarf implementieren
		return false;
	}

	/**
	 * finds the nearest node to the given coordinate in the road network
	 * 
	 * @param vesselPosition
	 *            {@link Coordinate} of vessel in which proximity the next node
	 *            should be found
	 * @return nearest node to given coordinate
	 */

	public ArrayList<JadeNode> getAllElements() {

		List<JadeNode> elements = new ArrayList<JadeNode>();
		elements = deepSearch(root.getArea(), root, elements);
		return (ArrayList<JadeNode>) elements;
	}

	public int getMaxElementsPerNode() {
		return this.maxElementsPerNode;
	}

	public int getMaxLevel() {
		return this.maxLevel;
	}

	public JadeNode getRoadNetworkNode(int i) {
		return store.get(i);
	}

	private void traverseTree(InnenjadeQuadtreeNode node, JadeNode roadNetworkNode) {
		if (!node.intersectRoadNetworkNode(roadNetworkNode)) {
			return;
		}
		if (node.isChild()) {
			node.addRoadNetworkNode(roadNetworkNode, store.size() - 1);
			if (node.isFull()) {
				if (node.getLevel() < maxLevel)
					node.splitNode();
			}
		} else {
			for (InnenjadeQuadtreeNode child : node.getChildren()) {
				traverseTree(child, roadNetworkNode);
			}
		}
	}

	private List<JadeNode> deepSearch(Envelope env, InnenjadeQuadtreeNode node, List<JadeNode> list) {
		if (!node.intersectFeature(env)) {
			return list;
		}
		if (node.isChild()) {
			list.addAll(node.getAllElementsOfArea(env));
		} else {
			for (InnenjadeQuadtreeNode child : node.getChildren()) {
				deepSearch(env, child, list);
			}
		}
		return list;
	}

}
