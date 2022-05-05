package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Kunde;
import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.repositories.BrugerRepo;
import com.example.bilabonnement.repositories.CRUDInterface;
import com.example.bilabonnement.utility.csvReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class DataregService {
    private CRUDInterface<Bruger> bRepo = new BrugerRepo();

    public ArrayList<Lejeaftale> seLejeaftaler(){
        læscsv();
        return null;
    }

    public void opretLejeaftale(Lejeaftale lAftale){
        //do stuff
    }

    public static ArrayList<Object> læscsv(){
        csvReader reader = new csvReader();
        Scanner currSc;
        ArrayList<Object> liste = new ArrayList<>();

        // Kunde
        try{
            reader.setSc("src/main/resources/csv/kunde.csv");
            currSc = reader.getSc();
            currSc.nextLine();

            while(currSc.hasNext()){
                String currLine = currSc.nextLine();
                String[] split = currLine.split(",");

                String fornavn = split[0];
                String efternavn = split[1];
                String adresse = split[2];
                int postNummer = parseInt(split[3]);
                String by = split[4];
                String email = split[5];
                int mobil = parseInt(split[6]);
                int cpr = parseInt(split[7]);
                int regNummer = parseInt(split[8]);
                int kontoNummer = parseInt(split[9]);

                Kunde kunde = new Kunde(fornavn,efternavn,adresse,postNummer,by,email,mobil,cpr,regNummer,kontoNummer);
                liste.add(kunde);

            }

        }catch(Exception e){
                e.printStackTrace();
        }

        // Bil
        // Abonnement
        // Prisoverslag
        // Afhentningssted
        return liste;
    }


}
