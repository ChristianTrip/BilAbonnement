package com.example.bilabonnement.models;

public class TilstandRapport {
    private Mangel mangel;
    private Skade skade;

    public TilstandRapport(Mangel mangel, Skade skade) {
        this.mangel = mangel;
        this.skade = skade;
    }

    public void setMangel(Mangel mangel) {
        this.mangel = mangel;
    }

    public void setSkade(Skade skade) {
        this.skade = skade;
    }
}
