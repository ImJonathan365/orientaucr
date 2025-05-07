package cr.ac.ucr.orientaucr.orientaucr.dao;

import java.util.LinkedList;

public interface CRUD<T> {

    public LinkedList<T> getAll(String search);

    public LinkedList<T> getAll();

    public boolean add(T t);

    public boolean update(T t);

    public boolean deleteById(Integer i);

    public T findById(Integer i);
}
