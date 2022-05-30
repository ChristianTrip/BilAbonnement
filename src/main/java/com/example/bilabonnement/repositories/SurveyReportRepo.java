package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyReportRepo implements CRUDInterface<SurveyReport>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(SurveyReport surveyReport) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO survey_reports(`agreement_id`) " +
                    "VALUES ('" + surveyReport.getAgreementId() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int reportId = getLastIndex();

            for (Deficiency deficiency : surveyReport.getDeficiencies()) {
                deficiency.setReportId(reportId);
                insertShortcoming(deficiency);
            }

            for (Injury injury : surveyReport.getInjuries()) {
                injury.setSurveyReportId(reportId);
                insertInjury(injury);
            }

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette tilstandsrapport i databasen");
        }

        return false;
    }


    @Override
    public SurveyReport getSingleEntityById(int id) {
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM survey_reports WHERE report_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int reportId = rs.getInt(1);
                int agreementId = rs.getInt(2);

                ArrayList<Deficiency> deficiencies = getShortcomings(reportId);
                ArrayList<Injury> injuries = getInjuries(reportId);

                return new SurveyReport(reportId, agreementId, deficiencies, injuries);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapport med id: " + id);
        }
        return null;
    }

    @Override
    public List<SurveyReport> getAllEntities() {

        ArrayList<SurveyReport> surveyReports = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM survey_reports;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int reportId = rs.getInt(1);

                surveyReports.add(getSingleEntityById(reportId));
            }
            return surveyReports;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde surveyReports");
        }
        return null;
    }

    @Override
    public boolean update(SurveyReport surveyReport) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE survey_reports " +
                            "SET report_id = " + surveyReport.getId() + ", " +
                            "agreement_id = " + surveyReport.getAgreementId() +
                            "WHERE report_id = " + surveyReport.getId();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM survey_reports WHERE report_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette tilstandsrapport med id: " + id);
            return false;
        }

        return true;
    }

    private int getLastIndex(){

        try {
            String sql = "SELECT LAST_INSERT_ID();";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    private boolean insertInjury(Injury injury){

        try{
            String sql = "INSERT IGNORE injuries(`report_id`, `title`, `description`, `price`) " +
                    "VALUES (" +
                    "'" + injury.getSurveyReportId() + "', " +
                    "'" + injury.getTitle() + "', " +
                    "'" + injury.getDescription() + "', " +
                    "'" + injury.getPrice() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette skade med tilstandsrapport Id " + injury.getSurveyReportId() + " i databasen");
        }
        return false;
    }


    private boolean insertShortcoming(Deficiency deficiency){

        try{
            String sql = "INSERT IGNORE deficiencies(`report_id`, `title`, `description`, `price`) " +
                    "VALUES (" +
                    "'" + deficiency.getReportId() + "', " +
                    "'" + deficiency.getTitle() + "', " +
                    "'" + deficiency.getDescription() + "', " +
                    "'" + deficiency.getPrice() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette mangel med tilstandsrapport Id " + deficiency.getReportId() + " i databasen");
        }
        return false;
    }


    private ArrayList<Injury> getInjuries(int surveyReportId) {

        ArrayList<Injury> injuries = new ArrayList<>();

        try {
            String sql = "SELECT * FROM injuries WHERE report_id = '" + surveyReportId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int injuryId = rs.getInt(1);
                int reportId = rs.getInt(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                int price = rs.getInt(5);

                injuries.add(new Injury(injuryId, reportId, title, description, price));
            }

            return injuries;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + surveyReportId);
        }
        return null;
    }

    private ArrayList<Deficiency> getShortcomings(int surveyReportId) {

        ArrayList<Deficiency> deficiencies = new ArrayList<>();

        try {
            String sql = "SELECT * FROM deficiencies WHERE report_id = '" + surveyReportId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int deficiencyId = rs.getInt(1);
                int reportId = rs.getInt(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                int price = rs.getInt(5);

                deficiencies.add(new Deficiency(deficiencyId, reportId, title, description, price));
            }

            return deficiencies;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + surveyReportId);
        }
        return null;
    }

}


