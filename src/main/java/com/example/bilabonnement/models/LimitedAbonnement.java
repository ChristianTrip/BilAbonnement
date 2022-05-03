package com.example.bilabonnement.models;

public class LimitedAbonnement extends Abonnement{

    private final int lejeperiodeMdr = 5;

    public LimitedAbonnement() {
        super.lejeperiodeMdr = this.lejeperiodeMdr;
    }
}
