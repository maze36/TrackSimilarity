package controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import model.AISMessage;
import model.Track;
import output.CSVWriter;

public class SimilarityCalculator {

	private final static Logger logger = Logger.getLogger(SimilarityCalculator.class.getName());

	public static void calculatingSimilarities(ArrayList<Track> historicTracks) {
		CSVWriter csvWriter = new CSVWriter();

		int counter = 0;

		while (counter <= 1000) {
			Track randomTrack = pickRandomTrack(historicTracks);
			int messageNumber = GeoUtil.generateRandomNumber(0, randomTrack.getMessage().size() - 1);
			AISMessage aisMessage = randomTrack.getMessage().get(messageNumber);
			logger.info("Picked message " + messageNumber);
			logger.info("Message content: " + aisMessage.toString());
			csvWriter.writeHistoricTrack(randomTrack, messageNumber);
		}

	}

	private static Track pickRandomTrack(ArrayList<Track> historicTracks) {
		int trackNumber = GeoUtil.generateRandomNumber(0, historicTracks.size() - 1);

		logger.info("Picking random track " + trackNumber);

		return historicTracks.get(trackNumber);
	}

}
