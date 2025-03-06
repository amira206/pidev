package GestionEvenement3a16.Controller;

import GestionEvenement3a16.Entity.Evenement;
import GestionEvenement3a16.Services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class EvenementCard {

    @FXML
    private Label Date;

    @FXML
    private Pane PaneEvenement;

    @FXML
    private Label name;

    @FXML
    private ImageView imageEvnement;

    @FXML
    private Button supprimer;

    private Evenement event;
    private EvenementService evenementService = new EvenementService();

    public void setData(Evenement event) {
        this.event = event;
        Date.setText(String.valueOf(event.getDateEvenement())); // Assuming Date is a Label
        name.setText(event.getNom()); // Assuming FullName is a Label

        // Set the image
        String imageUrl = event.getImageEvenement();
        if (imageUrl != null && !imageUrl.isEmpty()) {

            File file = new File(imageUrl);
            String imageUri = file.toURI().toString();
            Image image = new Image(imageUri);
            imageEvnement.setImage(image);
        } else {
            // Handle the case when imageUrl is null or empty
            // For example, set a default image
        }


    }

    public Evenement getEvent() {
    return this.event;
}

}