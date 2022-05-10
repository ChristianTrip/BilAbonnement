package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;

import java.util.ArrayList;

public class SkaderegService {
    //private Tilstandsrapport rapport = new Tilstandsrapport();

    public ArrayList<Lejeaftale> getLejeaftaler(){
        return new ArrayList<Lejeaftale>();
    }

    public void opretSkade(){
        /*
        Skade skade = new Skade();
        rapport.tilfoejSkade(skade);

         */
    }

    public void opretMangel(){
        /*
        Mangel mangel = new Mangel();
        rapport.tilfoejMangel(mangel);

         */
    }

    public void opdaterTilstandsRapport(Lejeaftale lejeaftale){
        //lejeaftale.setTilstandsrapport(rapport);
    }


}
