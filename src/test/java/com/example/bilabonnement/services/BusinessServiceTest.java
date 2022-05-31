package com.example.bilabonnement.services;

import com.example.bilabonnement.models.leaseAgreements.LeaseAgreement;
import com.example.bilabonnement.repositories.LeaseAgreementRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


class BusinessServiceTest {

    @Test
    void isTotalPriceCorrect(){

        //Arrange
        var bService = new BusinessService();
        var laRepo = new LeaseAgreementRepo();
        ArrayList<LeaseAgreement> agreements = (ArrayList<LeaseAgreement>) laRepo.getAllEntities();
        int expectedTotalPrice = 23498;

        //Act
        int result = bService.getTotalPrice(agreements);

        //Assert
        assertEquals(expectedTotalPrice,result);

    }

}