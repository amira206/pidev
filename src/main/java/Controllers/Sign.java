package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Role;
import models.User;
import services.UserService;
import java.io.IOException;

public class Sign {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField cpasswordS;

    @FXML
    private TextField emailS;

    @FXML
    private PasswordField passwordS;

    @FXML
    private ComboBox<Role> roleS;

    @FXML
    private TextField userNameS;

    @FXML
    void BackLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Créer la scène et la nouvelle fenêtre
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur", "Une erreur est survenue lors du chargement de l'écran de connexion.");
        }
    }

    @FXML
    void SignUp(ActionEvent event) {
        UserService sc = new UserService();
        User p = new User(userNameS.getText(), emailS.getText(), passwordS.getText(), roleS.getValue());

        try {
            sc.add(p);
            User loggedInUser = p;
            if (loggedInUser != null) {
                User.setCurrentUser(loggedInUser);
                System.out.println("Utilisateur ajouté : " + loggedInUser);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur Ajouté", "Bienvenue " + loggedInUser.getUserName() + "!");
                navigateToLogin(event);

            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout échoué", "Veuillez réessayer plus tard.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout", "Une erreur est survenue lors de l'ajout de l'utilisateur.");
        }
    }
    private void navigateToLogin(ActionEvent event) {
        try {
            // Charger l'écran de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Créer la scène et la nouvelle fenêtre
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur", "Une erreur est survenue lors du chargement de l'écran de connexion.");
        }
    }

    @FXML
    void initialize() {
        roleS.getItems().setAll(Role.values());
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
