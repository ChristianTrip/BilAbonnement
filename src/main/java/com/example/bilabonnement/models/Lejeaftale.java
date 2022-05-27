package com.example.bilabonnement.models;

import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;

import java.time.LocalDate;
import java.util.Date;

public class Lejeaftale {

    private int id;
    private Kunde kunde;
    private Bil bil;
    private Tilstandsrapport tilstandsrapport; //= new Tilstandsrapport();
    private Abonnement abonnement;
    private Prisoverslag prisoverslag;
    private AfhentningsSted afhentningsSted;
    private LocalDate oprettelsesDato;
    private LocalDate startDato;
    private LocalDate slutDato;


    public Lejeaftale(int id, Kunde kunde, Bil bil, Tilstandsrapport tilstandsrapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted, LocalDate oprettelsesDato, LocalDate startDato) {
        this.id = id;
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsrapport = tilstandsrapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.prisoverslag.setAbonnementsLængde(abonnement.getLejeperiodeMdr());
        this.afhentningsSted = afhentningsSted;
        this.oprettelsesDato = oprettelsesDato;
        this.startDato = startDato;
        setSlutDato();
    }

    // constructor til at oprette en lejeaftale i databasen
    public Lejeaftale(Kunde kunde, Bil bil, Tilstandsrapport tilstandsrapport, Abonnement abonnement, Prisoverslag prisoverslag, AfhentningsSted afhentningsSted) {
        this.kunde = kunde;
        this.bil = bil;
        this.tilstandsrapport = tilstandsrapport;
        this.abonnement = abonnement;
        this.prisoverslag = prisoverslag;
        this.prisoverslag.setAbonnementsLængde(abonnement.getLejeperiodeMdr());
        this.afhentningsSted = afhentningsSted;
        this.oprettelsesDato = LocalDate.now();

    }

    public boolean isActive(){
        return this.slutDato.isAfter(LocalDate.now());
    }


    private void setSlutDato(){
        int lejeperiode = abonnement.getLejeperiodeMdr();
        slutDato = startDato.plusMonths(lejeperiode);
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

    public LocalDate getOprettelsesDato() {
        return oprettelsesDato;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    //Used in alleLejeaftaler.html(th:if)
    public boolean isTilstandsrapportEmpty() {
        return this.tilstandsrapport.getSkader().isEmpty() && this.tilstandsrapport.getMangler().isEmpty();
    }

    public void setTilstandsrapport(Tilstandsrapport tilstandsrapport) {
        this.tilstandsrapport = tilstandsrapport;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
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
                "\noprettelsesDato = " + oprettelsesDato + "\n" +
                '}';
    }
}
