package GestionEvenement3a16.Controller;

import GestionEvenement3a16.Entity.Evenement;
import GestionEvenement3a16.Entity.Moyen_De_Transport;
import GestionEvenement3a16.Entity.User;
import GestionEvenement3a16.Services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import kong.unirest.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class EvenementFront {

    @FXML
    private GridPane eventContainer; // This is the container where you will add the event cards


    @FXML
    private ScrollPane most_popular_events;
    private WeatherService weatherService;
private UserService userService;

public void initialize() {
    try {
        weatherService = new WeatherService();
      userService = new UserService();
        List<Evenement> events = fetchEventsFromDatabase();

        int column = 0;
        int row = 3; // Start from the fourth row to leave space for the titleLabel, the popular events, and the new titleLabel

        for (Evenement event : events) {
            GridPane eventCard = createEventCard(event);
            eventContainer.add(eventCard, column, row);

            column++;
            if (column > 2) {
                column = 0;
                row++;
            }
        }
        // Set the horizontal gap between the columns in the GridPane
        eventContainer.setHgap(10); // Adjust the value as needed


        // Create a label for the all events list
        Label allEventsLabel = new Label("ALL EVENT LIST");
        allEventsLabel.setFont(new Font("Arial", 30)); // Set the font size to 30

        // Add the all events label to the GridPane
        eventContainer.add(allEventsLabel, 0, 2, 3, 1);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public GridPane createEventCard(Evenement event) {
    GridPane eventCard = new GridPane();
    eventCard.getStyleClass().add("card"); // Add the style class

    eventCard.setVgap(10); // Set the amount of vertical space you want
    WeatherService weatherService = new WeatherService();
    JSONObject weatherData = weatherService.getWeatherByCity(event.getLieu());

    String weatherInfo = "Weather data could not be retrieved.";
    if (weatherData != null) {
        // Extract the weather information from the JSON object
        String weatherDescription = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperatureInKelvin = weatherData.getJSONObject("main").getDouble("temp");

        // Convert the temperature to Celsius
        double temperatureInCelsius = temperatureInKelvin - 273.15;

        weatherInfo = " " + weatherDescription + ",  " + String.format("%.2f", temperatureInCelsius) + "°C";
    }

    Label weatherLabel = new Label(weatherInfo);
    eventCard.add(weatherLabel, 0, 3); // Add the weather label to the top of the card


    // Create an ImageView and load the image from the file path
    ImageView eventImage = new ImageView();
    Image image = new Image("file:" + event.getImageEvenement());
    eventImage.setImage(image);
    eventImage.setFitWidth(190);  // Set the width of the ImageView
    eventImage.setFitHeight(220);
    eventImage.setPreserveRatio(true);  // Preserve the aspect ratio
    eventCard.add(eventImage, 0, 1);

    Label eventName = new Label(event.getNom());
    eventName.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
    eventCard.add(eventName, 0, 0);

    GridPane.setHalignment(eventName, javafx.geometry.HPos.CENTER);

    Label eventDate = new Label(event.getDateEvenement().toString());
    eventDate.setStyle("-fx-font-size: 14;");
    eventCard.add(eventDate, 0, 2);

    // Add an event handler to the card
    eventCard.setOnMouseClicked(e -> {
        // Create and display the detailed card
        GridPane detailedCard = createDetailedCard(event);
        eventContainer.getChildren().clear(); // Clear the event container
        eventContainer.add(detailedCard, 0, 0); // Add the detailed card to the event container
    });

    return eventCard;
}



    private GridPane createDetailedCard(Evenement event) {
        GridPane detailedCard = new GridPane();
        detailedCard.getStyleClass().add("detailed-card"); // Add the style class


        // Fetch the weather data for the city
        WeatherService weatherService = new WeatherService();
        JSONObject weatherData = weatherService.getWeatherByCity(event.getLieu());

        String weatherInfo = "Weather data could not be retrieved.";
        if (weatherData != null) {
            // Extract the weather information from the JSON object
            String weatherDescription = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            double temperatureInKelvin = weatherData.getJSONObject("main").getDouble("temp");

            // Convert the temperature to Celsius
            double temperatureInCelsius = temperatureInKelvin - 273.15;

            weatherInfo = "Weather: " + weatherDescription + ", " + String.format("%.2f", temperatureInCelsius) + "°C";
        }

        Label weatherLabel = new Label(weatherInfo);
        detailedCard.add(weatherLabel, 0, 5); // Add the weather label to the card



        // Set vertical gap
        detailedCard.setVgap(10); // Set the amount of vertical space you want

        // Create an ImageView and load the image from the file path
        ImageView eventImage = new ImageView();
        Image image = new Image("file:" + event.getImageEvenement());
        eventImage.setImage(image);
        eventImage.setFitWidth(300);  // Set the width of the ImageView
        eventImage.setFitHeight(450);
        eventImage.setPreserveRatio(true);  // Preserve the aspect ratio
        detailedCard.add(eventImage, 0, 1);

// Center the ImageView in its area of the GridPane
        GridPane.setHalignment(eventImage, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(eventImage, javafx.geometry.VPos.CENTER);

        Label eventName = new Label( event.getNom());
        eventName.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
        detailedCard.add(eventName, 0, 0);


        // Center the eventName in its area of the GridPane
        GridPane.setHalignment(eventName, javafx.geometry.HPos.CENTER);

        Label eventDate = new Label("Event Date: " + event.getDateEvenement().toString());
        //eventDate.setStyle("-fx-font-size: 14;");
        detailedCard.add(eventDate, 0, 2);

        Label eventLieu = new Label("Event Lieu: " + event.getLieu());
        //eventDate.setStyle("-fx-font-size: 14;");
        detailedCard.add(eventLieu, 0, 4);

Label eventDescription = new Label("Event Description: " + event.getDescription());
eventDescription.setWrapText(true); // Enable text wrapping
        eventDescription.setPrefWidth(500); // Set the preferred width to 500
// eventDescription.setStyle("-fx-font-size: 14;");
        detailedCard.add(eventDescription, 0, 7);


        Button getTicketButton = new Button("Get Transport");
        getTicketButton.setOnAction(e -> {
            // Create and display the ticket card
            GridPane ticketCard = null;
            try {
                ticketCard = createTicketCard(event);
                ticketCard.setPrefSize(600, 800);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            //eventContainer.getChildren().clear(); // Clear the event container
            eventContainer.add(ticketCard, 1, 0); // Add the ticket card to the event container
        });
        detailedCard.add(getTicketButton, 0, 8); // Add the button to the detailed card


// Create an ImageView
ImageView backIcon = new ImageView(new Image(getClass().getResource("/GestionEvenement3a16/images/back.png").toString()));// Set the size of the ImageView
backIcon.setFitHeight(15); // adjust the size as needed
backIcon.setFitWidth(15); // adjust the size as needed

// Create a back button with an icon
Button backButton = new Button();

// Set the graphic (icon) of the button
backButton.setGraphic(backIcon);

        // Add an event handler to the back button
        backButton.setOnAction(e -> {
            try {
                // Clear the event container
                eventContainer.getChildren().clear();

                // Repopulate the event container with the event cards
                List<Evenement> events = fetchEventsFromDatabase();
                int column = 0;
                int row = 0;
                for (Evenement ev : events) {
                    GridPane eventCard = createEventCard(ev);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    eventContainer.add(eventCard, column++, row);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        // Add the back button to the detailed card
        detailedCard.add(backButton, 0, 0);

        // Calculate the number of days left for the event
        LocalDate currentDate = LocalDate.now();
        LocalDate evenementDate = event.getDateEvenement().toLocalDate();
        long daysLeft = ChronoUnit.DAYS.between(currentDate, evenementDate);

        // Create a label to display the number of days left
        Label daysLeftLabel = new Label("Days left for the event: " + daysLeft + " days");
        detailedCard.add(daysLeftLabel, 0, 3); // Adjust the index as per your layout

        return detailedCard;
    }


public GridPane createTicketCard(Evenement event) throws SQLException {
        GridPane mainContainer = new GridPane();
        mainContainer.getStyleClass().add("ticket-container");
        mainContainer.setVgap(10);

        // Create ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(700);
        scrollPane.setPrefViewportWidth(380);
        scrollPane.getStyleClass().add("ticket-scroll-pane");

        // Create container for tickets
        GridPane ticketsContainer = new GridPane();
        ticketsContainer.setVgap(15);
        ticketsContainer.setPadding(new javafx.geometry.Insets(10));

        // Add title
        Label eventTitle = new Label("AVAILABLE TICKETS");
        eventTitle.getStyleClass().add("ticket-title");
        mainContainer.add(eventTitle, 0, 0);

        // Get all tickets for the event
        MoyenDeTransportService moyenDeTransportService = new MoyenDeTransportService();
        List<Moyen_De_Transport> tickets = moyenDeTransportService.getAllMoyenDeTransportsByEventId(event.getId());

        int row = 0;
        for (Moyen_De_Transport ticket : tickets) {
            if (ticket.getnbrePlaces() > 0) {
                // Create card container
                GridPane ticketCard = new GridPane();
                ticketCard.getStyleClass().add("ticket-card");
                ticketCard.setPadding(new javafx.geometry.Insets(15));
                ticketCard.setVgap(8);
                ticketCard.setPrefWidth(250);

                // Add separator line
                Label separator = new Label("------------------------");
                separator.getStyleClass().add("separator");

                // Create and style labels
                Label titleLabel = new Label("Transport DETAILS");
                titleLabel.getStyleClass().add("card-title");

                Label ticketType = new Label("Type: " + ticket.getType());
                ticketType.getStyleClass().add("ticket-info");

                Label ticketPrice = new Label("Price: " + ticket.getPrix() + " DT");
                ticketPrice.getStyleClass().add("ticket-info");

                Label ticketQuantity = new Label("Available: " + ticket.getnbrePlaces());
                ticketQuantity.getStyleClass().add("ticket-info");


                // Add elements to card
                ticketCard.add(titleLabel, 0, 0);
                ticketCard.add(separator, 0, 1);
                ticketCard.add(ticketType, 0, 2);
                ticketCard.add(ticketPrice, 0, 3);
                ticketCard.add(ticketQuantity, 0, 4);

                // Center align all elements in the card
                GridPane.setHalignment(titleLabel, javafx.geometry.HPos.CENTER);
                GridPane.setHalignment(separator, javafx.geometry.HPos.CENTER);
                GridPane.setHalignment(ticketType, javafx.geometry.HPos.CENTER);
                GridPane.setHalignment(ticketPrice, javafx.geometry.HPos.CENTER);
                GridPane.setHalignment(ticketQuantity, javafx.geometry.HPos.CENTER);

                ticketsContainer.add(ticketCard, 0, row++);
                Button participateButton = new Button("PARTICIPATE");
                participateButton.setOnAction(e -> {
                    // Decrease the ticket quantity by one

                    User user= null;
                    try {
                        user = userService.getById(2);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    int userId = user.getId();
                    int ticketId = ticket.getId();



try {

    moyenDeTransportService.registerUserMoy(ticketId, userId);
    ticket.setnbrePlaces(ticket.getnbrePlaces() - 1);
    // Confirmation alert for successful registration
    javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
    confirmationAlert.setTitle("Registration Successful");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Your registration was successfully completed.");
    confirmationAlert.showAndWait();
    TwilioService twilioService = new TwilioService();
    // Register the user for the ticket
    //String userId = getCurrentUrseId();
    // Assuming the user ID is "1" for testing purposes
    String to = "+21623111985";  // Replace with the phone number of the user
    String from = "+19893822487";  // Replace with your Twilio number
    String body = "Vous avez participer à  " + event.getNom() + "\n"+ "\n"
            + " Date: " + event.getDateEvenement().toString() + "\n"
            + "Moyen de transport Type: " + ticket.getType() + "\n"
            + "Moyen de transport Price: " + ticket.getPrix() + " DT" + "\n" ;
    twilioService.sendSms(to, from, body);
} catch (SQLException sqlException) {
    if (sqlException.getMessage().contains("Duplicate entry")) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Duplicate Entry Error");
        alert.setHeaderText(null);
        alert.setContentText("You have already registered for this ticket.");
        alert.showAndWait();
    }
}
                    // Extract the parameters from the Ticket object
                    int id = ticket.getId();
                    Integer evenement_id = ticket.getEvenementId();
                    Integer prix = ticket.getPrix();
                    String type = ticket.getType();
                    Integer nbre_ticket = ticket.getnbrePlaces();

                    // Update the ticket in the database
                    try {
                        moyenDeTransportService.updateMoyenDeTransport(id, evenement_id, prix, type,nbre_ticket);
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    // Refresh the ticket card
                    eventContainer.getChildren().remove(ticketCard);
                    try {
                        GridPane newTicketCard = createTicketCard(event);
                        eventContainer.add(newTicketCard, 1, 0);
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }





                });
                ticketCard.add(participateButton, 0, 5);

            }
        }

        if (row == 0) {
            Label noTicketLabel = new Label("No transport available.");
            noTicketLabel.getStyleClass().add("no-ticket-label");
            ticketsContainer.add(noTicketLabel, 0, 0);
        }

        scrollPane.setContent(ticketsContainer);
        mainContainer.add(scrollPane, 0, 1);

        return mainContainer;
    }

    public void decreaseTicketQuantity(Moyen_De_Transport moyenDeTransport) {
    MoyenDeTransportService ticketService = new MoyenDeTransportService();
    try {
        if (moyenDeTransport != null && moyenDeTransport.getnbrePlaces() > 0) {
            moyenDeTransport.setnbrePlaces(moyenDeTransport.getnbrePlaces() - 1);
            ticketService.updateMoyenDeTransport(moyenDeTransport.getId(), moyenDeTransport.getEvenementId(), moyenDeTransport.getPrix(), moyenDeTransport.getType(), moyenDeTransport.getnbrePlaces());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void showSuccessMessage() {
    // Check if eventContainer is not null before clearing it
    if (eventContainer != null) {
        // Clear the event container
        eventContainer.getChildren().clear();

        // Create a success message label
        Label successLabel = new Label("  YOUR PAYMENT WAS SUCCESSFULL!  ");
        successLabel.getStyleClass().add("success-label"); // Add a CSS class for styling

        // Add the success message label to the event container
        eventContainer.add(successLabel, 0, 0);
    } else {
        // Handle the case where eventContainer is null
        System.out.println("eventContainer is null. Please ensure it is properly initialized before calling showSuccessMessage.");
    }
}


private List<Evenement> fetchEventsFromDatabase() throws SQLException {
        EvenementService service = new EvenementService();
        return service.getAllEvents();
    }

}