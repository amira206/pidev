package GestionEvenement3a16;

import connectionSql.ConnectionSql;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Frontview.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("GestionEvenement3a16");
        stage.setScene(scene);
        stage.show();

        ConnectionSql connectionSql = new ConnectionSql();
        Connection connection = connectionSql.getConnection();
    }

    public static void main(String[] args) {
        launch();


    }

}