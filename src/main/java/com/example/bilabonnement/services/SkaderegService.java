package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;

import java.util.ArrayList;

public class SkaderegService {
    private Tilstandsrapport rapport = new Tilstandsrapport();

    public ArrayList<Lejeaftale> getLejeaftaler(){
        return new ArrayList<Lejeaftale>();
    }

    public void opretSkade(String navn, String beskrivelse, int pris){
        Skade skade = new Skade(navn, beskrivelse, pris);
        rapport.tilfoejSkade(skade);
    }

    public void opretMangel(String navn, String beskrivelse, int pris){
        Mangel mangel = new Mangel(navn, beskrivelse, pris);
        rapport.tilfoejMangel(mangel);
    }

    public void opdaterTilstandsRapport(Lejeaftale lejeaftale){
        lejeaftale.setTilstandsrapport(rapport);
    }


}
