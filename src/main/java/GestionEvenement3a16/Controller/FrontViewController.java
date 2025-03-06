
package GestionEvenement3a16.Controller;


import GestionEvenement3a16.Controller.ReclamationController.AfficherReclamtion;
import GestionEvenement3a16.Entity.Evenement;
import GestionEvenement3a16.Entity.Moyen_De_Transport;
import GestionEvenement3a16.Entity.User;
import GestionEvenement3a16.Services.EvenementService;
import GestionEvenement3a16.Services.MoyenDeTransportService;
import GestionEvenement3a16.Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FrontViewController {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private AnchorPane achorfront;

    @FXML
    private AnchorPane achorfront1;

    @FXML
    private Button mes_tickets;

    @FXML
    private Label list_event;



    private UserService userService;


    @FXML
    public void reclamation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/AjouterReclamation.fxml"));
            Pane reclamtion = fxmlLoader.load(); // Load as Pane
            achorfront.getChildren().setAll(reclamtion);
            System.out.println("reclmation front view loaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading the evenement front view");
        }
    }

    private AfficherReclamtion afficherReclamtion;



    public void listeReclamtion() {
        afficherReclamtion = new AfficherReclamtion();
        afficherReclamtion.createCards();
        achorfront.getChildren().setAll(afficherReclamtion.getVbox());

    }


    @FXML
    public void  loadEvenementFront() {
        try {
            AnchorPane evenementFront = FXMLLoader.load(getClass().getResource("/GestionEvenement3a16/EvenementFront.fxml"));
            evenementFront.getStyleClass().add("center-content"); // Add the CSS class
            achorfront.getChildren().setAll(evenementFront);
            System.out.println("Evenement front view loaded successfully");
            mes_tickets.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading the evenement front view");
        }
    }




// Java
       // Java: FrontViewController.java
       @FXML
       public void loadUserTickets() {
           try {
               userService = new UserService();
               User user = userService.getById(2);
               int userId = user.getId();
               MoyenDeTransportService ticketService = new MoyenDeTransportService();
               List<Moyen_De_Transport> tickets = ticketService.getUserMoy(userId);
               achorfront.getChildren().clear();

               if (tickets.isEmpty()) {
                   Label messageLabel = new Label("Aucun tickets achete");
                   achorfront.getChildren().add(messageLabel);
               } else {
                   GridPane gridPane = new GridPane();
                   gridPane.setHgap(10);
                   gridPane.setVgap(10);
                   EvenementService evenementService = new EvenementService();
                   int column = 0;
                   int row = 0;

                   // Iterate over each reserved ticket and create an individual card
                   for (Moyen_De_Transport ticket : tickets) {
                       Evenement event = evenementService.getEventById(ticket.getEvenementId());
                       GridPane ticketCard = createReservedTicketCard(event, ticket);
                       gridPane.add(ticketCard, column, row);
                       ticketCard.getStyleClass().add("mes_tickets");
                       column++;
                       if (column > 3) {
                           column = 0;
                           row++;
                       }
                   }
                   achorfront.getChildren().add(gridPane);
               }
               System.out.println("Number of children in achorfront: " + achorfront.getChildren().size());
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

       // New method to create a reserved ticket card for each ticket
       public GridPane createReservedTicketCard(Evenement event, Moyen_De_Transport ticket) {
           GridPane reservedTicketCard = new GridPane();
           reservedTicketCard.getStyleClass().add("reserved-ticket-card");
           reservedTicketCard.setHgap(10);
           reservedTicketCard.setVgap(10);

           // Display event image
           File imageFile = new File(event.getImageEvenement());
           String imageUri = imageFile.toURI().toString();

           // Display event image using the converted URI.
           ImageView eventImage = new ImageView(new Image(imageUri));
           eventImage.setFitWidth(100);
           eventImage.setFitHeight(100);
           reservedTicketCard.add(eventImage, 0,2 , 1, 2);

           // Display event name
           Label eventNameLabel = new Label(event.getNom());
           eventNameLabel.getStyleClass().add("event-name");
           reservedTicketCard.add(eventNameLabel, 0, 0);

           // Optionally display ticket details (e.g., ticket type)
           Label ticketTypeLabel = new Label("Type: " + ticket.getType());
           reservedTicketCard.add(ticketTypeLabel, 0, 1);

           return reservedTicketCard;
       }






/*

@FXML
public void loadEvenementAndMostPopularEvents() {
    loadEvenementFront();

    // Fetch the most popular events
    TicketService ticketService = new TicketService();
    List<Evenement> popularEvents = null;
    try {
        popularEvents = ticketService.getMostPopularEvents();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    HBox hbox = new HBox(); // Use HBox instead of VBox
    hbox.setSpacing(10); // Set the space between the cards

    for (Evenement popularEvent : popularEvents) {
        GridPane popularEventCard = createPopularEventCard(popularEvent);
        hbox.getChildren().add(popularEventCard);
    }

    // Add the HBox to the ScrollPane
    most_popular_events.setContent(hbox);
}
*/

public GridPane createPopularEventCard(Evenement event) {
        GridPane popularEventCard = new GridPane();
        popularEventCard.getStyleClass().add("popular-event-card"); // Add the style class



        // Create an ImageView for the event image
        ImageView eventImage = new ImageView(new Image("file:/C:/Users/ayhem/Desktop/New folder/Pidev-3A34-G4/public/uploads/evenement/"+event.getImageEvenement()));
        eventImage.setFitWidth(100); // Adjust the width as needed
        eventImage.setFitHeight(100); // Adjust the height as needed

        // Create a Label for the event name
        Label eventName = new Label(event.getNom());
        eventName.getStyleClass().add("event-name"); // Add a CSS class for styling

        // Create a Label for the event date
        Label eventDate = new Label(event.getDateEvenement().toString());
        eventDate.getStyleClass().add("event-date"); // Add a CSS class for styling

        // Add the ImageView and Labels to the GridPane
        popularEventCard.add(eventImage, 0, 0, 1, 2); // Span 2 rows
        popularEventCard.add(eventName, 1, 0); // Top right
        popularEventCard.add(eventDate, 1, 1); // Bottom right

        return popularEventCard;
    }



}
