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
            String sql = "INSERT INTO tilstandsrapporter(`lejeaftale_id`) " +
                    "VALUES ('" + surveyReport.getAgreementId() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int tilstandsrapportId = getLastIndex();

            for (Deficiency deficiency : surveyReport.getDeficiencies()) {
                deficiency.setAgreementId(tilstandsrapportId);
                insertShortcoming(deficiency);
            }

            for (Injury injury : surveyReport.getInjuries()) {
                injury.setSurveyReportId(tilstandsrapportId);
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
            String sql = "SELECT * FROM tilstandsrapporter WHERE tilstandsrapport_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);

                ArrayList<Deficiency> deficiencies = getShortcomings(id);
                ArrayList<Injury> injuries = getInjuries(id);

                return new SurveyReport(tilstandsrapport_id, lejeaftale_id, deficiencies, injuries);
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
            String sql = "SELECT * FROM tilstandsrapporter;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);

                surveyReports.add(getSingleEntityById(tilstandsrapport_id));
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


        }catch(Exception e){
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM tilstandsrapporter WHERE tilstandsrapport_id = " + id + ";";
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
            String sql = "INSERT IGNORE skader(`tilstandsrapport_id`, `skade_navn`, `skade_beskrivelse`, `skade_pris`) " +
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
            String sql = "INSERT IGNORE mangler(`tilstandsrapport_id`, `mangel_navn`, `mangel_beskrivelse`, `mangel_pris`) " +
                    "VALUES (" +
                    "'" + deficiency.getAgreementId() + "', " +
                    "'" + deficiency.getTitle() + "', " +
                    "'" + deficiency.getDescription() + "', " +
                    "'" + deficiency.getPrice() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette mangel med tilstandsrapport Id " + deficiency.getAgreementId() + " i databasen");
        }
        return false;
    }


    private ArrayList<Injury> getInjuries(int surveyReportId) {

        ArrayList<Injury> injuries = new ArrayList<>();

        try {
            String sql = "SELECT * FROM skader WHERE tilstandsrapport_id = '" + surveyReportId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String skade_navn = rs.getString(3);
                String skade_beskrivelse = rs.getString(4);
                int skade_pris = rs.getInt(5);

                injuries.add(new Injury(skade_id, tilstandsrapport_id, skade_navn, skade_beskrivelse, skade_pris));
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
            String sql = "SELECT * FROM mangler WHERE tilstandsrapport_id = '" + surveyReportId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String mangel_navn = rs.getString(3);
                String mangel_beskrivelse = rs.getString(4);
                int mangel_pris = rs.getInt(5);

                deficiencies.add(new Deficiency(mangel_id, tilstandsrapport_id, mangel_navn, mangel_beskrivelse, mangel_pris));
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


