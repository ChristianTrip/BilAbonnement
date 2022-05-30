package com.example.bilabonnement.models;

public class Car {

    private int id;
    private int agreementId;
    private final String chassisNumber;
    private String brand;
    private String model;

    // Constructor for retrieving an object from the database
    public Car(int id, int agreementId, String chassisNumber, String brand, String model) {
        this.id = id;
        this.agreementId = agreementId;
        this.chassisNumber = chassisNumber;
        this.brand = brand;
        this.model = model;
    }

    // Constructor for inserting an object to the database table
    public Car(String chassisNumber, String brand, String model) {
        this.chassisNumber = chassisNumber;
        this.brand = brand;
        this.model = model;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public String getChassisNumber() {
        return chassisNumber.toUpperCase();
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" +
                "\nchassisNumber = '" + chassisNumber + '\'' +
                "\nagreementId = " + agreementId +
                "\nbrand = '" + brand + '\'' +
                "\nmodel = '" + model + '\'' +
                '}' + "\n";
    }
}
