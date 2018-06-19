package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

import model.JadeNode;
import model.Track;

public class CSVReader {

	private static String LINE = "";

	private final static String SPLITTER = ",";

	public static Track readPredictedTrack(String path) {

		System.out.println("Reading predicted track from " + path);

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(path));
			ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
			while ((LINE = reader.readLine()) != null) {
				String[] predTrackLine = LINE.split(SPLITTER);
				if (!predTrackLine[0].contains("lat")) {
					Coordinate coord = new Coordinate(Double.valueOf(predTrackLine[0]),
							Double.valueOf(predTrackLine[1]));
					coordinates.add(coord);
				}

			}
			System.out.println("Finished reading predicted track from " + path);
			return new Track(coordinates);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Track readHistoricTrack(String path) {
		System.out.println("Reading historic track from " + path);

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(path));
			ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
			while ((LINE = reader.readLine()) != null) {
				String[] predTrackLine = LINE.split(SPLITTER);
				if (!predTrackLine[0].contains("lat")) {
					Coordinate coord = new Coordinate(Double.valueOf(predTrackLine[0]),
							Double.valueOf(predTrackLine[1]));
					coordinates.add(coord);
				}
			}
			System.out.println("Finished reading historic track from " + path);
			return new Track(coordinates);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<JadeNode> readJadeNodes(String csvLocationJadeNodes) {

		ArrayList<JadeNode> jadeNodes = new ArrayList<JadeNode>();

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocationJadeNodes));

			while ((LINE = reader.readLine()) != null) {
				String[] jadeNodeLine = LINE.split(SPLITTER);
				if (!jadeNodeLine[0].contains("lat") && jadeNodeLine.length > 1) {
					Coordinate position = new Coordinate(Double.valueOf(jadeNodeLine[0]),
							Double.valueOf(jadeNodeLine[1]));
					String name = jadeNodeLine[2].replaceAll("\"", "");
					jadeNodes.add(new JadeNode(position, name));

				}
			}
			return jadeNodes;
		} catch (IOException e) {
			return null;
		}
	}

}
