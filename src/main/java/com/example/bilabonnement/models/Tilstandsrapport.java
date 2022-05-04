package com.example.bilabonnement.models;

public class Tilstandsrapport {
    private final int id;
    private Mangel mangel;
    private Skade skade;

    public Tilstandsrapport(int id, Mangel mangel, Skade skade) {
        this.id = id;
        this.mangel = mangel;
        this.skade = skade;
    }

    public void setMangel(Mangel mangel) {
        this.mangel = mangel;
    }

    public void setSkade(Skade skade) {
        this.skade = skade;
    }

    public int getId() {
        return id;
    }
}
