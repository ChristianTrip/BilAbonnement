package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.surveyReports.Deficiency;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeficiencyRepo implements CRUDInterface<Deficiency>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Deficiency deficiency) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO deficiencies(`report_id`, `title`, `description`, `price`) " +
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
            System.out.println("Kunne ikke oprette mangel tilhørende tilstandsrapport id " + deficiency.getReportId() + " i databasen");
        }

        return false;
    }

    @Override
    public Deficiency getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM deficiencies WHERE deficiency_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int deficiencyId = rs.getInt(1);
                int reportId = rs.getInt(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                int price = rs.getInt(5);


                return new Deficiency(deficiencyId, reportId, title, description, price);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde mangel tilhørende tilstandsrapport id: " + id);
        }
        return null;
    }

    @Override
    public List<Deficiency> getAllEntities() {

        List<Deficiency> deficiencies = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM deficiencies;";
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
            System.out.println("Kunne ikke finde deficiencies");
        }
        return null;
    }

    @Override
    public boolean update(Deficiency deficiency) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE deficiencies " +
                    "SET " +
                    "title = '" + deficiency.getTitle() + "', " +
                    "description = '" + deficiency.getDescription() + "', " +
                    "price = '" + deficiency.getPrice() + "'" +
                    "WHERE deficiency_id = " + deficiency.getId() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere mangel med id " + deficiency.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM deficiencies WHERE deficiency_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette mangel med id nummer: " + id);
            return false;
        }

        return true;
    }

}


