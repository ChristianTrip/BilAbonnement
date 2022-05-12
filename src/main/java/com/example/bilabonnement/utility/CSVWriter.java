package com.example.bilabonnement.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CSVWriter {

    public void fjernLinje(int linje){
        CSVReader reader = new CSVReader();
        ArrayList<String> alleStrings;
        int count = 0;

        //Kunde
        //Læs alt ned på en arraylist
        reader.setSc("src/main/resources/csv/kunde.csv");
        alleStrings = reader.læsAlt();
        System.out.println(alleStrings);
        //fjern række fra arraylisten. Er der ik en bedre måde at gøre det på?
        for (int i = 0; i < alleStrings.size()+1; i++) {
            if(i % 10 == 0 && i != 0){
                count++;
                if(count == linje){
                    for (int j = 0; j < 10; j++) {
                        System.out.println(j +""+i+"");
                        alleStrings.remove(i);
                    }
                }
                System.out.println(count);
            }
        }

        //Det skal lige se lidt pænere ud når det bliver sat ind i filen igen :) og det samme skal self gøres for alle andre filer ;)))))
        try{
            //FileWriter fr = new FileWriter("src/main/resources/csv/kunde.csv");
            BufferedWriter fr = new BufferedWriter(new FileWriter("src/main/resources/csv/kunde.csv"));
            String collect = alleStrings.stream().collect(Collectors.joining(","));

            fr.write(collect);
            fr.close();
        }catch(IOException e){
            e.printStackTrace();
        }



    }

}
