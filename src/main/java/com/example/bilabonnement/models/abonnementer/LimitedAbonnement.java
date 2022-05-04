package com.example.bilabonnement.models.abonnementer;

public class LimitedAbonnement extends Abonnement{

    private final int lejeperiodeMdr = 5;

    public LimitedAbonnement(int id) {
        super(id);
        super.lejeperiodeMdr = this.lejeperiodeMdr;
    }
}
