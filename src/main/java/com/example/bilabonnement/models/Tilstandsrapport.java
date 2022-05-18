package com.example.bilabonnement.models;

import java.util.ArrayList;

public class Tilstandsrapport {

    private int id;
    private int lejeaftaleId;
    private int totalPris;
    private ArrayList<Mangel> mangler;
    private ArrayList<Skade> skader;

    public Tilstandsrapport(int id, int lejeaftaleId, ArrayList<Mangel> mangler, ArrayList<Skade> skader) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.mangler = mangler;
        this.skader = skader;
        this.totalPris = udregnTotalPris();
    }

    public Tilstandsrapport() {
        this.mangler = new ArrayList<>();
        this.skader = new ArrayList<>();
    }

    public Tilstandsrapport(int id, int lejeaftaleId) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.mangler = new ArrayList<>();
        this.skader = new ArrayList<>();
    }

    public Tilstandsrapport(ArrayList<Mangel> mangler, ArrayList<Skade> skader){
        this.mangler = mangler;
        this.skader = skader;
        this.totalPris = udregnTotalPris();
    }

    private int udregnTotalPris(){
        int prisToReturn = 0;
        for (Mangel mangel : mangler) {
            prisToReturn += mangel.getPris();
        }

        for (Skade skade : skader) {
            prisToReturn += skade.getPris();
        }

        return prisToReturn;
    }

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public void tilfoejMangel(Mangel mangel){
        mangler.add(mangel);
    }

    public void tilfoejSkade(Skade skade){
        skader.add(skade);
    }

    public void setMangel(ArrayList<Mangel> mangler) {
        this.mangler = mangler;
    }

    public void setSkade(ArrayList<Skade> skader) {
        this.skader = skader;
    }

    public int getId() {
        return id;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public ArrayList<Mangel> getMangler() {
        return mangler;
    }

    public ArrayList<Skade> getSkader() {
        return skader;
    }

    @Override
    public String toString() {
        return "Tilstandsrapport{" +
                "\nid = " + id +
                "\nlejeaftaleId = " + lejeaftaleId +
                "\ntotalPris = " + totalPris +
                "\nmangler = " + mangler +
                "\nskader = " + skader + "\n" +
                "\n";
    }
}
