package com.example.bilabonnement.models.subscriptions;

public abstract class Subscription {

    protected int id;
    protected int agreementId;
    protected boolean lowDeductible;
    protected boolean deliveryInsurance;
    protected boolean standardColor;
    protected int lengthInMonths;

    public Subscription(int id, int agreementId, boolean lowDeductible, boolean standardColor) {
        this.id = id;
        this.agreementId = agreementId;
        this.lowDeductible = lowDeductible;
        this.standardColor = standardColor;
    }

    public Subscription(){}


    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public int getLengthInMonths() {
        return lengthInMonths;
    }

    public int getId() {
        return id;
    }

    public boolean hasLowDeductible() {
        return lowDeductible;
    }

    public boolean hasDeliveryInsurance() {
        return deliveryInsurance;
    }

    public boolean hasStandardColor() {
        return standardColor;
    }
}
