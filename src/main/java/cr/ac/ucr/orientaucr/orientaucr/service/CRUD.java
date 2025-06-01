package cr.ac.ucr.orientaucr.orientaucr.service;

import java.util.LinkedList;

public interface CRUD<T> {
    public LinkedList<T> getAll(String search);

    public LinkedList<T> getAll();

    public void add(T t);

    public void update(T t);

    public void deleteById(String i);

    public T findById(String i);
}
