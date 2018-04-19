package app;

import java.sql.Timestamp;

import geometry.GeoMethods;
import input.CSVReader;
import model.Track;

public class TrackSimilarity {

	public static void main(String[] args) {
		System.out.println("Start up at " + new Timestamp(System.currentTimeMillis()));

		Track predictedTrack = CSVReader.readPredictedTrack("tracks/predictedTrack.csv");
		Track historicTrack = CSVReader.readHistoricTrack("tracks/historicTrack.csv");

		double overallDistance = GeoMethods.calculateOverallDistance(predictedTrack, historicTrack);

		System.out.println("Overall distance between tracks: " + overallDistance);

	}

}
