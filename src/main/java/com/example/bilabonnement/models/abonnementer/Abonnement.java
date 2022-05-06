package com.example.bilabonnement.models.abonnementer;

public abstract class Abonnement {

    protected int id;
    protected boolean lavSelvrisiko;
    protected int lejeperiodeMdr;

    public Abonnement(int id, boolean lavSelvrisiko) {
        this.id = id;
        this.lavSelvrisiko = lavSelvrisiko;
    }

    public Abonnement(){}

    public int getId() {
        return id;
    }
}
