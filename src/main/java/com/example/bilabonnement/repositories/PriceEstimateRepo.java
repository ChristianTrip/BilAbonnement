package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.priceEstimates.PriceEstimate;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceEstimateRepo implements CRUDInterface<PriceEstimate> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(PriceEstimate priceEstimate) {
        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO price_estimates(`agreement_id`, `total_price`, `subscription_length`) " +
                    "VALUES (" +
                    "'" + priceEstimate.getAgreementId() + "', " +
                    "'" + priceEstimate.getTotalPrice() + "', " +
                    "'" + priceEstimate.getSubscriptionLength() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette prisoverslag med tilhørende lejeaftale id " + priceEstimate.getAgreementId() + " i databasen");
        }
        return false;
    }

    @Override
    public PriceEstimate getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM price_estimates WHERE price_estimate_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int estimateId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                int subscriptionLength = rs.getInt(3);
                int kmPerMonth = rs.getInt(4);
                int totalPrice = rs.getInt(5);

                return new PriceEstimate(estimateId, agreementId, subscriptionLength, kmPerMonth, totalPrice);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde prisoverslag tilhørende lejeaftale id: " + id);
        }
        return null;
    }

    @Override
    public List<PriceEstimate> getAllEntities() {

        List<PriceEstimate> priceEstimates = new ArrayList<>();

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM price_estimates;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int estimateId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                int subscriptionLength = rs.getInt(3);
                int kmPerMonth = rs.getInt(4);
                int totalPrice = rs.getInt(5);

                priceEstimates.add(new PriceEstimate(estimateId, agreementId, subscriptionLength, kmPerMonth, totalPrice));
            }
            return priceEstimates;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde alle prisoverslag");
        }

        return null;
    }

    @Override
    public boolean update(PriceEstimate priceEstimate) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE price_estimates " +
                            "SET " +
                            "subscription_length = '" + priceEstimate.getSubscriptionLength() + "', " +
                            "km_per_month = '" + priceEstimate.getkmPerMonth() + "', " +
                            "total_price = '" + priceEstimate.getTotalPrice() + "', " +
                            "WHERE price_estimate_id = " + priceEstimate.getId() + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere abonnement med id " + priceEstimate.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {
        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM price_estimates WHERE price_estimate_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette prisoverslag med id nummer: " + id);
            return false;
        }

        return true;
    }

}
