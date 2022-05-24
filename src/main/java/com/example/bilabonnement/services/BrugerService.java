package com.example.bilabonnement.services;

import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.repositories.BrugerRepo;

import java.util.ArrayList;

public class BrugerService {



    public Bruger validateUserinfo(String brugernavn, String adgangskode) {

        ArrayList<Bruger> brugere = (ArrayList<Bruger>) new BrugerRepo().getAllEntities();

        for (Bruger bruger : brugere) {
            if(bruger.getNavn().equals(brugernavn) && bruger.getAdgangskode().equals(adgangskode)) {
                return bruger;
            }
        }
        return null;
    }

    public BrugerType determineBrugertype(String brugernavn, String password) {

        Bruger bruger = validateUserinfo(brugernavn, password);

        if(bruger == null) {
            return BrugerType.IKKEBRUGER;
        }

        return bruger.getBrugerType();
    }

}
