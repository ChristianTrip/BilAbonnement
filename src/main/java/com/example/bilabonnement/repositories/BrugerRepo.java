package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.brugere.Bruger;

import java.util.List;

public class BrugerRepo implements CRUDInterface<Bruger>{
    @Override
    public boolean create(Bruger entity) {
        return false;
    }

    @Override
    public Bruger getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Bruger entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
