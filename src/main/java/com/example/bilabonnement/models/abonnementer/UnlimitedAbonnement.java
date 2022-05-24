package com.example.bilabonnement.models.abonnementer;

public class UnlimitedAbonnement extends Abonnement{


    public UnlimitedAbonnement(int id, int lejeaftaleId, int abonnementsLængde, boolean selvRisiko, boolean afleveringsForsikring, boolean valgtFarve) {
        super(id,lejeaftaleId, selvRisiko, valgtFarve);
        super.lejeperiodeMdr = abonnementsLængde;
        super.afleveringsforsikring = afleveringsForsikring;
    }

    public UnlimitedAbonnement(int lejePeriode, boolean selvRisiko, boolean afleveringsForsikring, boolean valgtFarve){
        super.lejeperiodeMdr = lejePeriode;
        super.lavSelvrisiko = selvRisiko;
        super.afleveringsforsikring = afleveringsForsikring;
        super.valgtFarve = valgtFarve;
    }

    @Override
    public String toString() {
        return "UnlimitedAbonnement{" +
                "\nid = " + id +
                "\nlejeaftaleId = " + lejeaftaleId +
                "\nlavSelvrisiko = " + lavSelvrisiko +
                "\nlejeperiodeMdr = " + lejeperiodeMdr +
                "\nafleveringsForsikring = " + afleveringsforsikring +
                '}' + "\n";
    }
}
