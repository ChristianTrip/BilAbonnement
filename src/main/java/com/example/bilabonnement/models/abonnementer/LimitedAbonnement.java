package com.example.bilabonnement.models.abonnementer;

public class LimitedAbonnement extends Abonnement{



    public LimitedAbonnement(int id, int lejeaftaleId, boolean selvRisiko, boolean valgtFarve) {
        super(id, lejeaftaleId, selvRisiko, valgtFarve);
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
                "\nid = " + id +
                "\nlejeaftaleId = " + lejeaftaleId +
                "\nlavSelvrisiko = " + lavSelvrisiko +
                "\nlejeperiodeMdr = " + lejeperiodeMdr +
                '}' + "\n";
    }
}
