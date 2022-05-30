package com.example.bilabonnement.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

    private CSVReader reader;
    private BufferedWriter bufferedWriter;
    private File file;
    private ArrayList<String> allLines;

    public void removeLineFromAllFiles(int lineIndex){
        reader = CSVReader.getInstance();

        //Customer
        //Læs alt ned på en arrayliste
        file = new File("src/main/resources/csvFiles/incomingCustomers.csv");
        allLines = reader.getAllLinesFromFile(file);
        //Fjern lineIndex fra arraylist
        allLines.remove(lineIndex);
        //Skriv arraylisten ind i csv igen
        writeToFile(file);

        //Car
        file = new File("src/main/resources/csvFiles/incomingCars.csv");
        allLines = reader.getAllLinesFromFile(file);
        allLines.remove(lineIndex);

        writeToFile(file);

        //Subscription
        file = new File("src/main/resources/csvFiles/incomingSubscriptions.csv");
        allLines = reader.getAllLinesFromFile(file);
        allLines.remove(lineIndex);

        writeToFile(file);

        //PriceEstimate
        file = new File("src/main/resources/csvFiles/incomingPriceEstimates.csv");
        allLines = reader.getAllLinesFromFile(file);
        allLines.remove(lineIndex);

        writeToFile(file);

        //PickupPlace
        file = new File("src/main/resources/csvFiles/incomingPickupPlaces.csv");
        allLines = reader.getAllLinesFromFile(file);
        allLines.remove(lineIndex);

        writeToFile(file);

    }

    private void writeToFile(File file){

        try{
            bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (String line : allLines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
