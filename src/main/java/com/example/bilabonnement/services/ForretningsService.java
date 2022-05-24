package com.example.bilabonnement.services;

import com.example.bilabonnement.models.Bil;
import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.repositories.CRUDInterface;

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

    private int count;
    public int udregnTotalPris(ArrayList<Lejeaftale> aftaler){

        int totalpris = 0;
        count = 0;

        for (Lejeaftale aftale : aftaler) {
            totalpris += aftale.getPrisoverslag().getTotalpris();
            count ++;
        }

        return totalpris;
    }

    public int getCount(){
        return count;
    }

}
