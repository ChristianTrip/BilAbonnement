package com.example.bilabonnement.models.prisoverslag;

public class Prisoverslag {

    private int id;
    private int lejeaftaleId;
    private int abonnementslængde;
    private int totalpris;

    public Prisoverslag(int id, int lejeaftaleId, int totalpris, int abonnementslaengde) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.totalpris = totalpris;
        this.abonnementslængde = abonnementslaengde;
    }

    public Prisoverslag(int totalpris, int abonnementslaengde){
        this.totalpris = totalpris;
        this.abonnementslængde = abonnementslaengde;
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

    public int getTotalpris() {
        return totalpris;
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
