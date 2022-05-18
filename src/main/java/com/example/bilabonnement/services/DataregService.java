package com.example.bilabonnement.services;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.abonnementer.UnlimitedAbonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.utility.CSVReader;
import com.example.bilabonnement.utility.CSVWriter;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class DataregService {

    private LejeaftaleRepo lejeaftaleRepo = new LejeaftaleRepo();
    private ArrayList<Lejeaftale> liste = new ArrayList<>();

    public ArrayList<Lejeaftale> seAlleLejeaftaler(){
        return liste;
    }

    public void opretLejeaftale(){
        //do stuff - opret Lejeaftale ud fra objecter fra læscsv()
        ArrayList<Lejeaftale> objListe = læscsv();

        for (Object o : objListe) {
            if(!testValidate(o))
                return;

        }


    }
    // man skal kunne vælge hvilken lejeaftale man gerne vil godkende
    // når man har godkendt den valgte lejeaftale skal den fjernes fra csv-filerne


    public Lejeaftale vælgLejeaftale(int index){
        return liste.get(index);
    }

    public void skrivcsv(){

    }

    public boolean addLejeaftaleToDB(int index){
        if(lejeaftaleRepo.create(liste.get(index))){
            new CSVWriter().fjernLinje(index + 1);
            return true;
        }
        return false;
    }

    public ArrayList<Lejeaftale> læscsv(){
        liste.clear();
        CSVReader reader = new CSVReader();
        Scanner currSc;
        ArrayList<Kunde> kundeListe = new ArrayList<>();
        ArrayList<Bil> bilListe = new ArrayList<>();
        ArrayList<Abonnement> abonnementListe = new ArrayList<>();
        ArrayList<Prisoverslag> pOverslagListe = new ArrayList<>();
        ArrayList<AfhentningsSted> afhentningsStedsListe = new ArrayList<>();

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
                String postNummer = split[3];
                String by = split[4];
                String email = split[5];
                String mobil = (split[6]);
                String cpr = (split[7]);
                String regNummer = (split[8]);
                String kontoNummer = (split[9]);

                kundeListe.add(new Kunde(fornavn,efternavn,adresse,postNummer,by,email,mobil,cpr,regNummer,kontoNummer));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Bil
        try{
            reader.setSc("src/main/resources/csv/bil.csv");
            currSc = reader.getSc();
            currSc.nextLine();

            while(currSc.hasNext()){
                String currLine = currSc.nextLine();
                String[] split = currLine.split(",");

                String stelnummer = split[0];
                String navn = split[1];
                String model = split[2];

                bilListe.add(new Bil(stelnummer,navn,model));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Abonnement
        try{
            reader.setSc("src/main/resources/csv/abonnement.csv");
            currSc = reader.getSc();
            currSc.nextLine();

            while(currSc.hasNext()){
                String currLine = currSc.nextLine();
                String[] split = currLine.split(",");

                int lejeperiode = parseInt(split[0]);
                int selvrisiko = parseInt(split[1]);
                int afleveringsfors = parseInt(split[2]);
                String abonnementType = split[3];
                boolean isSelvrisiko = selvrisiko == 1;
                boolean isAflevering = afleveringsfors == 1;

                //lav nyt abonnement og add det
                if(abonnementType.equals("limited"))
                    abonnementListe.add(new LimitedAbonnement(isSelvrisiko));
                else if(abonnementType.equals("unlimited"))
                    abonnementListe.add(new UnlimitedAbonnement(lejeperiode, isSelvrisiko, isAflevering));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Prisoverslag
        try{
            reader.setSc("src/main/resources/csv/prisoverslag.csv");
            currSc = reader.getSc();
            currSc.nextLine();

            while(currSc.hasNext()){
                String currLine = currSc.nextLine();
                String[] split = currLine.split(",");

                int totalPris = parseInt(split[0]);
                int abonnementsLængde = parseInt(split[1]);

                pOverslagListe.add(new Prisoverslag(totalPris,abonnementsLængde));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Afhentningssted
        try{
            reader.setSc("src/main/resources/csv/afhentningssted.csv");
            currSc = reader.getSc();
            currSc.nextLine();

            while(currSc.hasNext()){
                String currLine = currSc.nextLine();
                String[] split = currLine.split(",");

                String adresse = split[0];
                String postnummer = (split[1]);
                String by = split[2];
                int levering = parseInt(split[3]);

                afhentningsStedsListe.add(new AfhentningsSted(adresse,postnummer,by,levering));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Lejeaftale creation
        for (int i = 0; i < bilListe.size(); i++) {
            liste.add(new Lejeaftale(kundeListe.get(i), bilListe.get(i), null, abonnementListe.get(i), pOverslagListe.get(i), afhentningsStedsListe.get(i)));
        }
        return liste;
    }

    //lav metode der validerer objecter inden LegeaftaleRepo().create() bliver kaldt;


    public boolean testValidate(Object o){
        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(o) == null) {
                    System.out.println(o.getClass() + " contains a null field");
                    return false;
                }
            }
        }
        catch(IllegalAccessException | IllegalArgumentException e){
            e.printStackTrace();
        }
        return true;
    }



    public void run(){
        //System.out.println(læscsv());
        //Bil bil = new Bil("ZW0000069KL","null","null");
        //AfhentningsSted as = new AfhentningsSted("yes","4500",null,0);
        //System.out.println(testValidate(as));

        System.out.println(læscsv());



        //System.out.println(cr.læsAlt());



        /*
        for (Lejeaftale o : læscsv()) {
            System.out.println(o.getAbonnement());
        }

        */
    }

    public static void main(String[] args) {
        new DataregService().run();
    }
}
