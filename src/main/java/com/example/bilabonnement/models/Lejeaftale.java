package com.example.bilabonnement.models;


import com.example.bilabonnement.models.prisoverslag.Prisoverslag;

import java.util.Date;

public class Lejeaftale {

    private Kunde kunde;
    private Bil bil;
    private TilstandRapport tilstandsRapport;
    private Abonnement abonnement;
    private Prisoverslag prisoverslag;
    private AfhentningsSted afhentningsSted;
    private int totalPris;
    private Date oprettelsesDato;


    public Lejeaftale(Kunde kunde, Bil bil, TilstandRapport tilstandsRapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted, int totalPris, Date oprettelsesDato) {
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsRapport = tilstandsRapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.afhentningsSted = afhentningsSted;
        this.totalPris = totalPris;
        this.oprettelsesDato = oprettelsesDato;
    }

    public Date getOprettelsesDato() {
        return oprettelsesDato;
    }
}
