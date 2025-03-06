package GestionEvenement3a16.Controller.ReclamationController;

import GestionEvenement3a16.Entity.User;
import GestionEvenement3a16.Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;

import java.io.IOException;

        public class SampleController {
            public Button closeDataViewButton, updateDataButton;
            public AnchorPane DataView = new AnchorPane(), anchor = new AnchorPane(), EditProfileField, EditField = new AnchorPane();
            public ImageView avatarImageView;
            public TextField updateEmailText, updateFirstNameText, updateLastNameText, updatePhoneText, updateAdressText;
            public Button updateProfileButton = new Button();
            public Button logoutButton = new Button();

            private User currentUser;
            private User currentUser1;
            public Label updateProfileEmailError, updateProfileFirstNameError, updateProfileLastNameError, updateProfilePhoneError, updateProfileAdressError;
            public TextField updateProfileEmailText, updateProfileFirstNameText, updateProfileLastNameText, updateProfilePhoneText, updateProfileAdressText;
            @FXML
            private TilePane HBoxList;
            @FXML
            private ScrollPane userListScrollPane = new ScrollPane();
            @FXML
            private TextField searchField = new TextField();
            private UserService userService = new UserService();
            @FXML
            private Button exportButton = new Button();
            @FXML
            private Button gestionActivite;
            @FXML
            private Button gestionSalle;

            private static SampleController instance;

            static {
                instance = new SampleController();
            }

            private SampleController() {
                // Private constructor to prevent instantiation
            }

            public static SampleController getInstance() {
                return instance;
            }

            public void initialize() {
                EditField.setVisible(false);
                Circle clip = new Circle(avatarImageView.getFitWidth() / 2, avatarImageView.getFitHeight() / 2, Math.min(avatarImageView.getFitWidth(), avatarImageView.getFitHeight()) / 2);
                avatarImageView.setClip(clip);
            }

            @FXML
            public void loadRec() throws IOException {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/ReclamationView.fxml"));
                    Pane content = fxmlLoader.load();
                    searchField.setVisible(false);
                    userListScrollPane.setVisible(false);
                    exportButton.setVisible(false);
                    anchor.getChildren().setAll(content);
                    anchor.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @FXML
            public void loadRep() throws IOException {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/ReponseView.fxml"));
                    Pane content = fxmlLoader.load();
                    searchField.setVisible(false);
                    userListScrollPane.setVisible(false);
                    exportButton.setVisible(false);
                    anchor.getChildren().setAll(content);
                    anchor.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }