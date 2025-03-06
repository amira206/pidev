package piDev.GestionLIvariason.Livraison.services;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    public void ajouter(T t);

    public void supprimer(T t);
    public void modifier(T t);
    //public T recherche(T t);
    public List<T> findAll() throws SQLException;
}
