package controller;

import java.util.ArrayList;

import model.AISMessage;
import model.Prediction;
import model.Track;
import output.CSVWriter;

public class SimilarityCalculator {

	public static void calculatingSimilarities(ArrayList<Track> historicTracks) {
		CSVWriter csvWriter = new CSVWriter();

		for (Track track : historicTracks) {
			int messageNumber = GeoUtil.generateRandomNumber(0, track.getMessage().size() - 1);
			AISMessage aisMessage = track.getMessage().get(messageNumber);

			Prediction prediction = PathPredictor.INSTANCE.getPossiblePath(aisMessage);

			if (prediction != null) {
				csvWriter.writeHistoricTrack(track, messageNumber);
				csvWriter.writePrediction(prediction, track.getTrackId());
			}
		}

	}
}
