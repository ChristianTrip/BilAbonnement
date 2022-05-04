package com.example.bilabonnement.models;


import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;

import java.util.Date;

public class Lejeaftale {

    private Kunde kunde;
    private Bil bil;
    private Tilstandrapport tilstandsRapport;
    private Abonnement abonnement;
    private Prisoverslag prisoverslag;
    private AfhentningsSted afhentningsSted;
    private int totalPris;
    private Date oprettelsesDato;


    public Lejeaftale(Kunde kunde, Bil bil, Tilstandrapport tilstandsRapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted, int totalPris, Date oprettelsesDato) {
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsRapport = tilstandsRapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.afhentningsSted = afhentningsSted;
        this.totalPris = totalPris;
        this.oprettelsesDato = oprettelsesDato;
    }


    public Kunde getKunde() {
        return kunde;
    }

    public Bil getBil() {
        return bil;
    }

    public Tilstandrapport getTilstandsRapport() {
        return tilstandsRapport;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public Prisoverslag getPrisoverslag() {
        return prisoverslag;
    }

    public AfhentningsSted getAfhentningsSted() {
        return afhentningsSted;
    }

    public int getTotalPris() {
        return totalPris;
    }

    public Date getOprettelsesDato() {
        return oprettelsesDato;
    }
}
