package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Mangel;

import java.util.List;

public class MangelRepo implements CRUDInterface<Mangel>{

    @Override
    public boolean create(Mangel mangel) {
        return false;
    }

    @Override
    public Mangel getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Mangel> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Mangel mangel) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
