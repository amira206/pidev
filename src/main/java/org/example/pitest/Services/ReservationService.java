package org.example.pitest.Services;

import org.example.pitest.Interfaces.IService;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Model.User;
import org.example.pitest.Model.Offre;
import org.example.pitest.Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    private Connection connexion;
    private static ReservationService instance;

    public ReservationService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    UserService us  = new UserService();
    OffreService os = new OffreService();

    @Override
    public void add(Reservation reservation) {
        String req = "INSERT INTO reservations (user_id, offre_id, price) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, reservation.getUser().getId());
            pst.setInt(2, reservation.getOffre().getId());
            pst.setDouble(3, reservation.getPrice());
            pst.executeUpdate();
            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réservation supprimée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reservation reservation, int id) {
        String req = "UPDATE reservations SET user_id = ?, offre_id = ?, price = ? WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, reservation.getUser().getId());
            pst.setInt(2, reservation.getOffre().getId());
            pst.setDouble(3, reservation.getPrice());
            pst.setInt(4, id);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Réservation mise à jour avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation find(int id) {
        String req = "SELECT * FROM reservations WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                return new Reservation(rs.getInt("id"), us.find(rs.getInt("user_id")), os.find(rs.getInt("offre_id")), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservations";
        try (Statement st = connexion.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {

                reservations.add(new Reservation(rs.getInt("id"), us.find(rs.getInt("user_id")), os.find(rs.getInt("offre_id")), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
