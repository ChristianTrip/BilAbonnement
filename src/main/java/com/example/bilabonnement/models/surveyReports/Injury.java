package com.example.bilabonnement.models.surveyReports;

public class Injury {

    private int id;
    private int surveyReportId;
    private String title;
    private String description;
    private int price;


    public Injury(int id, int surveyReportId, String title, String description, int price) {
        this.id = id;
        this.surveyReportId = surveyReportId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Injury(String title, String description, int price){
        this.title = title;
        this.description = description;
        this.price = price;
    }


    public void setSurveyReportId(int surveyReportId) {
        this.surveyReportId = surveyReportId;
    }

    public int getId() {
        return id;
    }

    public int getSurveyReportId() {
        return surveyReportId;
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
        return "Injury{" +
                "id=" + id +
                ", surveyReportId=" + surveyReportId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
