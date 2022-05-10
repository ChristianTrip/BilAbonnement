package com.example.bilabonnement.models.abonnementer;

public class LimitedAbonnement extends Abonnement{



    public LimitedAbonnement(int id, boolean selvRisiko) {
        super(id, selvRisiko);
        super.lejeperiodeMdr = 5;
    }

    public LimitedAbonnement(boolean selvRisiko){
        super.lavSelvrisiko = selvRisiko;
        super.lejeperiodeMdr = 5;
    }

    @Override
    public String toString() {
        return "LimitedAbonnement{" +
                "id=" + id +
                ", lavSelvrisiko=" + lavSelvrisiko +
                ", lejeperiodeMdr=" + lejeperiodeMdr +
                '}';
    }
}
