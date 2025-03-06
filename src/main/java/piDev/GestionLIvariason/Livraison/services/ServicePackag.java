package piDev.GestionLIvariason.Livraison.services;
import piDev.GestionLIvariason.Livraison.entities.packag;
import piDev.GestionLIvariason.Livraison.entities.packag;
import piDev.GestionLIvariason.Livraison.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePackag implements IService<packag> {

    Connection cnx;

    public ServicePackag() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(packag p) {
        try {
            String req = "INSERT INTO `packag` (`weight_packag`, `description_packag`, `id_livrai`) VALUES (?,?,?)";

            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, p.getWeight_packag());
            pstm.setString(2, p.getDescription_packag());
            pstm.setInt(3, p.getId_livrai());

            pstm.executeUpdate();
            pstm.close();

            System.out.println("Package inserted successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(packag p) {
        try {
            String req = "UPDATE `packag` SET " +
                    "`weight_packag` =?, " +
                    "`description_packag` =? " +
                    "WHERE `id_packag` =?";

            PreparedStatement pstm = cnx.prepareStatement(req);

            pstm.setInt(1, p.getWeight_packag());
            pstm.setString(2, p.getDescription_packag());
            pstm.setInt(3, p.getId_packag());

            pstm.executeUpdate();
            pstm.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void supprimer(packag p) {
        try {
            String req = "DELETE FROM `packag` WHERE `id_packag` =?";
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, p.getId_packag());
            pstm.executeUpdate();
            pstm.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public List<packag> findAll() throws SQLException {
        List<packag> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM packag";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                packag p = new packag();
                p.setId_packag(rs.getInt("id_packag"));
                p.setWeight_packag(rs.getInt("weight_packag"));
                p.setDescription_packag(rs.getString("description_packag"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching packages: " + ex.getMessage());
            ex.printStackTrace(); // Print the full stack trace
            throw ex; // Re-throw the exception after logging
        }
        return list;
    }
}
