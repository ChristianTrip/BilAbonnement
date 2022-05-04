package com.example.bilabonnement.models.abonnementer;

public abstract class Abonnement {

    protected final int id;
    protected boolean lavSelvrisiko;
    protected int lejeperiodeMdr;

    public Abonnement(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
