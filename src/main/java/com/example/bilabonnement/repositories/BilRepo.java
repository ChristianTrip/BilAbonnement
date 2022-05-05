package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Bil;

import java.util.List;

public class BilRepo implements CRUDInterface<Bil> {


    @Override
    public boolean create(Bil bil) {
        return false;
    }

    @Override
    public Bil getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Bil> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Bil bil) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
