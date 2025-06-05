package cr.ac.ucr.orientaucr.orientaucr.services;

import java.util.LinkedList;

public interface CRUD<T> {
    LinkedList<T> getAll(String search);

    LinkedList<T> getAll();

    void add(T t);

    void update(T t);

    void deleteById(String i);

    T findById(String i);
}
