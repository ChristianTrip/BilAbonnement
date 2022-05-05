package com.example.bilabonnement.models.brugere;

public class Bruger {
    private final int ID;
    private String navn;
    private String adgangskode;
    private int[] brugerType = new int[3];
    // 0 index = DataregBruger
    // 1 index = ForretningsBruger
    // 2 index = SkaderegBruger
    // værdien 0 = ingen adgang
    // værdien 1 = adgang

    public Bruger(int id, String navn, String adgangskode, int[] brugerType) {
        this.ID = id;
        this.navn = navn;
        this.adgangskode = adgangskode;
        this.brugerType = brugerType;
    }

    protected int getId(){
        return this.ID;
    }

    protected int[] getBrugerType(){
        return this.brugerType;
    }

}
