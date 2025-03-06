package GestionEvenement3a16.Services;

import GestionEvenement3a16.Entity.Moyen_De_Transport;
import connectionSql.ConnectionSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoyenDeTransportService {

    Connection connection;

    public MoyenDeTransportService() {
        connection = ConnectionSql.getInstance().getConnection();
    }

    public void addMoyenDeTransport(Moyen_De_Transport moyenDeTransport) throws SQLException {
        String query = "INSERT INTO moyen_de_transport ( evenementId,prix, type, nbrePlaces) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, moyenDeTransport.getEvenementId());
        preparedStatement.setInt(2, moyenDeTransport.getPrix());
        preparedStatement.setString(3, moyenDeTransport.getType());
        preparedStatement.setInt(4, moyenDeTransport.getnbrePlaces());
        preparedStatement.executeUpdate();
    }

    public void deleteMoyenDeTransport(int id) throws SQLException {
        String query = "DELETE FROM moyen_de_transport WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateMoyenDeTransport(int id, Integer evenement_id, Integer prix, String type, Integer nbre_places) throws SQLException {
        String query = "UPDATE moyen_de_transport SET evenementId = ?, prix = ?, type = ?, nbrePlaces = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, evenement_id);
        preparedStatement.setInt(2, prix);
        preparedStatement.setString(3, type);
        preparedStatement.setInt(4, nbre_places);
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
    }

    public List<Moyen_De_Transport> getAllMoyenDeTransports() throws SQLException {
        List<Moyen_De_Transport> moyenDeTransports = new ArrayList<>();
        String query = "SELECT * FROM moyen_de_transport";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
            moyenDeTransport.setId(resultSet.getInt("id"));
            moyenDeTransport.setEvenementId(resultSet.getInt("evenementId"));
            moyenDeTransport.setPrix(resultSet.getInt("prix"));
            moyenDeTransport.setType(resultSet.getString("type"));
            moyenDeTransport.setnbrePlaces(resultSet.getInt("nbrePlaces"));
            moyenDeTransports.add(moyenDeTransport);
        }
        return moyenDeTransports;
    }


    public Moyen_De_Transport getMoyenDeTransportByEventId(int eventId) throws SQLException {
        String query = "SELECT * FROM moyen_de_transport WHERE evenementId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, eventId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
            moyenDeTransport.setId(resultSet.getInt("id"));
            moyenDeTransport.setEvenementId(resultSet.getInt("evenementId"));
            moyenDeTransport.setPrix(resultSet.getInt("prix"));
            moyenDeTransport.setType(resultSet.getString("type"));
            moyenDeTransport.setnbrePlaces(resultSet.getInt("nbreplaces"));
            return moyenDeTransport;
        }
        return null;
    }

public Moyen_De_Transport testMoyenDeTransportByEventId(int eventId, int currentMoyenDeTransportId) throws SQLException {
    String query = "SELECT * FROM moyen_de_transport WHERE evenementId = ? AND id != ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, eventId);
    preparedStatement.setInt(2, currentMoyenDeTransportId);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
        Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
        moyenDeTransport.setId(resultSet.getInt("id"));
        moyenDeTransport.setEvenementId(resultSet.getInt("evenementId"));
        moyenDeTransport.setPrix(resultSet.getInt("prix"));
        moyenDeTransport.setType(resultSet.getString("type"));
        moyenDeTransport.setnbrePlaces(resultSet.getInt("nbreplaces"));
        return moyenDeTransport;
    }
    return null;
}

    public List<Moyen_De_Transport> getAllMoyenDeTransportsByEventId(int eventId) throws SQLException {
        List<Moyen_De_Transport> moyenDeTransports = new ArrayList<>();
        String query = "SELECT * FROM moyen_de_transport WHERE evenementId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, eventId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
                    moyenDeTransport.setId(resultSet.getInt("id"));
                    moyenDeTransport.setEvenementId(resultSet.getInt("evenementId"));
                    moyenDeTransport.setPrix(resultSet.getInt("prix"));
                    moyenDeTransport.setType(resultSet.getString("type"));
                    moyenDeTransport.setnbrePlaces(resultSet.getInt("nbrePlaces"));

                    moyenDeTransports.add(moyenDeTransport);
                }
            }
        }

        return moyenDeTransports;
    }
