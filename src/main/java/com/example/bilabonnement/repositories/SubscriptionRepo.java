package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.leaseAgreements.LimitedSubscription;
import com.example.bilabonnement.models.leaseAgreements.Subscription;
import com.example.bilabonnement.models.leaseAgreements.UnlimitedSubscription;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepo implements CRUDInterface<Subscription> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Subscription subscription) {

        try{
            conn = DatabaseConnectionManager.getConnection();

            boolean isLimited = true;
            if (subscription.getClass().equals(UnlimitedSubscription.class)){
                isLimited = false;
            }

            String sql = "INSERT INTO subscriptions(`agreement_id`, `low_deductible`, `delivery_insurance`, `length_in_months`, `has_standard_color`, `is_limited`) " +
                    "VALUES (" +
                    "'" + subscription.getAgreementId() + "', " +
                    ""  + subscription.hasLowDeductible() + ", " +
                    ""  + subscription.hasDeliveryInsurance() + ", " +
                    "'" + subscription.getLengthInMonths() + "', " +
                    "" + subscription.hasStandardColor() + ", " +
                    " " + isLimited + ");";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette abonnement tilhørende lejeaftale med id " + subscription.getAgreementId() + " i databasen");
        }
        return false;
    }

    @Override
    public Subscription getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM subscriptions WHERE subscriptionId = '" + id + "';";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int subsriptionId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                boolean lowDeductible = rs.getBoolean(3);
                boolean deliveryInsurance = rs.getBoolean(4);
                int lengthInMonths = rs.getInt(5);
                boolean hasStandardColor = rs.getBoolean(6);
                boolean is_limited = rs.getBoolean(7);


                if (is_limited){
                    return new LimitedSubscription(subsriptionId, agreementId, lowDeductible, hasStandardColor);
                }
                else{
                    return new UnlimitedSubscription(subsriptionId, agreementId, lengthInMonths, lowDeductible, deliveryInsurance, hasStandardColor);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement tilhørende lejeaftale id: " + id);
        }
        return null;
    }

    @Override
    public List<Subscription> getAllEntities() {

        List<Subscription> subscriptions = new ArrayList<>();

        try {
            String sql = "SELECT * FROM subscriptions;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int subsriptionId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                boolean lowDeductible = rs.getBoolean(3);
                boolean deliveryInsurance = rs.getBoolean(4);
                int lengthInMonths = rs.getInt(5);
                boolean hasStandardColor = rs.getBoolean(6);
                boolean is_limited = rs.getBoolean(7);


                if (is_limited){
                    subscriptions.add(new LimitedSubscription(subsriptionId, agreementId, lowDeductible, hasStandardColor));
                }
                else{
                    subscriptions.add(new UnlimitedSubscription(subsriptionId, agreementId, lengthInMonths, lowDeductible, deliveryInsurance, hasStandardColor));
                }

            }
            return subscriptions;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde subscriptions");
        }

        return null;
    }

    @Override
    public boolean update(Subscription subscription) {

        try{
            conn = DatabaseConnectionManager.getConnection();

            boolean isLimited = false;
            if (subscription.getClass() == LimitedSubscription.class){
                isLimited = true;
            }

            String sql =    "UPDATE subscriptions " +
                    "SET " +
                    "low_deductible = '" + subscription.hasLowDeductible() + "', " +
                    "delevery_insurance = '" + subscription.hasDeliveryInsurance() + "', " +
                    "length_in_months = '" + subscription.getLengthInMonths() + "', " +
                    "has_standard_color = '" + subscription.hasStandardColor() + "', " +
                    "is_limited = '" + isLimited + "'" +
                    "WHERE subscription_id = " + subscription.getId() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere abonnement med id " + subscription.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM subscriptions WHERE subscription_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette abonnement med id nummer: " + id);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SubscriptionRepo repo = new SubscriptionRepo();
        System.out.println(repo.getAllEntities());
    }
}
