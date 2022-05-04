package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;

import java.util.ArrayList;

public class SkaderegService {
    private Tilstandsrapport rapport = null;

    public ArrayList<Lejeaftale> getLejeaftaler(){
        return new ArrayList<Lejeaftale>();
    }

    public Skade opretSkade(){
        return new Skade();
    }

    public Mangel opretMangel(){
        return new Mangel();
    }

    public void opdaterTilstandsRapport(){
        rapport.setMangel(opretMangel());
        rapport.setSkade(opretSkade());
    }


}
