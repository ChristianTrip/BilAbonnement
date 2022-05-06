package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Bil;
import com.example.bilabonnement.models.Kunde;
import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.abonnementer.UnlimitedAbonnement;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.repositories.BrugerRepo;
import com.example.bilabonnement.repositories.CRUDInterface;
import com.example.bilabonnement.utility.CSVReader;
import org.springframework.http.server.DelegatingServerHttpResponse;

import java.util.ArrayList;
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
        CSVReader reader = new CSVReader();
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


                Bil bil = new Bil(stelnummer,navn,model);
                liste.add(bil);

            }

        }catch(Exception e){
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
                Abonnement abonnement = null;


                //lav nyt abonnement og add det
                if(abonnementType.equals("limited"))
                    abonnement = new LimitedAbonnement(isSelvrisiko);
                else if(abonnementType.equals("unlimited"))
                    abonnement = new UnlimitedAbonnement(lejeperiode, isSelvrisiko, isAflevering);

                liste.add(abonnement);

            }

        }catch(Exception e){
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


                Prisoverslag pOverslag = new Prisoverslag(totalPris,abonnementsLængde);
                liste.add(pOverslag);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        // Afhentningssted


        return liste;
    }

    public static void main(String[] args) {
        System.out.println(DataregService.læscsv());
    }

}
