package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Frame extends Application {

	private String consoleText = "Starting App...";

	private String resultText = "";

	public Frame() {
	}

	public void initFrame() {
		JFrame frame = new JFrame("Track Similarity");
		frame.setSize(1005, 713);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridheight = 2;
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 13;
		frame.getContentPane().add(progressBar, gbc_progressBar);

		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_textArea.gridheight = 3;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 16;
		frame.getContentPane().add(textArea, gbc_textArea);
		frame.setVisible(true);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ui/entwurf.fxml"));
			Parent root = loader.load();
			primaryStage.setTitle("Hello");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
//	public void updateProgessBar(int value) {
//		this.progressBar.setValue(value);
//	}
//
//	public void updateConsoleTextArea(String textToAppend) {
//		this.consoleText = this.consoleText + "\n" + textToAppend;
//		this.consoleTextArea.setText(consoleText);
//	}
//
//	public void updateResultTextArea(String textToAppend) {
//		this.resultText = this.resultText + "\n" + textToAppend;
//		this.resultTextArea.setText(resultText);
//	}
//
//	public TextArea getConsoleTextAre() {
//		return this.consoleTextArea;
//	}
}
