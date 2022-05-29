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
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `total_pris`, `abonnements_længde`) " +
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
    public PriceEstimate getSingleEntityById(int lejeaftaleId) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM prisoverslag WHERE lejeaftale_id = '" + lejeaftaleId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                return new PriceEstimate(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde prisoverslag tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    @Override
    public List<PriceEstimate> getAllEntities() {

        List<PriceEstimate> allePriceEstimate = new ArrayList<>();

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM prisoverslag;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                allePriceEstimate.add(new PriceEstimate(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris));
            }
            return allePriceEstimate;

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
            String sql =    "UPDATE prisoverslag " +
                            "SET " +
                            "abonnements_længde = '" + priceEstimate.getSubscriptionLength() + "', " +
                            "km_pr_mdr = '" + priceEstimate.getkmPerMonth() + "', " +
                            "totalpris = '" + priceEstimate.getTotalPrice() + "', " +
                            "WHERE prisoverslag_id = " + priceEstimate.getId() + ";";
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
            String sql = "DELETE FROM prisoverslag WHERE prisoverslag_id = " + id + ";";
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
