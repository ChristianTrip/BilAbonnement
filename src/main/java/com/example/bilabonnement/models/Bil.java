package com.example.bilabonnement.models;

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



    public String getStelNummer() {
        return stelNummer.toUpperCase();
    }

}
