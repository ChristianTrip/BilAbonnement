package com.example.bilabonnement.services;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.priceEstimates.PriceEstimate;
import com.example.bilabonnement.models.subscriptions.Subscription;
import com.example.bilabonnement.repositories.*;
import com.example.bilabonnement.utility.CSVReader;
import com.example.bilabonnement.utility.CSVWriter;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaseAgreementService {


    private LeaseAgreementRepo leaseAgreementRepo = new LeaseAgreementRepo();
    private CustomerRepo customerRepo = new CustomerRepo();
    private CarRepo carRepo = new CarRepo();
    private SubscriptionRepo subscriptionRepo = new SubscriptionRepo();
    private PickupPlaceRepo pickupPlaceRepo = new PickupPlaceRepo();
    private PriceEstimateRepo priceEstimateRepo = new PriceEstimateRepo();
    private SurveyReportRepo surveyReportRepo = new SurveyReportRepo();
    private InjuryRepo injuryRepo = new InjuryRepo();
    private DeficiencyRepo deficiencyRepo = new DeficiencyRepo();


    private ArrayList<LeaseAgreement> nonAgreedLeases = new ArrayList<>();


    public LeaseAgreement getLeaseAgreementById(int id){
        LeaseAgreement leaseAgreement = leaseAgreementRepo.getSingleEntityById(id);
        
        for (SurveyReport rapport : surveyReportRepo.getAllEntities()) {
            if (rapport.getAgreementId() == id){
                leaseAgreement.setTilstandsrapport(rapport);
            }
        }
        return leaseAgreement;
    }

    public ArrayList<LeaseAgreement> getAllLeaseAgreements(){
        ArrayList<LeaseAgreement> agreements = new ArrayList<>();

        for (LeaseAgreement la: leaseAgreementRepo.getAllEntities()) {
            agreements.add(getLeaseAgreementById(la.getId()));
        }

        return agreements;
    }


    public ArrayList<LeaseAgreement> getAllActiveAgreements(ArrayList<LeaseAgreement> leaseAgremments){
        ArrayList<LeaseAgreement> agreements = new ArrayList<>();

        for (LeaseAgreement leaseAgreement : leaseAgremments) {
            if(leaseAgreement.getEndDate().isAfter(LocalDate.now())){
                agreements.add(leaseAgreement);
            }
        }
        return agreements;
    }

    public ArrayList<LeaseAgreement> getAllEndedAgreements(ArrayList<LeaseAgreement> leaseAgreements){
        ArrayList<LeaseAgreement> agreements = new ArrayList<>();

        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            if(leaseAgreement.getEndDate().isBefore(LocalDate.now())){
                agreements.add(leaseAgreement);
            }
        }
        return agreements;
    }

    public LeaseAgreement getNonAgreedLease(int index){
        return nonAgreedLeases.get(index);
    }


    public boolean addLeaseAgreementToDataBase(int index, Date startDate){
        LeaseAgreement leaseAgreement = getNonAgreedleases().get(index);
        leaseAgreement.setStartDate(startDate.toLocalDate());
            System.out.println("index number: " + index);

        if(leaseAgreementRepo.create(leaseAgreement)){
            new CSVWriter().removeLineFromAllFiles(index + 1);
            return true;
        }
        return false;
    }

    public ArrayList<LeaseAgreement> getNonAgreedleases(){
        ArrayList<LeaseAgreement> nonAgreedLeases = new ArrayList<>();
        CSVReader reader = CSVReader.getInstance();

        ArrayList<Customer> customers = reader.getCustomers();
        ArrayList<Car> cars = reader.getCars();
        ArrayList<Subscription> subscriptions = reader.getSubscriptions();
        ArrayList<PriceEstimate> priceEstimates = reader.getPriceEstimates();
        ArrayList<PickupPlace> pickupPlaces = reader.getPickupPlaces();

        // Lease agreement creation
        for (int i = 0; i < cars.size(); i++) {
            LeaseAgreement agreement = new LeaseAgreement(
                    customers.get(i),
                    cars.get(i),
                    null,
                    subscriptions.get(i),
                    priceEstimates.get(i),
                    pickupPlaces.get(i));

            nonAgreedLeases.add(agreement);
        }
        return nonAgreedLeases;
    }


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


}
