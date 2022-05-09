package com.example.bilabonnement.models;

public class Skade {

    private int id;
    private String navn;
    private String beskrivelse;
    private int pris;


    public Skade(int id, String navn, String beskrivelse, int pris) {
        this.id = id;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.pris = pris;
    }

    public Skade(String navn, String beskrivelse, int pris){
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.pris = pris;
    }

    public int getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public int getPris() {
        return pris;
    }
}
