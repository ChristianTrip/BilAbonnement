package com.example.bilabonnement.services;

import com.example.bilabonnement.models.LeaseAgreement;
import com.example.bilabonnement.models.SurveyReport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SurveyReportServiceTest {

    @Test
    void isInjuryValid(){
        //Arrange
        var srService = new SurveyReportService();
        var laService = new LeaseAgreementService();
        LeaseAgreement la = laService.getLeaseAgreementById(3);
        srService.setSurveyReport(la.getSurveyReport());
        boolean expectedInjuryWithEmptyTitle = false;
        boolean expectedValidInjury = true;
        boolean expectedInjuryWithEmptyDesc = false;
        boolean expectedInjuryWithEmptyPrice = false;
        boolean expectedInjuryWithEverythingEmpty = false;

        //Act
        boolean injuryWithoutTitle = srService.addInjury("","big hole in the roof","500");
        boolean validInjury = srService.addInjury("wreckage","big hole in the roof","500");
        boolean injuryWithoutDesc = srService.addInjury("wreckage","","500");
        boolean injuryWithoutPrice = srService.addInjury("wreckage","big hole in the roof","");
        boolean injuryWithEverythingEmpty = srService.addInjury("","","");

        //Assert
        assertEquals(expectedInjuryWithEmptyTitle,injuryWithoutTitle);
        assertEquals(expectedValidInjury,validInjury);
        assertEquals(expectedInjuryWithEmptyDesc,injuryWithoutDesc);
        assertEquals(expectedInjuryWithEmptyPrice,injuryWithoutPrice);
        assertEquals(expectedInjuryWithEverythingEmpty,injuryWithEverythingEmpty);



    }

}