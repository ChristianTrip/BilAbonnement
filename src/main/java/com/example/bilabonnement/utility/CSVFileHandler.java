package com.example.bilabonnement.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVFileHandler {

    private File file;
    private Scanner scanner;
    private BufferedWriter writer;

    private static final CSVFileHandler INSTANCE = new CSVFileHandler();

    private CSVFileHandler() {}


    public static CSVFileHandler getInstance() {
        return INSTANCE;
    }

    public ArrayList<String> getAllLinesFromFile(File file){

        ArrayList<String> list = new ArrayList<>();
        try{
            this.scanner = new Scanner(file);

            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                list.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

}
