package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.subscriptions.LimitedSubscription;
import com.example.bilabonnement.models.subscriptions.Subscription;
import com.example.bilabonnement.models.subscriptions.UnlimitedSubscription;
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

            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `afleveringsforsikring`, `lejeperiode_mdr`, `valgt_farve`, `is_limited`) " +
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
    public Subscription getSingleEntityById(int agreementId) {

        try {
            String sql = "SELECT * FROM abonnementer WHERE lejeaftale_id = '" + agreementId + "';";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int abonnement_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                boolean lav_selvrisiko = rs.getBoolean(3);
                boolean afleveringsforsikring = rs.getBoolean(4);
                int lejeperiode_mdr = rs.getInt(5);
                boolean valgt_farve = rs.getBoolean(6);
                boolean is_limited = rs.getBoolean(7);


                if (is_limited){
                    return new LimitedSubscription(abonnement_id, lejeaftale_id, lav_selvrisiko, valgt_farve);
                }
                else{
                    return new UnlimitedSubscription(abonnement_id, agreementId, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement tilhørende lejeaftale id: " + agreementId);
        }
        return null;
    }

    @Override
    public List<Subscription> getAllEntities() {

        List<Subscription> subscriptions = new ArrayList<>();

        try {
            String sql = "SELECT * FROM abonnementer;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int abonnement_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                boolean lav_selvrisiko = rs.getBoolean(3);
                boolean afleveringsforsikring = rs.getBoolean(4);
                int lejeperiode_mdr = rs.getInt(5);
                boolean valgt_farve = rs.getBoolean(6);
                boolean is_limited = rs.getBoolean(7);


                if (is_limited){
                    subscriptions.add(new LimitedSubscription(abonnement_id, lejeaftale_id, lav_selvrisiko, valgt_farve));
                }
                else{
                    subscriptions.add(new UnlimitedSubscription(abonnement_id, lejeaftale_id, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve));
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

            String sql =    "UPDATE abonnementer " +
                    "SET " +
                    "lav_selvrisiko = '" + subscription.hasLowDeductible() + "', " +
                    "afleveringsforsikring = '" + subscription.hasDeliveryInsurance() + "', " +
                    "lejeperiode = '" + subscription.getLengthInMonths() + "', " +
                    "valgt_farve = '" + subscription.hasStandardColor() + "', " +
                    "is_limited = '" + isLimited + "'" +
                    "WHERE abonnement_id = " + subscription.getId() + ";";

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
            String sql = "DELETE FROM abonnementer WHERE abonnement_id = " + id + ";";
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
