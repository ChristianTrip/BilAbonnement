package com.example.bilabonnement.models.abonnementer;

public class LimitedAbonnement extends Abonnement{



    public LimitedAbonnement(int id, boolean selvRisiko, boolean valgtFarve) {
        super(id, selvRisiko, valgtFarve);
        super.lejeperiodeMdr = 4;
    }

    public LimitedAbonnement(boolean selvRisiko, boolean valgtFarve){
        super.lavSelvrisiko = selvRisiko;
        super.valgtFarve = valgtFarve;
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
