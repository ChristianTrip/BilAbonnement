package com.example.bilabonnement.models;

public class Customer {

    private int agreementId;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String email;
    private String phoneNumber;
    private final String cpr;
    private String regNumber;
    private String accountNumber;

    public Customer(String firstName, String lastName, String address, String postalCode, String city, String email, String phoneNumber, String cpr, String regNumber, String accountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpr = cpr;
        this.regNumber = regNumber;
        this.accountNumber = accountNumber;
    }


    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCpr() {
        return cpr;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "\nfirstName = '" + firstName + '\'' +
                "\nlastName = '" + lastName + '\'' +
                "\nadresse = '" + address + '\'' +
                "\npostalCode = " + postalCode +
                "\ncity = '" + city + '\'' +
                "\nemail = '" + email + '\'' +
                "\nphoneNumber = " + phoneNumber +
                "\ncpr = " + cpr +
                "\nregNumber = " + regNumber +
                "\naccountNumber = " + accountNumber +
                '}' + "\n";
    }
}
