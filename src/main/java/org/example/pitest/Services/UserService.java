package org.example.pitest.Services;

import org.example.pitest.Interfaces.IService;
import org.example.pitest.Model.User;
import org.example.pitest.Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Connection connexion;
    private static UserService instance;

    public UserService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        String req = "INSERT INTO users (id, role) VALUES (?, ?)";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, user.getId());
            pst.setString(2, user.getRole());
            pst.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user, int id) {
        String req = "UPDATE users SET role = ? WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setString(1, user.getRole());
            pst.setInt(2, id);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur mis à jour avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        String req = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(req)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM users";
        try (Statement st = connexion.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
