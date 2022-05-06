package com.example.bilabonnement.models.brugere;

import java.util.Arrays;

public class Bruger {
    private final int id;
    private String navn;
    private String adgangskode;
    private BrugerType brugerType;
    // 0 index = DataregBruger
    // 1 index = ForretningsBruger
    // 2 index = SkaderegBruger
    // værdien 0 = ingen adgang
    // værdien 1 = adgang

    public Bruger(int id, String navn, String adgangskode, BrugerType brugerType) {
        this.id = id;
        this.navn = navn;
        this.adgangskode = adgangskode;
        this.brugerType = brugerType;
    }

    public Bruger(String navn, String adgangskode, BrugerType brugerType) {
        this.id = -1;
        this.navn = navn;
        this.adgangskode = adgangskode;
        this.brugerType = brugerType;
    }

    public int getId(){
        return this.id;
    }


    public BrugerType getBrugerType(){
        return this.brugerType;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdgangskode() {
        return adgangskode;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setAdgangskode(String adgangskode) {
        this.adgangskode = adgangskode;
    }

    public void setBrugerType(BrugerType brugerType) {
        this.brugerType = brugerType;
    }

    @Override
    public String toString() {
        return "\nBruger:" +
                "\n\tid = " + id +
                "\n\tnavn = " + navn +
                "\n\tadgangskode = " + adgangskode +
                "\n\tbrugerType = " + brugerType +
                "\n";
    }
}
