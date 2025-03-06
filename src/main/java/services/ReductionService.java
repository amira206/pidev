package services;

import models.Reduction;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReductionService {

    private Connection connection;

    // Constructeur
    public ReductionService() {
        connection = MyDabase.getInstance().getConnection();
    }

    // Méthode pour ajouter une réduction
    public boolean ajouterReduction(int userId, double reductionAmount, String validUntil, String status) {
        String query = "INSERT INTO Reductions (user_id, reduction_percentage, valid_until, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDouble(2, reductionAmount);
            statement.setString(3, validUntil);
            statement.setString(4, status);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0; // Si la réduction a été ajoutée avec succès
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Méthode pour récupérer toutes les réductions
    public List<Reduction> getAllReductions() {
        List<Reduction> reductions = new ArrayList<>();
        String sql = "SELECT * FROM Reductions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int reductionId = rs.getInt("reduction_id");
                int userId = rs.getInt("user_id");
                double reductionPercentage = rs.getDouble("reduction_percentage");
                Date validUntil = rs.getDate("valid_until");
                String status = rs.getString("status");

                reductions.add(new Reduction(reductionId, userId, reductionPercentage, validUntil, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reductions;
    }

    // Méthode pour récupérer les réductions d'un utilisateur spécifique
    public Reduction getReductionForUser(int userId) {
        String sql = "SELECT * FROM Reductions WHERE user_id = ? AND status = 'active'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int reductionId = rs.getInt("reduction_id");
                double reductionPercentage = rs.getDouble("reduction_percentage");
                Date validUntil = rs.getDate("valid_until");
                String status = rs.getString("status");

                return new Reduction(reductionId, userId, reductionPercentage, validUntil, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour mettre à jour le statut d'une réduction (par exemple, le désactiver)
    public boolean modifierReduction(int reductionId, int userId, double reductionPercentage, String validUntil, String status) {
        String query = "UPDATE Reductions SET user_id = ?, reduction_percentage = ?, valid_until = ?, status = ? WHERE reduction_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Paramétrage des valeurs
            stmt.setInt(1, userId);
            stmt.setDouble(2, reductionPercentage);
            stmt.setString(3, validUntil);
            stmt.setString(4, status);
            stmt.setInt(5, reductionId);

            // Exécution de la requête de mise à jour
            int rowsUpdated = stmt.executeUpdate();

            // Retourne true si la mise à jour a réussi (lorsque des lignes ont été modifiées)
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retourne false en cas d'exception
        }
    }


    // Méthode pour supprimer une réduction
    public boolean supprimerReduction(int userId) {
        String query = "DELETE FROM Reductions WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour obtenir toutes les réductions actives
    public List<Reduction> list() {
        List<Reduction> reductions = new ArrayList<>();
        String sql = "SELECT * FROM Reductions WHERE status = 'active'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int reductionId = rs.getInt("reduction_id");
                int userId = rs.getInt("user_id");
                double reductionPercentage = rs.getDouble("reduction_percentage");
                Date validUntil = rs.getDate("valid_until");
                String status = rs.getString("status");

                reductions.add(new Reduction(reductionId, userId, reductionPercentage, validUntil, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reductions;
    }
}
