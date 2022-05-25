package com.example.bilabonnement.models;

public class Skade {

    private int id;
    private int tilstandsRapportId;
    private String navn;
    private String beskrivelse;
    private int pris;


    public Skade(int id, int tilstandsRapportId, String navn, String beskrivelse, int pris) {
        this.id = id;
        this.tilstandsRapportId = tilstandsRapportId;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.pris = pris;
    }

    public Skade(String navn, String beskrivelse, int pris){
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.pris = pris;

    }


    public void setTilstandsRapportId(int tilstandsRapportId) {
        this.tilstandsRapportId = tilstandsRapportId;
    }

    public int getId() {
        return id;
    }

    public int getTilstandsRapportId() {
        return tilstandsRapportId;
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

    @Override
    public String toString() {
        return "Skade{" +
                "id=" + id +
                ", tilstandsRapportId=" + tilstandsRapportId +
                ", navn='" + navn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", pris=" + pris +
                '}';
    }
}
