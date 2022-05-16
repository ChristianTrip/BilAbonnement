package com.example.bilabonnement.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    /*
    Vi antager at hver linje i vores csv-filer repræsenterer hvad der svarer til en unik Lejeaftale.
    Vi antager yderligere at den måde vi får dataen fra hjemmesiden(bilabonnement.dk) som kunden har indtastet
    er en csv-fil, fordi opgaven beskriver at de arbejder i Excel.
     */

    private File file;
    private Scanner sc;


    public CSVReader() {

    }

    // Metode der skal bruges til CSVWriter - måske bruge modulus? BINGO!
    public ArrayList<String> læsAlt(){
        ArrayList<String> liste = new ArrayList<>();


        while(sc.hasNext()) {

            String currLine = sc.nextLine();
            liste.add(currLine);

        }
        return liste;
    }

    public ArrayList<String> læsSpecifikLinje(){
        ArrayList<String> liste = new ArrayList<>();

        while(sc.hasNext()) {

            String currLine = sc.nextLine();
            String[] split = currLine.split(",");

            liste.addAll(Arrays.asList(split));

        }
        return liste;
    }


    public Scanner getSc() {
        return sc;
    }

    public void setSc(String filePath) {
        file = new File(filePath);

        try{
            this.sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
