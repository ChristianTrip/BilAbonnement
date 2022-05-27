package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;
import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.repositories.MangelRepo;
import com.example.bilabonnement.repositories.SkadeRepo;
import com.example.bilabonnement.repositories.TilstandsrapportRepo;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class SkaderegService {

    private LejeaftaleRepo lejeaftaleRepo = new LejeaftaleRepo();
    private TilstandsrapportRepo tilstandsrapportRepo = new TilstandsrapportRepo();
    private SkadeRepo skadeRepo = new SkadeRepo();
    private MangelRepo mangelRepo = new MangelRepo();
    private Tilstandsrapport rapport;



    public boolean tilføjSkade(String titel, String beskrivelse, String pris){
        if(!titel.equals("") || !beskrivelse.equals("") || !pris.equals("") ) {
            Skade skade = new Skade(titel, beskrivelse, parseInt(pris));
            skade.setTilstandsRapportId(rapport.getId());
            if (skadeRepo.create(skade)) {
                return true;
            }
        }
        return false;
    }

    public boolean tilføjMangel(String titel, String beskrivelse, String pris){

        if(!titel.equals("") || !beskrivelse.equals("") || !pris.equals("")) {
            Mangel mangel = new Mangel(titel, beskrivelse, parseInt(pris));
            mangel.setTilstandsRapportId(rapport.getId());
            if (mangelRepo.create(mangel)){
                return true;
            }
        }
        return false;
    }

    public boolean fjernSkade(Skade skade){

        if (skadeRepo.deleteById(skade.getId())){
            return true;
        }
        return false;
    }

    public boolean fjernMangel(Mangel mangel){

        if (mangelRepo.deleteById(mangel.getId())){
            return true;
        }
        return false;
    }

    public void setRapport(Tilstandsrapport tilstandsrapport){
        rapport = tilstandsrapport;
    }


    public void createSkade(String navn, String beskrivelse, int pris){
        Skade skade = new Skade(navn, beskrivelse, pris);
        skade.setTilstandsRapportId(rapport.getId());
        rapport.tilfoejSkade(skade);
    }

    public void createMangel(String navn, String beskrivelse, int pris){
        Mangel mangel = new Mangel(navn, beskrivelse, pris);
        mangel.setTilstandsRapportId(rapport.getId());
        rapport.tilfoejMangel(mangel);
    }

    public void removeSkade(Skade skade){
        rapport.removeSkade(skade);
    }

    public void removeMangel(int id){
        rapport.removeMangel(id);
    }



    public void opdaterTilstandsRapport(){
        tilstandsrapportRepo.update(rapport);
    }

    public Tilstandsrapport getRapport(){
        return rapport;
    }


}
