package com.example.bilabonnement.models.prisoverslag;

public enum AbonnementsLængde {

    TRE(3),
    FIRE(4),
    FEM(5),
    SEKS(6),
    TOLV(12),
    FIREOGTYVE(24),
    SEKSOGTREDIVE(36);


    private final int mdr;

    AbonnementsLængde(int mdr) {
        this.mdr = mdr;
    }
}
