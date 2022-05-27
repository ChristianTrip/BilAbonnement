package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Bil;
import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.repositories.CRUDInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class ForretningsService {
    //private CRUDInterface<Lejeaftale> lejeRepo = new LejeaftaleRepo();


    public ForretningsService() {
    }

    public ArrayList<Bil> seUdlejedeBiler(){
        return null;
    }

    public void seTotalPris(){
        System.out.println(udregnTotalPris(new ArrayList<Lejeaftale>()));
    }

    public int udregnTotalPris(ArrayList<Lejeaftale> aftaler){

        int totalpris = 0;

        for (Lejeaftale aftale : aftaler) {
            totalpris += aftale.getPrisoverslag().getTotalpris();
        }

        return totalpris;
    }


    public int getRentedCars(ArrayList<Lejeaftale> leaseAgreements){
        LocalDate currTime = LocalDate.now();
        int count = 0;
        for (Lejeaftale leaseAgreement : leaseAgreements) {
            if(leaseAgreement.getStartDato().isBefore(currTime) && leaseAgreement.getSlutDato().isAfter(currTime))
                count++;
        }

        return count;
    }

}
