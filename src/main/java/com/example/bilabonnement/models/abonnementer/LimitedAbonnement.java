package com.example.bilabonnement.models.abonnementer;

public class LimitedAbonnement extends Abonnement{



    public LimitedAbonnement(int id, boolean selvRisiko) {
        super(id, selvRisiko);
        super.lejeperiodeMdr = 4;
    }

    public LimitedAbonnement(boolean selvRisiko){
        super.lavSelvrisiko = selvRisiko;
        super.lejeperiodeMdr = 4;
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
