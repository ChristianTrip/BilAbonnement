package com.example.bilabonnement.models.prisoverslag;

public class Prisoverslag {

    private int id;
    private int abonnementsLængde;
    private int totalPris;

    public Prisoverslag(int id) {
        this.id = id;
    }

    public Prisoverslag(int totalpris, int abonnementslaengde){
        this.totalPris = totalpris;
        this.abonnementsLængde = abonnementslaengde;
    }

    public int getId() {
        return id;
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
