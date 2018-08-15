package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import model.AISMessage;
import model.Prediction;
import model.Track;

/**
 * 
 * @author maze
 *
 */
public class CSVWriter {

	/**
	 * Writes a given {@link Track} to .csv file.
	 * 
	 * @param track
	 * @param index
	 */
	public void writeHistoricTrack(Track track, int index) {
		System.out.println("Writing track " + index);

		try {
			PrintWriter pw = new PrintWriter(new File("result/tracks/track" + track.getTrackId() + ".csv"));

			StringBuilder sb = new StringBuilder();
			sb.append("mmsi");
			sb.append(',');
			sb.append("heading");
			sb.append(',');
			sb.append("sog");
			sb.append(',');
			sb.append("cog");
			sb.append(',');
			sb.append("lat");
			sb.append(',');
			sb.append("lon");
			sb.append(',');
			sb.append("timestamp");
			sb.append(',');
			sb.append("length");
			sb.append(',');
			sb.append("shiptype");
			sb.append('\n');

			ArrayList<AISMessage> message = track.getMessage();

			for (int i = index; i < message.size(); i++) {
				sb.append(message.get(i).getMmsi());
				sb.append(',');
				sb.append(message.get(i).getHeading());
				sb.append(',');
				sb.append(message.get(i).getSog());
				sb.append(',');
				sb.append(message.get(i).getCog());
				sb.append(',');
				sb.append(message.get(i).getLat());
				sb.append(',');
				sb.append(message.get(i).getLon());
				sb.append(',');
				sb.append(message.get(i).getTimestamp());
				sb.append(',');
				sb.append(message.get(i).getLength());
				sb.append(',');
				sb.append(message.get(i).getShiptype().getStart());
				sb.append('\n');
			}

			pw.write(sb.toString());
			pw.close();
			System.out.println("Finished writing ");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Writes a given {@link Prediction} to a .csv file.
	 * 
	 * @param prediction
	 * @param trackId
	 */
	public void writePrediction(Prediction prediction, int trackId) {
		System.out.println("Writing predicted track to csv...");

		try {
			PrintWriter pw = new PrintWriter(new File("result/tracks/predictedTrack" + trackId + ".csv"));

			StringBuilder sb = new StringBuilder();
			sb.append("lat");
			sb.append(',');
			sb.append("lon");
			sb.append('\n');

			for (Coordinate coordinate : prediction.getPrediction()) {
				sb.append(coordinate.x);
				sb.append(',');
				sb.append(coordinate.y);
				sb.append('\n');
			}

			pw.write(sb.toString());
			pw.close();
			System.out.println("Finished writing ");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * Writes a given {@link HashMap} containing distances to a .csv file.
	 * 
	 * @param overallDistances
	 */
	public void writeDistances(HashMap<Integer, Double> overallDistances) {

		System.out.println("Writing distances...");

		try {
			PrintWriter pw = new PrintWriter(new File("result/distances.csv"));

			StringBuilder sb = new StringBuilder();
			sb.append("trackId");
			sb.append(',');
			sb.append("distance");
			sb.append('\n');

			Iterator<?> it = overallDistances.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<?, ?> pair = (Map.Entry<?, ?>) it.next();
				sb.append(pair.getKey());
				sb.append('+');
				sb.append(pair.getValue());
				sb.append('\n');
			}

			pw.write(sb.toString());
			pw.close();
			System.out.println("Finished writing ");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

}
