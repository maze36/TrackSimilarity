package gui;

public class MainFrame {

	public MainFrame() {

	}
	/*
	 * public void displayMap() {
	 * 
	 * final List<TileFactory> factories = new ArrayList<TileFactory>();
	 * 
	 * TileFactoryInfo osmInfo = new OSMTileFactoryInfo(); TileFactoryInfo
	 * veInfo = new
	 * VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
	 * 
	 * // factories.add(new EmptyTileFactory()); factories.add(new
	 * DefaultTileFactory(osmInfo)); factories.add(new
	 * DefaultTileFactory(veInfo));
	 * 
	 * // Setup JXMapViewer final JXMapViewer mapViewer = new JXMapViewer();
	 * mapViewer.setTileFactory(factories.get(0));
	 * 
	 * GeoPosition frankfurt = new GeoPosition(50.11, 8.68);
	 * 
	 * // Set the focus mapViewer.setZoom(7);
	 * mapViewer.setAddressLocation(frankfurt);
	 * 
	 * // Add interactions MouseInputListener mia = new
	 * PanMouseInputListener(mapViewer); mapViewer.addMouseListener(mia);
	 * mapViewer.addMouseMotionListener(mia);
	 * 
	 * mapViewer.addMouseWheelListener(new
	 * ZoomMouseWheelListenerCursor(mapViewer));
	 * 
	 * JPanel panel = new JPanel(); JLabel label = new JLabel(
	 * "Select a TileFactory ");
	 * 
	 * String[] tfLabels = new String[factories.size()]; for (int i = 0; i <
	 * factories.size(); i++) { tfLabels[i] =
	 * factories.get(i).getInfo().getName(); }
	 * 
	 * final JComboBox combo = new JComboBox(tfLabels);
	 * combo.addItemListener(new ItemListener() { public void
	 * itemStateChanged(ItemEvent e) { TileFactory factory =
	 * factories.get(combo.getSelectedIndex());
	 * mapViewer.setTileFactory(factory); } });
	 * 
	 * JLabel textField = new JLabel("Total Distance");
	 * 
	 * panel.setLayout(new GridLayout()); panel.add(textField);
	 * panel.add(label); panel.add(combo);
	 * 
	 * Track predictedTrack =
	 * CSVReader.readPredictedTrack("tracks/predictedTrack.csv"); Track
	 * historicTrack = CSVReader.readHistoricTrack("tracks/historicTrack.csv");
	 * 
	 * List<Painter<JXMapViewer>> predictedTrackPainter =
	 * routePainter(predictedTrack, historicTrack, Color.RED, Color.GREEN);
	 * CompoundPainter<JXMapViewer> predPainter = new
	 * CompoundPainter<JXMapViewer>(predictedTrackPainter);
	 * mapViewer.setOverlayPainter(predPainter);
	 * 
	 * final JLabel labelThreadCount = new JLabel("Threads: ");
	 * 
	 * // Display the viewer in a JFrame JFrame frame = new JFrame(
	 * "JXMapviewer2 Example 5"); frame.setLayout(new BorderLayout());
	 * frame.add(panel, BorderLayout.NORTH); frame.add(mapViewer);
	 * frame.add(labelThreadCount, BorderLayout.SOUTH); frame.setSize(800, 600);
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.setVisible(true);
	 * 
	 * Timer t = new Timer(500, new ActionListener() { public void
	 * actionPerformed(ActionEvent e) { Set<Thread> threads =
	 * Thread.getAllStackTraces().keySet(); labelThreadCount.setText("Threads: "
	 * + threads.size()); } });
	 * 
	 * t.start(); }
	 * 
	 * public List<Painter<JXMapViewer>> waypointPainer() {
	 * 
	 * ArrayList<JadeNode> nodes =
	 * CSVReader.readJadeNodes("jadeNodes/jadeNodes.csv"); Set<Waypoint>
	 * waypoints = new HashSet<Waypoint>();
	 * 
	 * for (JadeNode node : nodes) { waypoints.add(new
	 * DefaultWaypoint(node.getCoordinate().x, node.getCoordinate().y)); }
	 * 
	 * WaypointPainter<Waypoint> waypointPainter = new
	 * WaypointPainter<Waypoint>(); waypointPainter.setWaypoints(waypoints);
	 * List<Painter<JXMapViewer>> painters = new
	 * ArrayList<Painter<JXMapViewer>>(); painters.add(waypointPainter); return
	 * painters;
	 * 
	 * }
	 * 
	 * public void generateTracks() { Track historicTrack =
	 * CSVReader.readHistoricTrack("tracks/historicTrack.csv");
	 * 
	 * }
	 * 
	 * private List<GeoPosition> convertIntoGeoList(Track track) {
	 * List<GeoPosition> result = new ArrayList<GeoPosition>(); for (Coordinate
	 * coord : track.getCoordinatesList()) { result.add(new GeoPosition(coord.x,
	 * coord.y)); } return result; }
	 * 
	 * public List<Painter<JXMapViewer>> routePainter(Track historicTrack, Track
	 * predTrack, Color colorHist, Color colorPred) {
	 * 
	 * // Create a track from the geo-positions List<GeoPosition> trackPred =
	 * convertIntoGeoList(historicTrack); PredictedRoutePainter routePainterPred
	 * = new PredictedRoutePainter(trackPred, colorHist);
	 * 
	 * List<GeoPosition> histPred = convertIntoGeoList(historicTrack);
	 * HistoricRoutePainter routePainterHist = new
	 * HistoricRoutePainter(histPred, colorHist);
	 * 
	 * // Create a compound painter that uses both the route-painter and the //
	 * waypoint-painter List<Painter<JXMapViewer>> painters = new
	 * ArrayList<Painter<JXMapViewer>>(); painters.add(routePainterPred); return
	 * painters; }
	 */
}
