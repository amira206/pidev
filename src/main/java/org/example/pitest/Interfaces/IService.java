package org.example.pitest.Interfaces;

import java.util.List;

public interface IService <T>{
    public void add(T t);
    public void delete(int id);
    public void update(T t, int id);
    public T find(int id);
    public List<T> findAll();
}
