package com.example.bilabonnement.models;

import java.util.ArrayList;

public class SurveyReport {

    private int id;
    private int agreementId;
    private int totalPrice;
    private ArrayList<Deficiency> deficiencies;
    private ArrayList<Injury> injuries;

    public SurveyReport(int id, int agreementId, ArrayList<Deficiency> deficiencies, ArrayList<Injury> injuries) {
        this.id = id;
        this.agreementId = agreementId;
        this.deficiencies = deficiencies;
        this.injuries = injuries;
        calculateTotalPrice();
    }

    public SurveyReport(int agreementId) {
        this.agreementId = agreementId;
        this.deficiencies = new ArrayList<>();
        this.injuries = new ArrayList<>();
    }

    public SurveyReport(int id, int agreementId) {
        this.id = id;
        this.agreementId = agreementId;
        this.deficiencies = new ArrayList<>();
        this.injuries = new ArrayList<>();
    }

    public SurveyReport(ArrayList<Deficiency> deficiencies, ArrayList<Injury> injuries){
        this.deficiencies = deficiencies;
        this.injuries = injuries;
        calculateTotalPrice();
    }

    private void calculateTotalPrice(){
        totalPrice = 0;
        for (Deficiency deficiency : deficiencies) {
            totalPrice += deficiency.getPrice();
        }
        for (Injury injury : injuries) {
            totalPrice += injury.getPrice();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public void tilfoejMangel(Deficiency deficiency){
        deficiencies.add(deficiency);
    }

    public void tilfoejSkade(Injury injury){
        injuries.add(injury);
    }

    public void removeMangel(int index){
        this.deficiencies.remove(index - 1);
    }

    public void removeSkade(Injury injury){
        this.injuries.remove(injury);
    }

    public int getId() {
        return id;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public void setShortcomings(ArrayList<Deficiency> deficiencies) {
        this.deficiencies = deficiencies;
    }

    public void setInjuries(ArrayList<Injury> injuries) {
        this.injuries = injuries;
    }

    public ArrayList<Deficiency> getDeficiencies() {
        return deficiencies;
    }

    public ArrayList<Injury> getInjuries() {
        return injuries;
    }

    @Override
    public String toString() {
        return "SurveyReport{" +
                "\nid = " + id +
                "\nagreementId = " + agreementId +
                "\ntotalPrice = " + totalPrice +
                "\ndeficiencies = " + deficiencies +
                "\ninjuries = " + injuries + "\n" +
                "\n";
    }
}
