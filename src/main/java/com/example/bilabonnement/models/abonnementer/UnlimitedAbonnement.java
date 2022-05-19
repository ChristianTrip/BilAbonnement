package com.example.bilabonnement.models.abonnementer;

public class UnlimitedAbonnement extends Abonnement{


    public UnlimitedAbonnement(int id, int abonnementsLængde, boolean selvRisiko, boolean afleveringsForsikring, boolean valgtFarve) {
        super(id,selvRisiko, valgtFarve);
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
                "id=" + id +
                ", lavSelvrisiko=" + lavSelvrisiko +
                ", lejeperiodeMdr=" + lejeperiodeMdr +
                ", afleveringsForsikring=" + afleveringsforsikring +
                '}';
    }
}
