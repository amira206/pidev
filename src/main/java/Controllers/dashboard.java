package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class dashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main dashboard FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard_front.fxml")); // Ensure the correct path to the FXML file
            AnchorPane root = loader.load();  // Load the FXML file

            // Set the scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Go Mobility Dashboard");  // Set the title of the window
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
