package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.AfhentningsSted;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfhentningsstedRepo implements CRUDInterface<AfhentningsSted> {


    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;


    @Override
    public boolean create(AfhentningsSted afhentningsSted) {

        try{
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + afhentningsSted.getLejeaftaleId() + "', " +
                    "'" + afhentningsSted.getAdresse() + "', " +
                    "'" + afhentningsSted.getPostnummer() + "', " +
                    "'" + afhentningsSted.getBy() + "', " +
                    "'" + afhentningsSted.getLeveringspris() + "');";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette afhentningsted med tilhørende lejeaftale id " + afhentningsSted.getLejeaftaleId() + " i databasen");
        }

        return false;
    }

    @Override
    public AfhentningsSted getSingleEntityById(int lejeaftaleId) {
        try {
            String sql = "SELECT * FROM afhentningssteder WHERE lejeaftale_id = '" + lejeaftaleId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int afhentningssted_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                int levering = rs.getInt(6);

                return new AfhentningsSted(afhentningssted_id, adresse, post_nummer, by_navn, levering);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde afhentningssted tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    @Override
    public List<AfhentningsSted> getAllEntities() {

        List<AfhentningsSted> afhentningsSteder = new ArrayList<>();

        try{
            String sql = "SELECT * FROM afhentningssteder;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int afhentningssted_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                int levering = rs.getInt(6);

                afhentningsSteder.add(new AfhentningsSted(afhentningssted_id, lejeaftale_id, adresse, post_nummer, by_navn, levering));
            }
            return afhentningsSteder;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke hente afhentningsteder");

        }
        return null;
    }

    @Override
    public boolean update(AfhentningsSted afhentningsSted) {

        try {
            String sql = "UPDATE afhentningssteder " +
                    "SET " +
                    "adresse = '" + afhentningsSted.getAdresse() + "', " +
                    "post_nummer = '" + afhentningsSted.getPostnummer() + "', " +
                    "by_navn = '" + afhentningsSted.getBy() + "', " +
                    "levering = '" + afhentningsSted.getLeveringspris() + "'" +
                    "WHERE afhentningssted_id = " + afhentningsSted.getId() + ";";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere afhentningsted med id " + afhentningsSted.getId());

        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            String sql = "DELETE afhentningssteder WHERE afhentningssted_id = " + id;
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette afhentningsted med id " + id);
        }

        return false;
    }

    public static void main(String[] args) {
        AfhentningsstedRepo repo = new AfhentningsstedRepo();
        System.out.println(repo.getAllEntities());
    }
}
