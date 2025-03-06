package piDev.GestionLIvariason.Livraison.services;

import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLivraison implements IService<livraison> {

    Connection cnx;

    public ServiceLivraison() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(livraison l) {
        try {
            // Step 1: Prepare the SQL query with placeholders
            String req = "INSERT INTO livraison (start_location, delivery_location, is_delivered) VALUES (?, ?, ?)";

            // Step 2: Use PreparedStatement with RETURN_GENERATED_KEYS
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, l.getStart_location());
            ps.setString(2, l.getDelivery_location());
            ps.setBoolean(3, l.isIs_delivered());

            // Step 3: Execute the query
            ps.executeUpdate();

            // Step 4: Retrieve the generated id_livrai
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1); // Get the newly inserted ID
                l.setId_livrai(generatedId); // Store it inside the livraison object
                System.out.println("✅ Livraison added with ID: " + generatedId);
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error inserting livraison: " + ex.getMessage());
        }
    }

    @Override
    public void modifier(livraison l) {
        try {
            String req = "UPDATE `livraison` SET " +
                    "`start_location` =?, " +
                    "`delivery_location` =?, " +
                    "`is_delivered` =? " +
                    "WHERE `id_livrai` =?";

            PreparedStatement pstm = cnx.prepareStatement(req);

            pstm.setString(1, l.getStart_location());
            pstm.setString(2, l.getDelivery_location());
            pstm.setBoolean(3, l.isIs_delivered());
            System.out.println("ID to update: " + l.getId_livrai());
            pstm.setInt(4, l.getId_livrai()); // Corrected: Use the 4th placeholder

            pstm.executeUpdate();
            pstm.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void supprimer(livraison l) {
        try {
            String req = "DELETE FROM `livraison`"+
                    "WHERE `id_livrai` =?";

            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1 , l.getId_livrai());
            pstm.executeUpdate();
            pstm.close();

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<livraison> findAll() throws SQLException {
        String sql = "select * from livraison";
        Statement statement = cnx.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<livraison> list = new ArrayList<>();
        while (rs.next()){
            livraison p = new livraison();
            p.setId_livrai(rs.getInt("id_livrai"));
            p.setStart_location(rs.getString("start_location"));
            p.setDelivery_location(rs.getString("delivery_location"));
            p.setIs_delivered(rs.getBoolean("is_delivered"));
            list.add(p);

        }
        return list;
    }
    public List<livraison> searchLivraisons(String keyword) throws SQLException {
        List<livraison> resultList = new ArrayList<>();
        String query = "SELECT * FROM livraison WHERE start_location LIKE ? OR delivery_location LIKE ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                livraison liv = new livraison(
                        rs.getInt("id_livrai"),
                        rs.getString("start_location"),
                        rs.getString("delivery_location"),
                        rs.getBoolean("is_delivered")
                );
                resultList.add(liv);
            }
        }
        return resultList;
    }

    public List<livraison> sortLivraisonsByStartLocation() throws SQLException {
        List<livraison> sortedList = new ArrayList<>();
        String query = "SELECT * FROM livraison ORDER BY start_location ASC"; // Sorting by start location alphabetically

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                livraison liv = new livraison(
                        rs.getInt("id_livrai"),
                        rs.getString("start_location"),
                        rs.getString("delivery_location"),
                        rs.getBoolean("is_delivered")
                );
                sortedList.add(liv);
            }
        }
        return sortedList;
    }
}


