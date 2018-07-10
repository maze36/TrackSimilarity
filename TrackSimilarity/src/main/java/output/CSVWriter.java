package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import model.AISMessage;
import model.Track;

public class CSVWriter {

	private final static Logger logger = Logger.getLogger(CSVWriter.class.getName());

	public boolean writeHistoricTrack(Track track, int index) {
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
			return true;
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
			return false;
		}
	}

}
