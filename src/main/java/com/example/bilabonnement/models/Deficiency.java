package com.example.bilabonnement.models;

public class Deficiency {

    private int id;
    private int reportId;
    private String title;
    private String description;
    private int price;

    public Deficiency(int id, int reportId, String title, String description, int price) {
        this.id = id;
        this.reportId = reportId;
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

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getId() {
        return id;
    }

    public int getReportId() {
        return reportId;
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


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Deficiency{" +
                "id=" + id +
                ", agreementId=" + reportId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
