package com.example.bilabonnement.models.leaseAgreementElements;

import com.example.bilabonnement.models.surveyReportElements.SurveyReport;

import java.time.LocalDate;

public class LeaseAgreement {

    private int id;
    private Customer customer;
    private Car car;
    private SurveyReport surveyReport; //= new Tilstandsrapport();
    private Subscription subscription;
    private PriceEstimate priceEstimate;
    private PickupPlace pickupPlace;
    private LocalDate approvalDate;
    private LocalDate startDate;
    private LocalDate endDate;


    public LeaseAgreement(int id, Customer customer, Car car, SurveyReport surveyReport, Subscription subscription, PriceEstimate priceEstimate, PickupPlace pickupPlace, LocalDate approvalDate, LocalDate startDate) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.surveyReport = surveyReport;
        this.subscription = subscription;
        this.priceEstimate = priceEstimate;
        this.priceEstimate.setSubscriptionLength(subscription.getLengthInMonths());
        this.pickupPlace = pickupPlace;
        this.approvalDate = approvalDate;
        this.startDate = startDate;
        setEndDate();
    }

    // constructor til at oprette en lejeaftale i databasen
    public LeaseAgreement(Customer customer, Car car, SurveyReport surveyReport, Subscription subscription, PriceEstimate priceEstimate, PickupPlace pickupPlace) {
        this.customer = customer;
        this.car = car;
        this.surveyReport = surveyReport;
        this.subscription = subscription;
        this.priceEstimate = priceEstimate;
        this.priceEstimate.setSubscriptionLength(subscription.getLengthInMonths());
        this.pickupPlace = pickupPlace;
        this.approvalDate = LocalDate.now();

    }

    // isActive() is used in leaseagreement.html to determine if a lease is active or not
    public boolean isActive(){
        return this.endDate.isAfter(LocalDate.now());
    }

    private void setEndDate(){
        int subscriptionLength = subscription.getLengthInMonths();
        endDate = startDate.plusMonths(subscriptionLength);
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public SurveyReport getSurveyReport() {
        return surveyReport;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public PriceEstimate getPriceEstimate() {
        return priceEstimate;
    }

    public PickupPlace getPickupPlace() {
        return pickupPlace;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    //Used in leaseagreements.html(th:if)
    public boolean isSurveyReportEmpty() {
        return this.surveyReport.getInjuries().isEmpty() && this.surveyReport.getDeficiencies().isEmpty();
    }

    public void setTilstandsrapport(SurveyReport surveyReport) {
        this.surveyReport = surveyReport;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "LeaseAgreement{" +
                "\nid = " + id +
                "\ncustomer = " + customer +
                "\ncar = " + car +
                "\nsurveyReport = " + surveyReport +
                "\nsubscription = " + subscription +
                "\npriceEstimate = " + priceEstimate +
                "\npickupPlace = " + pickupPlace +
                "\napprovalDate = " + approvalDate + "\n" +
                '}';
    }
}
