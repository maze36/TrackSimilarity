package controller;

import java.io.PrintStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import input.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import model.Track;
import model.resultObject.AverageDistanceResultObject;
import model.resultObject.ResultObject;
import output.CSVWriter;
import output.CustomOutputStream;

@SuppressWarnings("restriction")
public class UIController implements Initializable {

	@FXML
	private ProgressBar progressBar;

	@FXML
	private RadioButton radioTanker;

	@FXML
	private RadioButton radioCargo;

	@FXML
	private Button btnStart;

	@FXML
	private TextArea consoleTxtArea;

	@FXML
	private Button btnSave;

	@FXML
	private Label lblSave;

	@FXML
	private TableView<?> table;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn clmFeature;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn clmValue;

	private ObservableList<ResultObject> data;

	@FXML
	private void handleStartAction() {

		if (radioCargo.isSelected() || radioTanker.isSelected()) {
			System.out.println(new Timestamp(System.currentTimeMillis()) + ": Starting....");
			PathPredictor.INSTANCE.init();

			if (this.radioCargo.isSelected()) {
				ArrayList<Track> historicTracksCargo = CSVReader.readHistoricTracks("tracks/cargo");
				System.out.println(
						new Timestamp(System.currentTimeMillis()) + ": Predicting behavior for cargo vessels...");
				HashMap<Integer, Double> result = SimilarityCalculator.calculatingSimilarities(historicTracksCargo);

				this.btnSave.setDisable(false);

				printResult(result);
			}

			if (this.radioTanker.isSelected()) {
				ArrayList<Track> historicTracksTanker = CSVReader.readHistoricTracks("tracks/tanker");
				System.out.println(
						new Timestamp(System.currentTimeMillis()) + ": Predicting behavior for tanker vessels...");
				HashMap<Integer, Double> result = SimilarityCalculator.calculatingSimilarities(historicTracksTanker);
				this.btnSave.setDisable(false);

				printResult(result);

			}
		} else {
			System.out.println(new Timestamp(System.currentTimeMillis())
					+ ": No vessel is selected. Please select a vessel and press start again");
		}

	}

	private void printResult(HashMap<Integer, Double> result) {
		CSVWriter writer = new CSVWriter();
		writer.writeDistances(result);
	}

	private static Track pickRandomTrack(ArrayList<Track> historicTracks) {
		int trackNumber = GeoUtil.generateRandomNumber(0, historicTracks.size() - 1);

		Track track = historicTracks.get(trackNumber);

		System.out.println(new Timestamp(System.currentTimeMillis()) + ": Picking random track " + track.getTrackId());

		return track;
	}

	@SuppressWarnings("rawtypes")
	public void updateTable() {
		HashMap<String, Double> tableMap = new HashMap<>();

		tableMap.put("MinDistance", 20d);
		tableMap.put("MaxDistance", 20d);
		tableMap.put("AverageDistance", 21d);

		AverageDistanceResultObject avg = new AverageDistanceResultObject();

		avg.setPrediction("Prediction");
		avg.setValue(29d);

		data = FXCollections.observableArrayList(avg);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PrintStream printStream = new PrintStream(new CustomOutputStream(consoleTxtArea));
		System.setOut(printStream);
		System.setErr(printStream);

//		btnStart.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				consoleTxtArea.appendText("piasfpiadfh\n");
//			}
//		});

	}

}
