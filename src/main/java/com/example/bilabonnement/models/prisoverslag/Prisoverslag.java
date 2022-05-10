package com.example.bilabonnement.models.prisoverslag;

public class Prisoverslag {

    private int id;
    private int lejeaftaleId;
    private int abonnementsLængde;
    private int totalPris;

    public Prisoverslag(int id, int lejeaftaleId, int totalpris, int abonnementslaengde) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.totalPris = totalpris;
        this.abonnementsLængde = abonnementslaengde;
    }

    public Prisoverslag(int totalpris, int abonnementslaengde){
        this.totalPris = totalpris;
        this.abonnementsLængde = abonnementslaengde;
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

    public int getAbonnementsLængde() {
        return abonnementsLængde;
    }

    public int getTotalPris() {
        return totalPris;
    }

    @Override
    public String toString() {
        return "Prisoverslag{" +
                "id=" + id +
                ", abonnementsLængde=" + abonnementsLængde +
                ", totalPris=" + totalPris +
                '}';
    }
}
