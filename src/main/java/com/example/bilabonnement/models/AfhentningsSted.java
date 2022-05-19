package com.example.bilabonnement.models;

public class AfhentningsSted {

    private int id;
    private int lejeaftaleId;
    private final String adresse;
    private final String postnummer;
    private final String by;
    private final int leveringspris;


    public AfhentningsSted(int id, String adresse, String postnummer, String by, int leveringspris) {
        this.id = id;
        this.adresse = adresse;
        this.postnummer = postnummer;
        this.by = by;
        this.leveringspris = leveringspris;
    }

    public AfhentningsSted(String adresse, String postnummer, String by, int leveringspris) {
        this.adresse = adresse;
        this.postnummer = postnummer;
        this.by = by;
        this.leveringspris = leveringspris;
    }

    public int getId() {
        return id;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public String getBy() {
        return by;
    }

    public int getLeveringspris() {
        return leveringspris;
    }

    @Override
    public String toString() {
        return "AfhentningsSted{" +
                "adresse='" + adresse + '\'' +
                ", postNummer=" + postnummer +
                ", by='" + by + '\'' +
                ", levering=" + leveringspris +
                '}';
    }
}
