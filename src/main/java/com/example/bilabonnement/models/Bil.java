package com.example.bilabonnement.models;

public class Bil {

    private int id;
    private int lejeaftaleId;
    private String stelnummer; //<-
    private String name; //<-
    private String model; //<-
    /*
    private int kilometerPrLiter;
    private String nummerPlade;
    private Boolean manuelGear;
    private Brændstof brændstof;
    private int prisPrMdr;
    private boolean tilvalgtFarve;
     */

    public Bil(int id, String stelnummer, String name, String model) {
        this.id = id;
        this.stelnummer = stelnummer;
        this.name = name;
        this.model = model;
    }

    public Bil(String stelnummer, String name, String model) {
        /*
        if(stelNummer == null)
            throw new IllegalArgumentException("stelnummer er null");
        if(name == null)
            throw new IllegalArgumentException("name er null");
        if(model == null)
            throw new IllegalArgumentException("model er null");
         */

        this.stelnummer = stelnummer;
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

    public String getStelnummer() {
        return stelnummer.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public void setStelnummer(String stelnummer) {
        this.stelnummer = stelnummer;
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
                ", stelNummer='" + stelnummer + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
