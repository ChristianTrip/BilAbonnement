package com.example.bilabonnement.services;

import com.example.bilabonnement.models.leaseAgreementElements.LeaseAgreement;

import java.time.LocalDate;
import java.util.ArrayList;

public class BusinessService {

    public int getTotalPrice(ArrayList<LeaseAgreement> leaseAgreements){

        int totalPrice = 0;
        for (LeaseAgreement agreement : leaseAgreements) {
            totalPrice += agreement.getPriceEstimate().getTotalPrice();
        }
        return totalPrice;
    }

    public int getNumberOfRentedOutCars(ArrayList<LeaseAgreement> leaseAgreements){

        LocalDate currentTime = LocalDate.now();
        int count = 0;
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            if(leaseAgreement.getStartDate().isBefore(currentTime) && leaseAgreement.getEndDate().isAfter(currentTime))
                count++;
        }
        return count;
    }

}
