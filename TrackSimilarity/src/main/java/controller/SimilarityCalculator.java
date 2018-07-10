package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.vividsolutions.jts.geom.LineString;

import geometry.GeometryMethods;
import model.AISMessage;
import model.Prediction;
import model.Track;
import output.CSVWriter;

public class SimilarityCalculator {

	private final static Logger logger = Logger.getLogger(SimilarityCalculator.class.getName());

	public static void calculatingSimilarities(ArrayList<Track> historicTracks) {
		CSVWriter csvWriter = new CSVWriter();

		HashMap<Integer, Double> overallDistances = new HashMap<Integer, Double>();
		GeometryMethods geo = new GeometryMethods();

		int counter = 0;

		while (counter <= 1000) {
			Track randomTrack = pickRandomTrack(historicTracks);
			int messageNumber = GeoUtil.generateRandomNumber(0, randomTrack.getMessage().size() - 1);
			AISMessage aisMessage = randomTrack.getMessage().get(messageNumber);
			logger.info("Picked message " + messageNumber);
			logger.info("Message content: " + aisMessage.toString());
			// csvWriter.writeHistoricTrack(randomTrack, messageNumber);

			Prediction prediction = PathPredictor.INSTANCE.getPossiblePath(aisMessage);
			// csvWriter.writePrediction(prediction, randomTrack.getTrackId());

			LineString lsPrediction = geo.createLineString(prediction);
			LineString lsTrack = geo.createLineString(randomTrack.getMessage(), messageNumber);

			double distance = geo.calculateOverallDistance(lsPrediction, lsTrack);

			overallDistances.put(randomTrack.getTrackId(), distance);

		}
		csvWriter.writeDistances(overallDistances);

	}

	private static Track pickRandomTrack(ArrayList<Track> historicTracks) {
		int trackNumber = GeoUtil.generateRandomNumber(0, historicTracks.size() - 1);

		logger.info("Picking random track " + trackNumber);

		return historicTracks.get(trackNumber);
	}

}
