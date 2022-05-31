package com.example.bilabonnement.services;

import com.example.bilabonnement.models.leaseAgreementElements.*;
import com.example.bilabonnement.models.surveyReportElements.SurveyReport;
import com.example.bilabonnement.repositories.*;
import com.example.bilabonnement.repositories.NonAgreedLeasesRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaseAgreementService {

    private LeaseAgreementRepo leaseAgreementRepo = new LeaseAgreementRepo();
    private SurveyReportRepo surveyReportRepo = new SurveyReportRepo();


    public LeaseAgreement getLeaseAgreementById(int id){
        LeaseAgreement leaseAgreement = leaseAgreementRepo.getSingleEntityById(id);
        
        for (SurveyReport rapport : surveyReportRepo.getAllEntities()) {
            if (rapport.getAgreementId() == id){
                leaseAgreement.setTilstandsrapport(rapport);
            }
        }
        return leaseAgreement;
    }

    public ArrayList<LeaseAgreement> getAllAgreedLeases(){
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

    public boolean addLeaseAgreementToDatabase(int index, Date startDate){
        LeaseAgreement leaseAgreement = getNonAgreedLeases().get(index);
        leaseAgreement.setStartDate(startDate.toLocalDate());

        if(leaseAgreementRepo.create(leaseAgreement)){
            NonAgreedLeasesRepo.getInstance().removeLineFromAllFiles(index + 1);
            return true;
        }
        return false;
    }

    public boolean removeLeaseAgreementFromCsv(int index){
        try{
            NonAgreedLeasesRepo.getInstance().removeLineFromAllFiles(index + 1);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<LeaseAgreement> getNonAgreedLeases(){
        ArrayList<LeaseAgreement> nonAgreedLeases = new ArrayList<>();
        NonAgreedLeasesRepo reader = NonAgreedLeasesRepo.getInstance();

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

}
