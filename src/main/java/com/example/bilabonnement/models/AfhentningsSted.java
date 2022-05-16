package com.example.bilabonnement.models;

public class AfhentningsSted {

    private int id;
    private int lejeaftaleId;
    private final String adresse;
    private final String postNummer;
    private final String by;
    private final int levering;


    public AfhentningsSted(int id, String adresse, String postNummer, String by, int levering) {
        this.id = id;
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.by = by;
        this.levering = levering;
    }

    public AfhentningsSted(String adresse, String postNummer, String by, int levering) {
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.by = by;
        this.levering = levering;
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

    public String getPostNummer() {
        return postNummer;
    }

    public String getBy() {
        return by;
    }

    public int getLevering() {
        return levering;
    }

    @Override
    public String toString() {
        return "AfhentningsSted{" +
                "adresse='" + adresse + '\'' +
                ", postNummer=" + postNummer +
                ", by='" + by + '\'' +
                ", levering=" + levering +
                '}';
    }
}
