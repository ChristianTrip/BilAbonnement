package com.example.bilabonnement.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

    public void fjernLinje(int linje){
        CSVReader reader = new CSVReader();
        String filSti;
        ArrayList<String> alleStrings;

        //KUNDE
        //Læs alt ned på en arraylist
        filSti = "src/main/resources/csv/kunde.csv";
        reader.setSc(filSti);
        alleStrings = reader.læsAlt();
        //Fjern linje fra arraylist
        alleStrings.remove(linje);
        //Skriv arraylisten ind i csv igen
        try{
            BufferedWriter fr = new BufferedWriter(new FileWriter(filSti));

            for (String s : alleStrings) {
                fr.write(s);
                fr.newLine();
            }

            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        //BIL
        filSti = "src/main/resources/csv/bil.csv";
        reader.setSc(filSti);
        alleStrings = reader.læsAlt();
        alleStrings.remove(linje);

        try{
            BufferedWriter fr = new BufferedWriter(new FileWriter(filSti));

            for (String s : alleStrings) {
                fr.write(s);
                fr.newLine();
            }

            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        //ABONNEMENT
        filSti = "src/main/resources/csv/abonnement.csv";
        reader.setSc(filSti);
        alleStrings = reader.læsAlt();
        alleStrings.remove(linje);

        try{
            BufferedWriter fr = new BufferedWriter(new FileWriter(filSti));

            for (String s : alleStrings) {
                fr.write(s);
                fr.newLine();
            }

            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        //PRISOVERSLAG
        filSti = "src/main/resources/csv/prisoverslag.csv";
        reader.setSc(filSti);
        alleStrings = reader.læsAlt();
        alleStrings.remove(linje);

        try{
            BufferedWriter fr = new BufferedWriter(new FileWriter(filSti));

            for (String s : alleStrings) {
                fr.write(s);
                fr.newLine();
            }

            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        //AFHENTNINGSSTED
        filSti = "src/main/resources/csv/afhentningssted.csv";
        reader.setSc(filSti);
        alleStrings = reader.læsAlt();
        alleStrings.remove(linje);

        try{
            BufferedWriter fr = new BufferedWriter(new FileWriter(filSti));

            for (String s : alleStrings) {
                fr.write(s);
                fr.newLine();
            }

            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

}
