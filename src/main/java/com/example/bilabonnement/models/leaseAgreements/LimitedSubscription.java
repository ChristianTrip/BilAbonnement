package com.example.bilabonnement.models.leaseAgreements;

public class LimitedSubscription extends Subscription {



    public LimitedSubscription(int id, int agreementId, boolean lowDeductible, boolean standardColor) {
        super(id, agreementId, lowDeductible, standardColor);
        super.lengthInMonths = 4;
    }


    public LimitedSubscription(boolean lowDeductible, boolean standardColor){
        super.lowDeductible = lowDeductible;
        super.standardColor = standardColor;
        super.lengthInMonths = 4;
    }

    @Override
    public String toString() {
        return "LimitedSubscription{" +
                "\nid = " + id +
                "\nagreementId = " + agreementId +
                "\nlowDeductible = " + lowDeductible +
                "\nsubscriptionLength = " + lengthInMonths +
                '}' + "\n";
    }
}
