package GestionEvenement3a16.Services;

import GestionEvenement3a16.Entity.Evenement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connectionSql.ConnectionSql;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EvenementService {

    Connection connection;

    public EvenementService() {
        connection = ConnectionSql.getInstance().getConnection();
    }


    public void addEvent(Evenement event) throws SQLException {
        String query = "INSERT INTO evenement (nom, description, lieu, date_evenement, image_evenement) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getNom());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setString(3, event.getLieu());
        preparedStatement.setDate(4, event.getDateEvenement());
        preparedStatement.setString(5, event.getImageEvenement());
        preparedStatement.executeUpdate();
    }

public void deleteEvent(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteTransportStmt = null;
        PreparedStatement deleteEventStmt = null;

        try {
            connection = ConnectionSql.getInstance().getConnection();
            // Start transaction
            connection.setAutoCommit(false);

            // First, delete all means of transport related to the event
            String deleteTransportQuery = "DELETE FROM moyen_de_transport WHERE evenementId = ?";
            deleteTransportStmt = connection.prepareStatement(deleteTransportQuery);
            deleteTransportStmt.setInt(1, id);
            int transportDeleted = deleteTransportStmt.executeUpdate();
            System.out.println("Deleted " + transportDeleted + " means of transport");

            // Then, delete the event
            String deleteEventQuery = "DELETE FROM evenement WHERE id = ?";
            deleteEventStmt = connection.prepareStatement(deleteEventQuery);
            deleteEventStmt.setInt(1, id);
            int eventDeleted = deleteEventStmt.executeUpdate();
            System.out.println("Deleted event: " + (eventDeleted > 0));

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback in case of error
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            // Reset auto-commit to true
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            // Close statements
            if (deleteTransportStmt != null) {
                deleteTransportStmt.close();
            }
            if (deleteEventStmt != null) {
                deleteEventStmt.close();
            }
        }
    }
/*
    public void updateEvent(int id, String nom, String description, String lieu, Date date_evenement, String image_evenement) throws SQLException {
        String query = "UPDATE evenement SET nom = ?, description = ?, lieu = ?, date_evenement = ?, image_evenement = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, lieu);
        preparedStatement.setDate(4, date_evenement);
        preparedStatement.setString(5, image_evenement);
        preparedStatement.setInt(6, id);
        preparedStatement.executeUpdate();
    }
*/

    public void updateEvent(int id, String nom, String description, String lieu, Date date_evenement, String image_evenement) throws SQLException {
        String query = "UPDATE evenement SET nom = ?, description = ?, lieu = ?, date_evenement = ?, image_evenement = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, lieu);
        preparedStatement.setDate(4, date_evenement);
        preparedStatement.setString(5, image_evenement);
        preparedStatement.setInt(6, id);

        System.out.println("Executing update query: " + preparedStatement.toString());

        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("An existing event was updated successfully!");
        } else {
            System.out.println("No event was updated. Check if the event with the given ID exists.");
        }
    }

    public List<Evenement> getAllEvents() throws SQLException {
        List<Evenement> events = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Evenement event = new Evenement();
            event.setId(resultSet.getInt("id"));
            event.setNom(resultSet.getString("nom"));
            event.setDescription(resultSet.getString("description"));
            event.setLieu(resultSet.getString("lieu"));
            event.setDateEvenement(resultSet.getDate("date_evenement"));
            event.setImageEvenement(resultSet.getString("image_evenement"));
            events.add(event);
        }

        // Print the events for debugging
        System.out.println(events);

        return events;
    }


    public List<String> getAllEventNames() throws SQLException {
        List<String> eventNames = new ArrayList<>();
        String query = "SELECT nom FROM evenement";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            eventNames.add(resultSet.getString("nom"));
        }
        return eventNames;
    }

    public Evenement getEventByName(String eventName) throws SQLException {
        String query = "SELECT * FROM evenement WHERE nom = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, eventName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Evenement event = new Evenement();
            event.setId(resultSet.getInt("id"));
            event.setNom(resultSet.getString("nom"));
            event.setDescription(resultSet.getString("description"));
            event.setLieu(resultSet.getString("lieu"));
            event.setDateEvenement(resultSet.getDate("date_evenement"));
            event.setImageEvenement(resultSet.getString("image_evenement"));
            return event;
        }
        return null;
    }

    public String getEventNameById(int id) throws SQLException {
        String query = "SELECT nom FROM evenement WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("nom");
        }
        return null;
    }


    public Evenement getEventById(int id) throws SQLException {
        String query = "SELECT * FROM evenement WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Evenement event = new Evenement();
            event.setId(resultSet.getInt("id"));
            event.setNom(resultSet.getString("nom"));
            event.setDescription(resultSet.getString("description"));
            event.setLieu(resultSet.getString("lieu"));
            event.setDateEvenement(resultSet.getDate("date_evenement"));
            event.setImageEvenement(resultSet.getString("image_evenement"));
            return event;
        }
        return null;
    }

    public boolean eventExists(String eventName) throws SQLException {
        String query = "SELECT COUNT(*) FROM evenement WHERE nom = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, eventName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
        return false;
    }


    public String convertEventsToJson(List<Evenement> events) {
        List<Map<String, Object>> formattedEvents = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Adjusted date format
        for (Evenement event : events) {
            Map<String, Object> formattedEvent = new HashMap<>();
            formattedEvent.put("title", event.getNom());
            formattedEvent.put("start", sdf.format(event.getDateEvenement()));
            // Add more fields as needed
            formattedEvents.add(formattedEvent);
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(formattedEvents);
    }


    public List<Evenement> searchEvents(String searchTerm) throws SQLException {
        List<Evenement> events = new ArrayList<>();
        String query = "SELECT * FROM evenement WHERE nom LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + searchTerm + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Evenement event = new Evenement();
            event.setId(resultSet.getInt("id"));
            event.setNom(resultSet.getString("nom"));
            event.setDescription(resultSet.getString("description"));
            event.setLieu(resultSet.getString("lieu"));
            event.setDateEvenement(resultSet.getDate("date_evenement"));
            event.setImageEvenement(resultSet.getString("image_evenement"));
            events.add(event);
        }
        return events;
    }



}



