package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.repositories.BrugerRepo;
import com.example.bilabonnement.repositories.CRUDInterface;

import java.util.ArrayList;

public class DataregService {
    private CRUDInterface<Bruger> bRepo = new BrugerRepo();

    public ArrayList<Lejeaftale> seLejeaftaler(){
        return null;
    }

    public void opretLejeaftale(Lejeaftale lAftale){
        //do stuff
    }

    private String læscsv(){
        // Kunde
        // Bil
        // Abonnement
        // Tilstandsrapport
        // Prisoverslag
        // Afhentningssted
        return "";
    }

}
