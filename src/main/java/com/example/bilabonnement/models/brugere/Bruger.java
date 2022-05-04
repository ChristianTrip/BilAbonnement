package com.example.bilabonnement.models.brugere;

public abstract class Bruger {
    protected final int ID;
    protected String navn;
    protected String adgangskode;

    protected Bruger(int id) {
        this.ID = id;
    }

    protected int getId(){
        return this.ID;
    }

}