public boolean ticketExistsForEvent(int eventId) throws SQLException {
    String query = "SELECT * FROM ticket WHERE evenement_id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, eventId);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet.next();
}



public void registerUserMoy(int Moy_Id, int user_Id) throws SQLException {
    String query = "INSERT INTO moyen_de_transport_user (moyen_de_transport_id , user_id) VALUES (?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, Moy_Id);
    preparedStatement.setInt(2, user_Id);
    preparedStatement.executeUpdate();
}


public List<Moyen_De_Transport> getUserMoy(int userId) throws SQLException {
    List<Moyen_De_Transport> MoyDeTransports = new ArrayList<>();
    String query = "SELECT * FROM moyen_de_transport_user  JOIN moyen_de_transport ON moyen_de_transport_user.moyen_de_transport_id = moyen_de_transport.id WHERE user_id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, userId);
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
        Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
        moyenDeTransport.setId(resultSet.getInt("id"));
        moyenDeTransport.setEvenementId(resultSet.getInt("evenementId"));
        moyenDeTransport.setPrix(resultSet.getInt("prix"));
        moyenDeTransport.setType(resultSet.getString("type"));
        moyenDeTransport.setnbrePlaces(resultSet.getInt("nbrePlaces"));
        MoyDeTransports.add(moyenDeTransport);
    }
    return MoyDeTransports;
}

/*
public List<Evenement> getMostPopularEvents() throws SQLException {
    // Create a Statement object
    Statement statement = connection.createStatement();

    // Create an EvenementService object
    EvenementService evenementService = new EvenementService();

    // Query to get the 4 events with the most unique users
    String query = "SELECT ticket.evenement_id, COUNT(DISTINCT ticket_user.user_id) as user_count " +
                   "FROM ticket_user " +
                   "JOIN ticket ON ticket_user.ticket_id = ticket.id " +
                   "GROUP BY ticket.evenement_id " +
                   "ORDER BY user_count DESC LIMIT 4";

    // Execute the query and get the results
    ResultSet rs = statement.executeQuery(query);

    // Create a list to store the most popular events
    List<Evenement> mostPopularEvents = new ArrayList<>();

    // For each result, get the event and add it to the list
    while (rs.next()) {
        int eventId = rs.getInt("evenement_id");
        Evenement event = evenementService.getEventById(eventId);
        mostPopularEvents.add(event);
    }

    return mostPopularEvents;
}

public int getTotalPurchasedTicketsByEventId(int eventId) throws SQLException {
    String query = "SELECT COUNT(DISTINCT user_id) as total_tickets FROM ticket_user JOIN ticket ON ticket_user.ticket_id = ticket.id WHERE ticket.evenement_id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, eventId);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
        return resultSet.getInt("total_tickets");
    }
    return 0;
}

    public List<Moyen_De_Transport> searchTicketsByEventName(String eventName) throws SQLException {
        List<Moyen_De_Transport> tickets = new ArrayList<>();
        String query = "SELECT ticket.* FROM ticket JOIN evenement ON ticket.evenement_id = evenement.id WHERE evenement.nom LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + eventName + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Moyen_De_Transport ticket = new Moyen_De_Transport();
            ticket.setId(resultSet.getInt("id"));
            ticket.setEvenementId(resultSet.getInt("evenement_id"));
            ticket.setPrix(resultSet.getInt("prix"));
            ticket.setType(resultSet.getString("type"));
            ticket.setnbrePlaces(resultSet.getInt("nbre_places"));
            tickets.add(ticket);
        }
        return tickets;
    }
*/
}
