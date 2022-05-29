package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Deficiency;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShortcomingRepo implements CRUDInterface<Deficiency>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Deficiency deficiency) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO mangler(`tilstandsrapport_id`, `mangel_navn`, `mangel_beskrivelse`, `mangel_pris`) " +
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
            System.out.println("Kunne ikke oprette mangel tilhørende tilstandsrapport id " + deficiency.getAgreementId() + " i databasen");
        }

        return false;
    }

    @Override
    public Deficiency getSingleEntityById(int tilstandsrapportId) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM mangler WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                return new Deficiency(mangel_id, tilstandsrapport_id, navn, beskrivelse, pris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde mangel tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }

    @Override
    public List<Deficiency> getAllEntities() {

        List<Deficiency> deficiencies = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM mangler;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                deficiencies.add(new Deficiency(mangel_id, tilstandsrapport_id, navn, beskrivelse, pris));
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
            String sql =    "UPDATE mangler " +
                    "SET " +
                    "mangel_navn = '" + deficiency.getTitle() + "', " +
                    "mangel_beskrivelse = '" + deficiency.getDescription() + "', " +
                    "mangel_pris = '" + deficiency.getPrice() + "', " +
                    "WHERE mangel_id = " + deficiency.getId() + ";";

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

}


