package com.example.bilabonnement.models.leaseAgreementElements;

public class PriceEstimate {

    private int id;
    private int agreementId;
    private int subscriptionLength;
    private int kmPerMonth;
    private int totalPrice;

    public PriceEstimate(int id, int agreementId, int abonnementslaengde, int kmPerMonth, int totalPrice) {
        this.id = id;
        this.agreementId = agreementId;
        this.subscriptionLength = abonnementslaengde;
        this.kmPerMonth = kmPerMonth;
        this.totalPrice = totalPrice;
    }

    public PriceEstimate(int abonnementslaengde, int kmPerMonth, int totalPrice){
        this.subscriptionLength = abonnementslaengde;
        this.kmPerMonth = kmPerMonth;
        this.totalPrice = totalPrice;
    }


    public void setSubscriptionLength(int subscriptionLength) {
        this.subscriptionLength = subscriptionLength;
    }

    public int getId() {
        return id;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getSubscriptionLength() {
        return subscriptionLength;
    }

    public int getkmPerMonth() {
        return kmPerMonth;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "PriceEstimate{" +
                "\nid = " + id +
                "\nagreementId = " + agreementId +
                "\nsubscriptionLength = " + subscriptionLength +
                "\nkmPerMonth = " + kmPerMonth +
                "\ntotalPrice = " + totalPrice +
                '}' + "\n";
    }
}
