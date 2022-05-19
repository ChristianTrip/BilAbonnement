package com.example.bilabonnement.models.prisoverslag;

public class Prisoverslag {

    private int id;
    private int lejeaftaleId;
    private int abonnementslængde;
    private int totalpris;
    private int abonnementsLængde;
    private int kmPrMdr;
    private int totalPris;

    public Prisoverslag(int id, int lejeaftaleId, int abonnementslaengde, int kmPrMdr) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.abonnementsLængde = abonnementslaengde;
        this.kmPrMdr = kmPrMdr;
        this.totalPris = 0;
    }

    public Prisoverslag(int abonnementslaengde, int kmPrMdr){
        this.abonnementsLængde = abonnementslaengde;
        this.kmPrMdr = kmPrMdr;
        this.totalPris = 0;
    }

    public int getId() {
        return id;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public int getAbonnementsLaengde() {
        return abonnementslængde;
    }

    public int getKmPrMdr() {
        return kmPrMdr;
    }

    public int getTotalpris() {
        return totalPris;
    }

    @Override
    public String toString() {
        return "Prisoverslag{" +
                "id=" + id +
                ", abonnementsLængde=" + abonnementslængde +
                ", totalPris=" + totalpris +
                '}';
    }
}
