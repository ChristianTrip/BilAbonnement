package com.example.bilabonnement.utility;

import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.models.Customer;
import com.example.bilabonnement.models.PickupPlace;
import com.example.bilabonnement.models.priceEstimates.PriceEstimate;
import com.example.bilabonnement.models.subscriptions.LimitedSubscription;
import com.example.bilabonnement.models.subscriptions.Subscription;
import com.example.bilabonnement.models.subscriptions.UnlimitedSubscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class CSVReader {
    /*
    Vi antager at hver linje i vores csv-filer repræsenterer hvad der svarer til en unik Lejeaftale.
    Vi antager yderligere at den måde vi får dataen fra hjemmesiden(bilabonnement.dk) som kunden har indtastet
    er en csv-fil, fordi opgaven beskriver at de arbejder i Excel.
     */

    private File file;
    private Scanner scanner;

    private static final CSVReader INSTANCE = new CSVReader();

    private CSVReader(){}

    public static CSVReader getInstance(){
        return INSTANCE;
    }

    public ArrayList<Customer> getCustomers(){

        ArrayList<Customer> customers = new ArrayList<>();
        try{
            file = new File("src/main/resources/csvFiles/incomingCustomers.csv");
            scanner = new Scanner(file);
            
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                String firstName = attributes[0];
                String lastName = attributes[1];
                String address = attributes[2];
                String postalCode = attributes[3];
                String city = attributes[4];
                String email = attributes[5];
                String phone = (attributes[6]);
                String cpr = (attributes[7]);
                String regNumber = (attributes[8]);
                String accountNumber = (attributes[9]);

                customers.add(new Customer(firstName, lastName, address, postalCode, city, email, phone, cpr, regNumber, accountNumber));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return customers;
    }
    
    public ArrayList<Car> getCars(){
        ArrayList<Car> cars = new ArrayList<>();
        try{
            file = new File("src/main/resources/csvFiles/incomingCars.csv");
            scanner = new Scanner(file);

            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                
                String chassisNumber = attributes[0];
                String brand = attributes[1];
                String model = attributes[2];

                cars.add(new Car(chassisNumber, brand, model));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cars;
    }

    public ArrayList<PickupPlace> getPickupPlaces(){
        ArrayList<PickupPlace> pickupPlaces = new ArrayList<>();
        try{
            file = new File("src/main/resources/csvFiles/incomingPickupPlaces.csv");
            scanner = new Scanner(file);

            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                String address = attributes[0];
                String postalCode = (attributes[1]);
                String city = attributes[2];
                int deliveryCost = parseInt(attributes[3]);

                pickupPlaces.add(new PickupPlace(address, postalCode, city, deliveryCost));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return pickupPlaces;
    }

    public ArrayList<Subscription> getSubscriptions(){
        ArrayList<Subscription> subscriptions = new ArrayList<>();
        try{
            file = new File("src/main/resources/csvFiles/incomingSubscriptions.csv");
            scanner = new Scanner(file);

            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                int lenght = parseInt(attributes[0]);
                int lowDeductible = parseInt(attributes[1]);
                int standardColor = parseInt(attributes[2]);
                int deliveryInsurance = parseInt(attributes[3]);
                String type = attributes[4];
                boolean hasStandardColor = standardColor == 1;
                boolean haslowDeductible = lowDeductible == 1;
                boolean hasDeliveryInsurance = deliveryInsurance == 1;

                Subscription subscription;

                if(type.equals("limited")){
                    subscription = new LimitedSubscription(haslowDeductible, hasStandardColor);
                }
                else {
                    subscription = new UnlimitedSubscription(lenght, haslowDeductible, hasDeliveryInsurance, hasStandardColor);
                }
                subscriptions.add(subscription);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return subscriptions;
    }

    public ArrayList<PriceEstimate> getPriceEstimates(){
        ArrayList<PriceEstimate> priceEstimates = new ArrayList<>();
        try{
            file = new File("src/main/resources/csvFiles/incomingPriceEstimates.csv");
            scanner = new Scanner(file);

            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                int totalPrice = parseInt(attributes[0]);
                int subscriptionLength = parseInt(attributes[1]);
                int kmPerMonth = parseInt((attributes[2]));

                priceEstimates.add(new PriceEstimate(subscriptionLength, kmPerMonth, totalPrice));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return priceEstimates;
    }



    // Metode der skal bruges til CSVWriter - måske bruge modulus? BINGO!
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
