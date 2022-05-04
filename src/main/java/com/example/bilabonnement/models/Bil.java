package com.example.bilabonnement.models;

public class Bil {

    private int id;
    private String name;
    private String model;
    private int kilometerPrLiter;
    private String stelNummer;
    private String nummerPlade;
    private Boolean manuelGear;
    private Brændstof brændstof;
    private int prisPrMdr;
    private boolean tilvalgtFarve;

    public int getId() {
        return id;
    }
}
