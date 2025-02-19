package org.example.pitest.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.pitest.Interfaces.IService;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParcoursService implements IService<Parcours> {
    private Connection connexion;
    private static ParcoursService instance;

    public ParcoursService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public static ParcoursService getInstance() {
        if (instance == null) {
            instance = new ParcoursService();
        }
        return instance;
    }

    @Override

    public void add(Parcours parcours) {
        String query = "INSERT INTO parcours (name, pickup, destination, latDest, lngDest, latPickup, lngPickup, distance, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            // Set parameters for the query
            execute_stmnt(parcours, pst);

            // Add debug log to check parameters before execution
            System.out.println("Inserting parcours: " + parcours);

            // Execute the update (this actually runs the query)
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Parcours inserted successfully");
            } else {
                System.out.println("Failed to insert parcours");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting parcours: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int id) {
        String query = "DELETE FROM parcours WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Parcours parcours, int id) {
        String query = "UPDATE parcours SET name = ?, pickup = ?, destination = ?, latDest = ?, lngDest = ?, latPickup = ?, lngPickup = ?, distance = ?, time = ? WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            execute_stmnt(parcours, pst);
            pst.setInt(10, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void execute_stmnt(Parcours parcours, PreparedStatement pst) throws SQLException {
        pst.setString(1, parcours.getName());
        pst.setString(2, parcours.getPickup());
        pst.setString(3, parcours.getDestination());
        pst.setDouble(4, parcours.getLatDest());
        pst.setDouble(5, parcours.getLngDest());
        pst.setDouble(6, parcours.getLatPickup());
        pst.setDouble(7, parcours.getLngPickup());
        pst.setInt(8, parcours.getDistance());
        pst.setInt(9, parcours.getTime());
    }

    @Override
    public Parcours find(int id) {
        String query = "SELECT * FROM parcours WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Parcours(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("pickup"),
                        rs.getString("destination"),
                        rs.getDouble("latDest"),
                        rs.getDouble("lngDest"),
                        rs.getDouble("latPickup"),
                        rs.getDouble("lngPickup"),
                        rs.getInt("distance"),
                        rs.getInt("time")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Parcours> findAll() {
        List<Parcours> parcoursList = new ArrayList<>();
        String query = "SELECT * FROM parcours";
        try (Statement st = connexion.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                parcoursList.add(new Parcours(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("pickup"),
                        rs.getString("destination"),
                        rs.getDouble("latDest"),
                        rs.getDouble("lngDest"),
                        rs.getDouble("latPickup"),
                        rs.getDouble("lngPickup"),
                        rs.getInt("distance"),
                        rs.getInt("time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parcoursList;
    }


}
