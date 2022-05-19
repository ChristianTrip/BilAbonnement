package com.example.bilabonnement.models.abonnementer;

public class UnlimitedAbonnement extends Abonnement{


    public UnlimitedAbonnement(int id, int abonnementsLængde, boolean selvRisiko, boolean afleveringsForsikring) {
        super(id,selvRisiko);
        super.lejeperiodeMdr = abonnementsLængde;
        super.afleveringsforsikring = afleveringsForsikring;
    }

    public UnlimitedAbonnement(int lejePeriode, boolean selvRisiko, boolean afleveringsForsikring){
        super.lejeperiodeMdr = lejePeriode;
        super.lavSelvrisiko = selvRisiko;
        super.afleveringsforsikring = afleveringsForsikring;
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
