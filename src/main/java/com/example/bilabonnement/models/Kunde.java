package com.example.bilabonnement.models;

public class Kunde {

    private int id;
    private String forNavn;
    private String efterNavn;
    private String adresse;
    private int postNummer;
    private String by;
    private String email;
    private int mobil;
    private String cpr;
    private int regNummer;
    private int kontoNummer;

    public Kunde(int id, String forNavn, String efterNavn, String adresse, int postNummer, String by, String email, int mobil, String cpr, int regNummer, int kontoNummer) {
        this.id = id;
        this.forNavn = forNavn;
        this.efterNavn = efterNavn;
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.by = by;
        this.email = email;
        this.mobil = mobil;
        this.cpr = cpr;
        this.regNummer = regNummer;
        this.kontoNummer = kontoNummer;
    }

    public Kunde(String forNavn, String efterNavn, String adresse, int postNummer, String by, String email, int mobil, String cpr, int regNummer, int kontoNummer) {
        this.forNavn = forNavn;
        this.efterNavn = efterNavn;
        this.adresse = adresse;
        this.postNummer = postNummer;
        this.by = by;
        this.email = email;
        this.mobil = mobil;
        this.cpr = cpr;
        this.regNummer = regNummer;
        this.kontoNummer = kontoNummer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForNavn() {
        return forNavn;
    }

    public void setForNavn(String forNavn) {
        this.forNavn = forNavn;
    }

    public String getEfterNavn() {
        return efterNavn;
    }

    public void setEfterNavn(String efterNavn) {
        this.efterNavn = efterNavn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPostNummer() {
        return postNummer;
    }

    public void setPostNummer(int postNummer) {
        this.postNummer = postNummer;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobil() {
        return mobil;
    }

    public void setMobil(int mobil) {
        this.mobil = mobil;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public int getRegNummer() {
        return regNummer;
    }

    public void setRegNummer(int regNummer) {
        this.regNummer = regNummer;
    }

    public int getKontoNummer() {
        return kontoNummer;
    }

    public void setKontoNummer(int kontoNummer) {
        this.kontoNummer = kontoNummer;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "id=" + id +
                ", forNavn='" + forNavn + '\'' +
                ", efterNavn='" + efterNavn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", postNummer=" + postNummer +
                ", by='" + by + '\'' +
                ", email='" + email + '\'' +
                ", mobil=" + mobil +
                ", cpr=" + cpr +
                ", regNummer=" + regNummer +
                ", kontoNummer=" + kontoNummer +
                '}';
    }
}
