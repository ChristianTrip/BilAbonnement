package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.PickupPlace;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PickupPlaceRepo implements CRUDInterface<PickupPlace> {


    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;


    @Override
    public boolean create(PickupPlace pickupPlace) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + pickupPlace.getAgreementId() + "', " +
                    "'" + pickupPlace.getAddress() + "', " +
                    "'" + pickupPlace.getPostalCode() + "', " +
                    "'" + pickupPlace.getCity() + "', " +
                    "'" + pickupPlace.getDeliveryPrice() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette afhentningsted med tilhørende lejeaftale id " + pickupPlace.getAgreementId() + " i databasen");
        }

        return false;
    }

    @Override
    public PickupPlace getSingleEntityById(int lejeaftaleId) {
        try {
            conn = DatabaseConnectionManager.getConnection();
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

                return new PickupPlace(afhentningssted_id, adresse, post_nummer, by_navn, levering);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde afhentningssted tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    @Override
    public List<PickupPlace> getAllEntities() {

        List<PickupPlace> afhentningsSteder = new ArrayList<>();

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM afhentningssteder;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int afhentningssted_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                int levering = rs.getInt(6);

                afhentningsSteder.add(new PickupPlace(afhentningssted_id, lejeaftale_id, adresse, post_nummer, by_navn, levering));
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
    public boolean update(PickupPlace pickupPlace) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE afhentningssteder " +
                    "SET " +
                    "adresse = '" + pickupPlace.getAddress() + "', " +
                    "post_nummer = '" + pickupPlace.getPostalCode() + "', " +
                    "by_navn = '" + pickupPlace.getCity() + "', " +
                    "levering = '" + pickupPlace.getDeliveryPrice() + "'" +
                    "WHERE afhentningssted_id = " + pickupPlace.getId() + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere afhentningsted med id " + pickupPlace.getId());

        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE afhentningssteder WHERE afhentningssted_id = " + id;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette afhentningsted med id " + id);
        }
        return false;
    }

}
