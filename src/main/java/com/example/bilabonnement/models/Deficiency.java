package com.example.bilabonnement.models;

public class Deficiency {

    private int id;
    private int agreementId;
    private String title;
    private String description;
    private int price;

    public Deficiency(int id, int agreementId, String title, String description, int price) {
        this.id = id;
        this.agreementId = agreementId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Deficiency(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public int getId() {
        return id;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Deficiency{" +
                "id=" + id +
                ", agreementId=" + agreementId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
