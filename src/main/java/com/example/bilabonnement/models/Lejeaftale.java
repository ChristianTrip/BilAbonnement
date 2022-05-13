package com.example.bilabonnement.models;

import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;

import java.util.Date;

public class Lejeaftale {

    private int id;
    private Kunde kunde;
    private Bil bil;
    private Tilstandsrapport tilstandsrapport = new Tilstandsrapport();
    private Abonnement abonnement;
    private Prisoverslag prisoverslag;
    private AfhentningsSted afhentningsSted;
    private int totalPris;
    private Date oprettelsesDato;


    public Lejeaftale(int id, Kunde kunde, Bil bil, Tilstandsrapport tilstandsrapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted, Date oprettelsesDato) {
        this.id = id;
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsrapport = tilstandsrapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.afhentningsSted = afhentningsSted;
        this.oprettelsesDato = oprettelsesDato;
    }

    public Lejeaftale(Kunde kunde, Bil bil, Tilstandsrapport tilstandsrapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted) {
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsrapport = tilstandsrapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.afhentningsSted = afhentningsSted;
        this.oprettelsesDato = new Date();
    }



    public int getId() {
        return id;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Bil getBil() {
        return bil;
    }

    public Tilstandsrapport getTilstandsrapport() {
        return tilstandsrapport;
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

    public void setTilstandsrapport(Tilstandsrapport tilstandsrapport) {
        this.tilstandsrapport = tilstandsrapport;
    }


    @Override
    public String toString() {
        return "Lejeaftale{" +
                "\nid = " + id +
                "\nkunde = " + kunde +
                "\nbil = " + bil +
                "\ntilstandsrapport = " + tilstandsrapport +
                "\nabonnement = " + abonnement +
                "\nprisoverslag = " + prisoverslag +
                "\nafhentningsSted = " + afhentningsSted +
                "\ntotalPris = " + totalPris +
                "\noprettelsesDato = " + oprettelsesDato + "\n" +
                '}';
    }
}
