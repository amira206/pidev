package Controllers;

import java.io.IOException;
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

public class ModifierUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailM;

    @FXML
    private PasswordField passwordM;

    @FXML
    private ComboBox<Role> roleM;

    @FXML
    private TextField userNameM;

    private int id;

    @FXML
    void ModifierU(ActionEvent event) {
        UserService userService = new UserService();
        String userName = userNameM.getText();
        String email = emailM.getText();
        String password = passwordM.getText();
        Role role = roleM.getSelectionModel().getSelectedItem();

        if (role != null) {
            // Créez un nouvel utilisateur avec les données modifiées.
            User user = new User(id, userName, email, password, role);  // Passez l'ID de l'utilisateur ici.
            try {
                userService.update(user);  // Appelle la méthode update pour modifier l'utilisateur dans la base de données.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("GOMOBILITE :: Success Message");
                alert.setHeaderText(null);
                alert.setContentText("User has been updated");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("An error occurred while updating the user. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a role.");
            alert.showAndWait();
        }
    }

    public void setTextFields(User user){
        id = user.getId();
        userNameM.setText(user.getUserName());
        emailM.setText(user.getEmail());
        passwordM.setText(user.getPassword());
        roleM.setValue(user.getRole());
    }

    @FXML
    void initialize() {
        try {
            roleM.getItems().setAll(Role.values());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
