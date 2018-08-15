package app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import controller.PathPredictor;
import controller.SimilarityCalculator;
import input.CSVReader;
import model.Track;

/**
 * 
 * @author maze
 *
 */
public class TrackSimilarity {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Starting....");
		PathPredictor.INSTANCE.init();

		ArrayList<Track> historicTracks = CSVReader.readHistoricTracks("tracks");
		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Predicting behavior for "
				+ historicTracks.size() + " vessels");
		SimilarityCalculator.calculatingSimilarities(historicTracks);

		long end = System.currentTimeMillis();

		long duration = TimeUnit.MILLISECONDS.toSeconds(end - start);

		System.out.println(new Timestamp(System.currentTimeMillis()) + "Finished. Duration: " + duration + " seconds");

	}

}
