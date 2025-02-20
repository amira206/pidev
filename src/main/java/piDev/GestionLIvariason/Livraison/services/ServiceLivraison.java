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
            String req = "INSERT INTO `livraison`(" +
                    " `start_location`," +
                    " `delivery_location`," +
                    " `is_delivered`) " +
                    "VALUES ('"
                    + l.getStart_location() + "','"
                    + l.getDelivery_location() + "',"
                    + l.isIs_delivered() + ")";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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

}
