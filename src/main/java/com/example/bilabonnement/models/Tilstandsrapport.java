package com.example.bilabonnement.models;

import java.util.ArrayList;

public class Tilstandsrapport {

    private int id;
    private int totalPris;
    private ArrayList<Mangel> mangler;
    private ArrayList<Skade> skader;

    public Tilstandsrapport(int id, ArrayList<Mangel> mangler, ArrayList<Skade> skader) {
        this.id = id;
        this.mangler = mangler;
        this.skader = skader;
        this.totalPris = udregnTotalPris();
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
}
