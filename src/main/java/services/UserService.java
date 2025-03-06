package services;

import models.Role;
import models.User;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements CRUD<User> {
    private Connection connection;
    public UserService(){
        connection = MyDabase.getInstance().getConnection();
    }

    @Override
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO user (userName, email, password, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Définir les paramètres
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().toString());


            // Exécuter la mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Si des lignes ont été affectées, l'ajout a réussi
            if (rowsAffected > 0) {
                System.out.println("Utilisateur ajouté avec succès !");  // Utilisez JOptionPane si vous avez une interface graphique
            } else {
                System.out.println("L'ajout de l'utilisateur a échoué.");
            }
        } catch (SQLException e) {
            // En cas d'exception, afficher l'erreur
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }



    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET userName = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().name());
            preparedStatement.setInt(5, user.getId());  // Assurez-vous que l'ID est bien défini.
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user was updated.");
            }
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM `user` WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<User> list() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String userName = resultSet.getString("userName");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String roleString = resultSet.getString("role");

            // Convertir la chaîne en Role, avec gestion d'erreur si rôle invalide
            Role role;
            try {
                role = Role.valueOf(roleString.toUpperCase()); // Assure-toi que le rôle est en majuscules
            } catch (IllegalArgumentException e) {
                role = Role.PASSAGER; // Valeur par défaut ou gestion d'erreur
            }

            User user = new User(userName,email,password,role);
            users.add(user);
        }
        return users;}
    }

