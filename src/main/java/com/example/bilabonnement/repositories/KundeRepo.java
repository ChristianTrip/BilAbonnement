package com.example.bilabonnement.repositories;

import java.util.List;

public class KundeRepo implements CRUDInterface{

    @Override
    public boolean create(Object entity) {
        return false;
    }

    @Override
    public Object getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
