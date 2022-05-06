package com.example.bilabonnement.models.abonnementer;

public class UnlimitedAbonnement extends Abonnement{

    private boolean afleveringsForsikring;


    public UnlimitedAbonnement(int id, int abonnementsLængde, boolean selvRisiko, boolean afleveringsForsikring) {
        super(id,selvRisiko);
        super.lejeperiodeMdr = abonnementsLængde;
        this.afleveringsForsikring = afleveringsForsikring;
    }

    public UnlimitedAbonnement(int lejePeriode, boolean selvRisiko, boolean afleveringsForsikring){
        super.lejeperiodeMdr = lejePeriode;
        super.lavSelvrisiko = selvRisiko;
        this.afleveringsForsikring = afleveringsForsikring;
    }

    @Override
    public String toString() {
        return "UnlimitedAbonnement{" +
                "id=" + id +
                ", lavSelvrisiko=" + lavSelvrisiko +
                ", lejeperiodeMdr=" + lejeperiodeMdr +
                ", afleveringsForsikring=" + afleveringsForsikring +
                '}';
    }
}
