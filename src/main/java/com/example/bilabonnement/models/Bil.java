package com.example.bilabonnement.models;

public class Bil {

    private int id;
    private int lejeaftaleId;
    private String stelNummer; //<-
    private String name; //<-
    private String model; //<-
    private int kilometerPrLiter;
    private String nummerPlade;
    private Boolean manuelGear;
    private Brændstof brændstof;
    private int prisPrMdr;
    private boolean tilvalgtFarve;

    public Bil(int id, String stelNummer, String name, String model) {
        this.id = id;
        this.stelNummer = stelNummer;
        this.name = name;
        this.model = model;
    }

    public Bil(String stelNummer, String name, String model) {
        this.stelNummer = stelNummer;
        this.name = name;
        this.model = model;
    }

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public int getId() {
        return id;
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

    public void setStelNummer(String stelNummer) {
        this.stelNummer = stelNummer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Bil{" +
                "id=" + id +
                ", stelNummer='" + stelNummer + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
