package com.example.bilabonnement.repositories;

import java.util.List;

public interface CRUDInterface<T>{

    //Create
    public boolean create(T entity);

    //Read
    public T getSingleEntityById(int id);
    public List<T> getAllEntities();

    //Update
    public boolean update(T entity);

    //Delete
    public boolean deleteById(int id);
}
