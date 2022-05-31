package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.leaseAgreements.PickupPlace;
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
            String sql = "INSERT INTO pickup_places(`agreement_id`, `address`, `postal_code`, `city`, `delivery_cost`) " +
                    "VALUES (" +
                    "'" + pickupPlace.getAgreementId() + "', " +
                    "'" + pickupPlace.getAddress() + "', " +
                    "'" + pickupPlace.getPostalCode() + "', " +
                    "'" + pickupPlace.getCity() + "', " +
                    "'" + pickupPlace.getDeliveryCost() + "');";

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
    public PickupPlace getSingleEntityById(int id) {
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM pickup_places WHERE pickup_place_id = '" + id + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int pickupPlaceId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                String address = rs.getString(3);
                String postalCode = rs.getString(4);
                String city = rs.getString(5);
                int deliveryCost = rs.getInt(6);

                return new PickupPlace(pickupPlaceId, agreementId, address, postalCode, city, deliveryCost);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde afhentningssted tilhørende lejeaftale id: " + id);
        }
        return null;
    }

    @Override
    public List<PickupPlace> getAllEntities() {

        List<PickupPlace> pickupPlaces = new ArrayList<>();

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM pickup_places;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int pickupPlaceId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                String address = rs.getString(3);
                String postalCode = rs.getString(4);
                String city = rs.getString(5);
                int deliveryCost = rs.getInt(6);

                pickupPlaces.add(new PickupPlace(pickupPlaceId, agreementId, address, postalCode, city, deliveryCost));
            }
            return pickupPlaces;

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
            String sql = "UPDATE pickup_places " +
                    "SET " +
                    "address = '" + pickupPlace.getAddress() + "', " +
                    "postal_code = '" + pickupPlace.getPostalCode() + "', " +
                    "city = '" + pickupPlace.getCity() + "', " +
                    "delivery_cost = '" + pickupPlace.getDeliveryCost() + "'" +
                    "WHERE pickup_place_id = " + pickupPlace.getId() + ";";
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
            String sql = "DELETE pickup_places WHERE pickup_place_id = " + id;
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
