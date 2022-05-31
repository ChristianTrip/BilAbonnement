package com.example.bilabonnement.models.leaseAgreementElements;

public class PickupPlace {

    private int id;
    private int agreementId;
    private final String address;
    private final String postalCode;
    private final String city;
    private final int deliveryCost;


    public PickupPlace(int id, int agreementId, String address, String postalCode, String city, int deliveryCost) {
        this.id = id;
        this.agreementId = agreementId;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.deliveryCost = deliveryCost;
    }

    public PickupPlace(String address, String postalCode, String city, int deliveryCost) {
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.deliveryCost = deliveryCost;
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

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    @Override
    public String toString() {
        return "PickupPlace{" +
                "\nid = " + id +
                "\nagreementId = " + agreementId +
                "\naddress = '" + address + '\'' +
                "\npostalCode = '" + postalCode + '\'' +
                "\ncity = '" + city + '\'' +
                "\ndeliveryCost = " + deliveryCost +
                '}' +"\n";
    }
}
