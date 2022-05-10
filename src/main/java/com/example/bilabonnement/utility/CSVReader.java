package com.example.bilabonnement.utility;

import com.example.bilabonnement.models.Lejeaftale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVReader {

    private File file;
    private Scanner sc;


    public CSVReader() {

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
