package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import com.vividsolutions.jts.geom.Coordinate;

import input.CSVReader;
import model.JadeNode;
import model.RoutePainter;
import model.Track;

public class MainFrame {

	public MainFrame() {

	}

	public void displayMap() {

		final List<TileFactory> factories = new ArrayList<TileFactory>();

		TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
		TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);

		// factories.add(new EmptyTileFactory());
		factories.add(new DefaultTileFactory(osmInfo));
		factories.add(new DefaultTileFactory(veInfo));

		// Setup JXMapViewer
		final JXMapViewer mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(factories.get(0));

		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

		// Set the focus
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(frankfurt);

		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);

		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

		JPanel panel = new JPanel();
		JLabel label = new JLabel("Select a TileFactory ");

		String[] tfLabels = new String[factories.size()];
		for (int i = 0; i < factories.size(); i++) {
			tfLabels[i] = factories.get(i).getInfo().getName();
		}

		final JComboBox combo = new JComboBox(tfLabels);
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				TileFactory factory = factories.get(combo.getSelectedIndex());
				mapViewer.setTileFactory(factory);
			}
		});

		JLabel textField = new JLabel("Total Distance");

		panel.setLayout(new GridLayout());
		panel.add(textField);
		panel.add(label);
		panel.add(combo);

		Track predictedTrack = CSVReader.readPredictedTrack("tracks/predictedTrack.csv");

		List<Painter<JXMapViewer>> predictedTrackPainter = routePainter(predictedTrack, Color.RED);

		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(predictedTrackPainter);
		mapViewer.setOverlayPainter(painter);

		final JLabel labelThreadCount = new JLabel("Threads: ");

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example 5");
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.add(mapViewer);
		frame.add(labelThreadCount, BorderLayout.SOUTH);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Timer t = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Set<Thread> threads = Thread.getAllStackTraces().keySet();
				labelThreadCount.setText("Threads: " + threads.size());
			}
		});

		t.start();
	}

	public List<Painter<JXMapViewer>> waypointPainer() {

		ArrayList<JadeNode> nodes = CSVReader.readJadeNodes("jadeNodes/jadeNodes.csv");
		Set<Waypoint> waypoints = new HashSet<Waypoint>();

		for (JadeNode node : nodes) {
			waypoints.add(new DefaultWaypoint(node.getPosition().x, node.getPosition().y));
		}

		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		waypointPainter.setWaypoints(waypoints);
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(waypointPainter);
		return painters;

	}

	public void generateTracks() {
		Track historicTrack = CSVReader.readHistoricTrack("tracks/historicTrack.csv");

	}

	private List<GeoPosition> convertIntoGeoList(Track track) {
		List<GeoPosition> result = new ArrayList<GeoPosition>();
		for (Coordinate coord : track.getCoordinatesList()) {
			result.add(new GeoPosition(coord.x, coord.y));
		}
		return result;
	}

	public List<Painter<JXMapViewer>> routePainter(Track track, Color color) {

		Set<Waypoint> waypoints = new HashSet<Waypoint>();

		for (Coordinate coord : track.getCoordinatesList()) {
			waypoints.add(new DefaultWaypoint(coord.x, coord.y));
		}

		// Create a track from the geo-positions
		List<GeoPosition> trackPred = convertIntoGeoList(track);
		RoutePainter routePainter = new RoutePainter(trackPred, color);

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		waypointPainter.setWaypoints(waypoints);

		// Create a compound painter that uses both the route-painter and the
		// waypoint-painter
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(routePainter);
		painters.add(waypointPainter);
		return painters;
	}

}
