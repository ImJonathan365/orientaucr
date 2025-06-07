package cr.ac.ucr.orientaucr.orientaucr.services;

import java.util.List;

public interface CRUD<T> {
    List<T> getAll(String search);

    List<T> getAll();

    void add(T t);

    void update(T t);

    void deleteById(String i);

    T findById(String i);
}
