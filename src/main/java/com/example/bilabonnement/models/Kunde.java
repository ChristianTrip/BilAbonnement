package com.example.bilabonnement.models;

public class Kunde {


    private int lejeaftaleId;
    private String fornavn;
    private String efternavn;
    private String adresse;
    private String postnummer;
    private String by;
    private String email;
    private String mobil;
    private String cpr;
    private String regNummer;
    private String kontoNummer;

    public Kunde(String fornavn, String efternavn, String adresse, String postnummer, String by, String email, String mobil, String cpr, String regNummer, String kontoNummer) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.adresse = adresse;
        this.postnummer = postnummer;
        this.by = by;
        this.email = email;
        this.mobil = mobil;
        this.cpr = cpr;
        this.regNummer = regNummer;
        this.kontoNummer = kontoNummer;
    }


    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }


    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
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

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getRegNummer() {
        return regNummer;
    }

    public void setRegNummer(String regNummer) {
        this.regNummer = regNummer;
    }

    public String getKontoNummer() {
        return kontoNummer;
    }

    public void setKontoNummer(String kontoNummer) {
        this.kontoNummer = kontoNummer;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                ", forNavn='" + fornavn + '\'' +
                ", efterNavn='" + efternavn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", postNummer=" + postnummer +
                ", by='" + by + '\'' +
                ", email='" + email + '\'' +
                ", mobil=" + mobil +
                ", cpr=" + cpr +
                ", regNummer=" + regNummer +
                ", kontoNummer=" + kontoNummer +
                '}';
    }
}
