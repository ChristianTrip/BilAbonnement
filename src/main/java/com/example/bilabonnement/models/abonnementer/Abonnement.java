package com.example.bilabonnement.models.abonnementer;

public abstract class Abonnement {

    protected int id;
    public int lejeaftaleId;
    protected boolean lavSelvrisiko;
    protected boolean afleveringsforsikring;
    protected int lejeperiodeMdr;

    public Abonnement(int id, boolean lavSelvrisiko) {
        this.id = id;
        this.lavSelvrisiko = lavSelvrisiko;
    }

    public Abonnement(){}

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public boolean isLavSelvrisiko() {
        return lavSelvrisiko;
    }

    public int getLejeperiodeMdr() {
        return lejeperiodeMdr;
    }

    public int getId() {
        return id;
    }

    public boolean isAfleveringsforsikring() {
        return afleveringsforsikring;
    }
}
