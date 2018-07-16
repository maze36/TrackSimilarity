package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

import model.AISMessage;
import model.InnenjadeQuadtree;
import model.JadeNode;
import model.JadeNodeType;
import model.LengthDistribution;
import model.ShiptypeDistribution;
import model.Track;

public class CSVReader {

	private static String LINE = "";

	private final static String SPLITTER = ",";

	/**
	 * Reads in the CSV-file containing the nodes of the transportation network in
	 * the Jade. The nodes will be stored in a {@link InnenjadeQuadtree}.
	 * 
	 * @param csvLocationJadeNodes The location of the CSV file.
	 * @return The {@link InnenjadeQuadtree} containing the nodes.
	 */
	public static InnenjadeQuadtree readJadeNodes(String csvLocationJadeNodes) {

		InnenjadeQuadtree jadeQuadtree = new InnenjadeQuadtree(new Envelope(0.0, 100.0, 0.0, 100.0), 100, 100);

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocationJadeNodes));

			while ((LINE = reader.readLine()) != null) {
				String[] jadeNodeLine = LINE.split(SPLITTER);
				if (!jadeNodeLine[0].contains("lat") && jadeNodeLine.length > 1) {
					Coordinate position = new Coordinate(Double.valueOf(jadeNodeLine[0]),
							Double.valueOf(jadeNodeLine[1]));
					String name = jadeNodeLine[2].replaceAll("\"", "");
					JadeNodeType jadeNodeType;
					if (name.equals("\"Exit\"")) {
						jadeNodeType = JadeNodeType.EXIT;
					} else if (name.equals("\"Entry\"2")) {
						jadeNodeType = JadeNodeType.ENTRY;
					} else {
						jadeNodeType = JadeNodeType.DESTINATION;
					}

					if (name.contains("WP3")) {
						name = "WP3";
					}

					JadeNode jadeNode = new JadeNode(position, jadeNodeType, name);
					jadeQuadtree.insert(jadeNode);
					jadeNode.setDirectNeighbors(extractNeighbors(jadeNodeLine[3]));
				}
			}
			return jadeQuadtree;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Extracting the neighbors that are contained in a {@link String}. The
	 * {@link String} must have the format "neighbor1, neighbor2...neighborN".
	 * 
	 * @param neighbors The {@link String} in the described format above.
	 * @return An {@link ArrayList} containing the neighbors as {@link String}.
	 */
	private static ArrayList<String> extractNeighbors(String neighbors) {

		ArrayList<String> result = new ArrayList<String>();

		while (neighbors.contains(";")) {
			int end = neighbors.indexOf(";");
			String neighbor = neighbors.substring(0, end);
			result.add(neighbor.replaceAll("\"", ""));
			String res = neighbors.replace(neighbor + ";", "");
			neighbors = res;
		}

		result.add(neighbors.replaceAll("\"", ""));

		return result;
	}

	/**
	 * Reads in the CSV-files which contain the statistical distribution for every
	 * node in the extracted traffic pattern graph. The appropriate nodes will be
	 * augmented by these information.
	 * 
	 * @param csvLocationStatisticalDistribution The path to the directory of the
	 *                                           CSV-files.
	 * @param innenJadeQuadtree                  The {@link InnenjadeQuadtree}
	 *                                           containing the extracted traffic
	 *                                           pattern graph.
	 * @return
	 */
	public static InnenjadeQuadtree readStatisticalDistribution(String csvLocationStatisticalDistribution,
			InnenjadeQuadtree innenJadeQuadtree) {

		File[] listOfFiles = getFilesInDirectory(csvLocationStatisticalDistribution);

		for (int i = 0; i < listOfFiles.length; i++) {
			String location = listOfFiles[i].getPath();
			try {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(location));
				String name = extractNodeName(location);
				while ((LINE = reader.readLine()) != null) {
					String[] statLine = LINE.split(SPLITTER);
					if (!statLine[0].contains("length")) {
						Double length = Double.valueOf(statLine[0]);
						double lengthCount = Double.valueOf(statLine[1]);
						double lengthFrequency = Double.valueOf(statLine[2]);
						double shiptypeCount = 0;
						double shiptypeFrequency = 0;
						ShiptypeDistribution shiptypeDistribution = null;
						String shiptype = "";
						if (statLine.length > 3) {
							shiptype = statLine[3].toUpperCase().replaceAll("\"", "");
							shiptypeCount = Double.valueOf(statLine[4]);
							shiptypeFrequency = Double.valueOf(statLine[5]);
							shiptypeDistribution = new ShiptypeDistribution(shiptypeCount, shiptypeFrequency);

						}

						LengthDistribution lengthDistribution = new LengthDistribution(lengthCount, lengthFrequency);

						for (JadeNode node : innenJadeQuadtree.getAllElements()) {
							if (node.getName().equals(name)) {
								node.getLengthDistribution().put(length, lengthDistribution);
								if (statLine.length > 3) {
									node.getShiptypeDistribution().put(shiptype, shiptypeDistribution);
								}
								break;
							}
						}

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return innenJadeQuadtree;
	}

	private static String extractNodeName(String path) {

		int indexPoint = path.lastIndexOf(".");
		int indexLastSlash = path.lastIndexOf("\\");

		String result = path.substring(indexLastSlash + 1, indexPoint);

		return result;
	}

	/**
	 * Returns all files in the given directory.
	 * 
	 * @param directory
	 * @return
	 */
	private static File[] getFilesInDirectory(String directory) {
		File folder = new File(directory);
		File[] result = folder.listFiles();
		return result;
	}

	public static ArrayList<Track> readHistoricTracks(String path) {

		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Reading historic tracks from " + path);

		ArrayList<Track> tracks = new ArrayList<Track>();
		File[] listOfFiles = getFilesInDirectory(path);
		for (int i = 0; i < listOfFiles.length; i++) {
			String location = listOfFiles[i].getPath();

			Track track = new Track();
			try {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(location));
				ArrayList<AISMessage> messages = new ArrayList<AISMessage>();
				while ((LINE = reader.readLine()) != null) {
					String[] predTrackLine = LINE.split(SPLITTER);
					if (!predTrackLine[0].contains("mmsi")) {
						AISMessage message = new AISMessage();
						message.setMmsi(Integer.valueOf(predTrackLine[0]));
						message.setHeading(Double.valueOf(predTrackLine[1]));
						message.setSog(Double.valueOf(predTrackLine[2]));
						message.setCog(Double.valueOf(predTrackLine[3]));
						message.setTimestamp(predTrackLine[6]);
						message.setLat(Double.valueOf(predTrackLine[7]));
						message.setLon(Double.valueOf(predTrackLine[8]));
						message.setLength(Double.valueOf(predTrackLine[13]));
						message.setShiptype(predTrackLine[14]);
						messages.add(message);
					}
				}
				track.setMessage(messages);
				track.setTrackId(i);
				tracks.add(track);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				return null;
			}
		}
		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Finished reading tracks from " + path);
		return tracks;
	}

}
