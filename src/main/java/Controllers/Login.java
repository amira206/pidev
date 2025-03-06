package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.User;
import services.UserService;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    private UserService userService;

    public Login() {
        this.userService = new UserService();  // Initialisation du service UserService
    }

    @FXML
    void BackSign(ActionEvent event) {
        try {
            // Si l'utilisateur clique sur "Back", on le redirige vers l'écran d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sign.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur", "Une erreur est survenue lors du chargement de l'écran d'inscription.");
        }
    }

    @FXML
    void Login(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();

        try {
            // Récupérer tous les utilisateurs
            List<User> users = userService.list();

            User loggedInUser = null;

            // Vérifier si les informations d'identification correspondent à un utilisateur
            for (User u : users) {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    loggedInUser = u;
                    break;
                }
            }

            if (loggedInUser != null) {
                // Si l'utilisateur est trouvé et les informations sont correctes
                User.setCurrentUser(loggedInUser);  // Définir l'utilisateur actuel
                System.out.println("Utilisateur connecté : " + loggedInUser);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie", "Bienvenue " + loggedInUser.getUserName() + "!");

                // Si l'utilisateur est un administrateur, redirection vers Back.fxml
                if (loggedInUser.getRole() == models.Role.ADMIN) {
                    redirectToBack(event);
                }

            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Connexion échouée", "Nom d'utilisateur ou mot de passe incorrect.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Problème de connexion à la base de données", "Veuillez réessayer plus tard.");
        }
    }

    private void redirectToBack(ActionEvent event) {
        try {
            // Redirection vers l'interface admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur de redirection", "Une erreur est survenue lors de la redirection vers la page d'accueil.");
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void forgetPass(ActionEvent event) {
        // Vous pouvez implémenter cette fonction pour gérer l'oubli du mot de passe
    }

    @FXML
    void initialize() {
        // Initialisation des composants si nécessaire
    }
}
