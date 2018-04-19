package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

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
				if (!predTrackLine[0].contains("mmsi")) {
					Coordinate coord = new Coordinate(Double.valueOf(predTrackLine[7]),
							Double.valueOf(predTrackLine[8]));
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

}
