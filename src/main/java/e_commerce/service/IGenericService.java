package e_commerce.service;

import java.util.List;

public interface IGenericService<T, E> {
    List<T> getAll();
    List<T> findAll(int page,String name);
    T findById(E id);
    void save(T t);
    void delete(E id);
}
