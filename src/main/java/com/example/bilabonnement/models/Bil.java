package com.example.bilabonnement.models;

import java.util.Locale;

public class Bil {

    private String stelNummer; //<-
    private String name; //<-
    private String model; //<-
    private int kilometerPrLiter;
    private String nummerPlade;
    private Boolean manuelGear;
    private Brændstof brændstof;
    private int prisPrMdr;
    private boolean tilvalgtFarve;

    public Bil(String stelNummer, String name, String model) {
        this.stelNummer = stelNummer;
        this.name = name;
        this.model = model;
    }

    public String getStelNummer() {
        return stelNummer.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }
}
