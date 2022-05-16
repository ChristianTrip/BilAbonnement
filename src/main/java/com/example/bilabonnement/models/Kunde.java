package com.example.bilabonnement.models;

public class Kunde {


    private int lejeaftaleId;
    private String forNavn;
    private String efterNavn;
    private String adresse;
    private String postNummer;
    private String by;
    private String email;
    private String mobil;
    private String cpr;
    private String regNummer;
    private String kontoNummer;

    public Kunde(String forNavn, String efterNavn, String adresse, String postNummer, String by, String email, String mobil, String cpr, String regNummer, String kontoNummer) {
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


    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
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

    public String getPostNummer() {
        return postNummer;
    }

    public void setPostNummer(String postNummer) {
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
