package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import com.vividsolutions.jts.geom.Coordinate;

import model.AISMessage;
import model.Prediction;
import model.Track;

public class CSVWriter {

	private final static Logger logger = Logger.getLogger(CSVWriter.class.getName());

	public void writeHistoricTrack(Track track, int index) {
		logger.info("Writing remaining track to csv...");

		try {
			PrintWriter pw = new PrintWriter(new File("track" + track.getTrackId() + ".csv"));

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
			logger.info("Finished writing ");
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
		}
	}

	public void writePrediction(Prediction prediction, int trackId) {
		logger.info("Writing predicted track to csv...");

		try {
			PrintWriter pw = new PrintWriter(new File("predictedTrack" + trackId + ".csv"));

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
			logger.info("Finished writing ");
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
		}

	}

	public void writeDistances(HashMap<Integer, Double> overallDistances) {

		logger.info("Writing distances...");

		try {
			PrintWriter pw = new PrintWriter(new File("distances.csv"));

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
			logger.info("Finished writing ");
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
		}
	}

}
