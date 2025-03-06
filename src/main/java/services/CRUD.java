package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD <C> {
    void add(C c) throws SQLException;
    void update(C c) throws SQLException;
    void delete(int id) throws SQLException;
    List<C> list()throws SQLException;
}
