package GestionEvenement3a16.Services;


import java.sql.SQLException;
import java.util.List;

public interface CRUD <C> {
    void add(C c) throws SQLException;
    void update(C c) throws SQLException;
    void delete(int id) throws SQLException;
    List<C> list()throws SQLException;
    C getById(int id) throws SQLException;  // New method to retrieve an entity by id

}