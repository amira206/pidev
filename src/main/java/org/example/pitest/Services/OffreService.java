package org.example.pitest.Services;

import org.example.pitest.Interfaces.IService;
import org.example.pitest.Model.Offre;
import org.example.pitest.Model.User;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements IService<Offre> {
    private Connection connexion;
    private static OffreService instance;

    public OffreService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public static OffreService getInstance() {
        if (instance == null) {
            instance = new OffreService();
        }
        return instance;
    }
    UserService us  = new UserService();
    ParcoursService ps = new ParcoursService();

    @Override
    public void add(Offre offre) {
        String req = "INSERT INTO offre (nbplaces, user_id, prix, parcours_id, date, bagage) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, offre.getNbPlaces());
            pst.setInt(2, offre.getUser().getId());
            pst.setDouble(3, offre.getPrix());
            pst.setInt(4, offre.getParcours().getId());
            pst.setDate(5, Date.valueOf(offre.getDate()));
            pst.setBoolean(6, offre.isBagage());
            pst.executeUpdate();
            System.out.println("Offre ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM offre WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Offre supprimée avec succès !");
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Offre offre, int id) {
        String req = "UPDATE offre SET nbplaces = ?, user_id = ?, prix = ?, parcours_id = ?, date = ?, bagage = ? WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, offre.getNbPlaces());
            pst.setInt(2, offre.getUser().getId());
            pst.setDouble(3, offre.getPrix());
            pst.setInt(4, offre.getParcours().getId());
            pst.setDate(5, Date.valueOf(offre.getDate()));
            pst.setBoolean(6, offre.isBagage());
            pst.setInt(7, id);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Offre mise à jour avec succès !");
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Offre find(int id) {
        String req = "SELECT * FROM offre WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                //User user = new User(); // Remplacez par un appel à UserService si disponible
                //Parcours parcours = new Parcours(); // Remplacez par un appel à ParcoursService si disponible
                //user.setId(rs.getInt("user_id"));
                //parcours.setId(rs.getInt("parcours_id"));
                return new Offre(
                        rs.getInt("id"),
                        rs.getInt("nbplaces"),
                        us.find(rs.getInt("user_id")),
                        rs.getDouble("prix"),
                        ps.find(rs.getInt("parcours_id")),
                        rs.getDate("date").toLocalDate(),
                        rs.getBoolean("bagage")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Offre> findAll() {
        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM offre";
        try (Statement st = connexion.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                offres.add(new Offre(
                        rs.getInt("id"),
                        rs.getInt("nbplaces"),
                        us.find(rs.getInt("user_id")),

                        rs.getDouble("prix"),
                        ps.find(rs.getInt("parcours_id")),

                        rs.getDate("date").toLocalDate(),
                        rs.getBoolean("bagage")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }
}
