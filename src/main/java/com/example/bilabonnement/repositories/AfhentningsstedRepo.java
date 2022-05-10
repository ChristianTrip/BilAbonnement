package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.AfhentningsSted;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AfhentningsstedRepo implements CRUDInterface<AfhentningsSted> {

    @Override
    public boolean create(AfhentningsSted afhentningsSted) {

        try{
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + afhentningsSted.getLejeaftaleId() + "', " +
                    "'" + afhentningsSted.getAdresse() + "', " +
                    "'" + afhentningsSted.getPostNummer() + "', " +
                    "'" + afhentningsSted.getBy() + "', " +
                    "'" + afhentningsSted.getLevering() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
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
    public AfhentningsSted getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<AfhentningsSted> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(AfhentningsSted entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
