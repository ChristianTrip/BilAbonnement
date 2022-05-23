package com.example.bilabonnement.models.prisoverslag;

public class Prisoverslag {

    private int id;
    private int lejeaftaleId;
    private int abonnementsLængde;
    private int kmPrMdr;
    private int totalPris;

    // vi går ud fra at vi får en totalpris fra csv filen, sådan at vi ikke skal udregne den.
    // så den skal sættes i constructoren

    public Prisoverslag(int id, int lejeaftaleId, int abonnementslaengde, int kmPrMdr, int totalPris) {
        this.id = id;
        this.lejeaftaleId = lejeaftaleId;
        this.abonnementsLængde = abonnementslaengde;
        this.kmPrMdr = kmPrMdr;
        this.totalPris = totalPris;
    }

    public Prisoverslag(int abonnementslaengde, int kmPrMdr, int totalPris){
        this.abonnementsLængde = abonnementslaengde;
        this.kmPrMdr = kmPrMdr;
        this.totalPris = totalPris;
    }

    public void setAbonnementsLængde(int abonnementsLængde) {
        this.abonnementsLængde = abonnementsLængde;
    }

    private void udregnTotalpris(){

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
        return abonnementsLængde;
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
                "\nid = " + id +
                "\nlejeaftaleId = " + lejeaftaleId +
                "\nabonnementsLængde = " + abonnementsLængde +
                "\nkmPrMdr = " + kmPrMdr +
                "\ntotalPris = " + totalPris +
                '}' + "\n";
    }
}
