package app;

import java.util.ArrayList;
import java.util.logging.Logger;

import controller.PathPredictor;
import controller.SimilarityCalculator;
import input.CSVReader;
import model.Track;

public class TrackSimilarity {

	private static final Logger logger = Logger.getLogger(TrackSimilarity.class.getName());

	public static void main(String[] args) {

		logger.info("Starting app...");

		ArrayList<Track> historicTracks = CSVReader.readHistoricTracks("tracks");

		PathPredictor.INSTANCE.init();

		SimilarityCalculator.calculatingSimilarities(historicTracks);

	}

}
