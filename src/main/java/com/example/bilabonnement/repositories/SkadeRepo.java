package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Skade;

import java.util.List;

public class SkadeRepo implements CRUDInterface<Skade>{

    @Override
    public boolean create(Skade skade) {
        return false;
    }

    @Override
    public Skade getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Skade> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Skade skade) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
