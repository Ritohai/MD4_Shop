package e_commerce.dao;

import java.util.List;


public interface IGenericDao<T, E> {
    List<T> getAll();
    List<T> findAll(int page,String name);
    T findById(E id);
    void save(T t);
    void delete(E id);
}