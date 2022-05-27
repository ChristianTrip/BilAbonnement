package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkadeRepo implements CRUDInterface<Skade>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Skade skade) {

        try{
            String sql = "INSERT INTO skader(`tilstandsrapport_id`, `skade_navn`, `skade_beskrivelse`, `skade_pris`) " +
                    "VALUES (" +
                    "'" + skade.getTilstandsRapportId() + "', " +
                    "'" + skade.getNavn() + "', " +
                    "'" + skade.getBeskrivelse() + "', " +
                    "'" + skade.getPris() + "');";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette skade tilhørende tilstandsrapport id " + skade.getTilstandsRapportId() + " i databasen");
        }

        return false;
    }

    @Override
    public Skade getSingleEntityById(int tilstandsrapportId) {

        try {
            String sql = "SELECT * FROM skader WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                return new Skade(skade_id, tilstandsrapport_id, navn, beskrivelse, pris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }

    @Override
    public List<Skade> getAllEntities() {

        List<Skade> mangler = new ArrayList<>();
        try {

            String sql = "SELECT * FROM skader;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String navn = rs.getString(3);
                String beskrivelse = rs.getString(4);
                int pris = rs.getInt(5);

                mangler.add(new Skade(skade_id, tilstandsrapport_id, navn, beskrivelse, pris));
            }

            return mangler;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skader");
        }
        return null;
    }

    @Override
    public boolean update(Skade skade) {

        try{
            String sql =    "UPDATE skader " +
                    "SET " +
                    "skade_navn = '" + skade.getNavn() + "', " +
                    "skade_beskrivelse = '" + skade.getBeskrivelse() + "', " +
                    "skade_pris = '" + skade.getPris() + "', " +
                    "WHERE skade_id = " + skade.getId() + ";";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere skade med id " + skade.getId());
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

    public static void main(String[] args) {
        SkadeRepo repo = new SkadeRepo();
        System.out.println(repo.getAllEntities());
    }

}
