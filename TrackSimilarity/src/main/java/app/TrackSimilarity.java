package app;

import java.io.IOException;

import controller.UIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class TrackSimilarity extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setController(new UIController());
			loader.setLocation(getClass().getResource("/ui/entwurf.fxml"));
			Parent root = loader.load();
			primaryStage.setTitle("Hello");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// launch(args);

		System.out.println("Starting...");

	}

}
