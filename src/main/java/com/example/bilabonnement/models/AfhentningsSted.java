package com.example.bilabonnement.models;

public class AfhentningsSted {

    private final String adresse;
    private final int postNummer;
    private final String by;
    private final int levering;


    AfhentningsSted(String adresse, int postNummer, String by, int levering) {
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.by = by;
        this.levering = levering;
    }
}
