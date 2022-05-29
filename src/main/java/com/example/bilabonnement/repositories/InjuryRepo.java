package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Injury;
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
            String sql = "INSERT INTO skader(`tilstandsrapport_id`, `skade_navn`, `skade_beskrivelse`, `skade_pris`) " +
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
    public Injury getSingleEntityById(int tilstandsrapportId) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM skader WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                return new Injury(skade_id, tilstandsrapport_id, navn, beskrivelse, pris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }

    @Override
    public List<Injury> getAllEntities() {

        List<Injury> injuries = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM skader;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                injuries.add(new Injury(skade_id, tilstandsrapport_id, navn, beskrivelse, pris));
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
            String sql =    "UPDATE skader " +
                    "SET " +
                    "skade_navn = '" + injury.getTitle() + "', " +
                    "skade_beskrivelse = '" + injury.getDescription() + "', " +
                    "skade_pris = '" + injury.getPrice() + "', " +
                    "WHERE skade_id = " + injury.getId() + ";";

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
            String sql = "DELETE FROM skader WHERE skade_id = " + id + ";";
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
