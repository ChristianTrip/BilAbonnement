package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MangelRepo implements CRUDInterface<Mangel>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Mangel mangel) {

        try{
            String sql = "INSERT INTO mangler(`tilstandsrapport_id`, `mangel_navn`, `mangel_beskrivelse`, `mangel_pris`) " +
                    "VALUES (" +
                    "'" + mangel.getTilstandsRapportId() + "', " +
                    "'" + mangel.getNavn() + "', " +
                    "'" + mangel.getBeskrivelse() + "', " +
                    "'" + mangel.getPris() + "');";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette mangel tilhørende tilstandsrapport id " + mangel.getTilstandsRapportId() + " i databasen");
        }

        return false;
    }

    @Override
    public Mangel getSingleEntityById(int tilstandsrapportId) {

        try {
            String sql = "SELECT * FROM mangler WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                return new Mangel(mangel_id, tilstandsrapport_id, navn, beskrivelse, pris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde mangel tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }

    @Override
    public List<Mangel> getAllEntities() {

        List<Mangel> mangler = new ArrayList<>();
        try {

            String sql = "SELECT * FROM mangler;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                mangler.add(new Mangel(mangel_id, tilstandsrapport_id, navn, beskrivelse, pris));
            }

            return mangler;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde mangler");
        }
        return null;
    }

    @Override
    public boolean update(Mangel mangel) {

        try{
            String sql =    "UPDATE mangler " +
                    "SET " +
                    "mangel_navn = '" + mangel.getNavn() + "', " +
                    "mangel_beskrivelse = '" + mangel.getBeskrivelse() + "', " +
                    "mangel_pris = '" + mangel.getPris() + "', " +
                    "WHERE mangel_id = " + mangel.getId() + ";";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere mangel med id " + mangel.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM mangler WHERE mangel_id = " + id + ";";
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

    public static void main(String[] args) {
        MangelRepo repo = new MangelRepo();
        System.out.println(repo.getAllEntities());
    }

}


