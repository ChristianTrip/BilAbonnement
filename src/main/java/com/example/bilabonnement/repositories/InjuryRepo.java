package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.surveyReports.Injury;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InjuryRepo implements CRUDInterface<Injury>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Injury injury) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO injuries(`report_id`, `title`, `description`, `price`) " +
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
            System.out.println("Kunne ikke oprette skade tilhørende tilstandsrapport id " + injury.getSurveyReportId() + " i databasen");
        }

        return false;
    }

    @Override
    public Injury getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM injuries WHERE injury_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int injuryId = rs.getInt(1);
                int reportId = rs.getInt(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                int price = rs.getInt(5);


                return new Injury(injuryId, reportId, title, description, price);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade med id: " + id);
        }
        return null;
    }

    @Override
    public List<Injury> getAllEntities() {

        List<Injury> injuries = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM injuries;";
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
            System.out.println("Kunne ikke finde skader");
        }
        return null;
    }

    @Override
    public boolean update(Injury injury) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE injuries " +
                            "SET " +
                            "title = '" + injury.getTitle() + "', " +
                            "description = '" + injury.getDescription() + "', " +
                            "price = '" + injury.getPrice() + "'" +
                            "WHERE injury_id = " + injury.getId() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere skade med id " + injury.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM injuries WHERE injury_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette skade med id nummer: " + id);
            return false;
        }
        return true;
    }

}
