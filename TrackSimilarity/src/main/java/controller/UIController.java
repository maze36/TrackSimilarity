package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

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

	@FXML
	private TableColumn<String, String> clmFeature;

	@FXML
	private TableColumn<Double, Double> clmValue;

	@FXML
	private void handleStartAction() {
		consoleTxtArea.appendText("Hallo");
	}

	public void initialize(URL location, ResourceBundle resources) {
		btnStart.setText("Say 'Hello World'");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				consoleTxtArea.appendText("piasfpiadfh\n");
			}
		});

	}

}
