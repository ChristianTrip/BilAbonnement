package com.example.bilabonnement.models;

import java.util.ArrayList;

public class Tilstandsrapport {
    private int id;
    private ArrayList<Mangel> mangler;
    private ArrayList<Skade> skader;

    public Tilstandsrapport(int id, ArrayList<Mangel> mangler, ArrayList<Skade> skader) {
        this.id = id;
        this.mangler = mangler;
        this.skader = skader;
    }

    public Tilstandsrapport(){}

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
