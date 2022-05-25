package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;
import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.repositories.TilstandsRapportRepo;

import java.util.ArrayList;

public class SkaderegService {

    private LejeaftaleRepo lejeaftaleRepo = new LejeaftaleRepo();
    private TilstandsRapportRepo tilstandsRapportRepo = new TilstandsRapportRepo();
    private Tilstandsrapport rapport;


    public void setRapport(int lejeaftaleId){
        rapport = tilstandsRapportRepo.getSingleEntityById(lejeaftaleId);
    }

    public ArrayList<Lejeaftale> getLejeaftaler(){
        return (ArrayList<Lejeaftale>) lejeaftaleRepo.getAllEntities();
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
        tilstandsRapportRepo.update(rapport);
    }

    public Tilstandsrapport getRapport(){
        return rapport;
    }


}
