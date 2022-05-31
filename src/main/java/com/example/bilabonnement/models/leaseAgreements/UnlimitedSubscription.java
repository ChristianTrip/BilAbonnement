package com.example.bilabonnement.models.leaseAgreements;

public class UnlimitedSubscription extends Subscription {


    public UnlimitedSubscription(int id, int agreementId, int subscriptionLength, boolean lowDeductible, boolean deliveryInsurance, boolean standardColor) {
        super(id,agreementId, lowDeductible, standardColor);
        super.lengthInMonths = subscriptionLength;
        super.deliveryInsurance = deliveryInsurance;
    }

    public UnlimitedSubscription(int lengthInMonths, boolean lowDeductible, boolean deliveryInsurance, boolean standardColor){
        super.lengthInMonths = lengthInMonths;
        super.lowDeductible = lowDeductible;
        super.deliveryInsurance = deliveryInsurance;
        super.standardColor = standardColor;
    }

    @Override
    public String toString() {
        return "UnlimitedSubscription{" +
                "\nid = " + id +
                "\nagreementId = " + agreementId +
                "\nlowDeductible = " + lowDeductible +
                "\nsubscriptionLength = " + lengthInMonths +
                "\ndeliveryInsurance = " + deliveryInsurance +
                '}' + "\n";
    }
}
