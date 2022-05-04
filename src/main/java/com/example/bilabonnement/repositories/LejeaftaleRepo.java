package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LejeaftaleRepo implements CRUDInterface{



    @Override
    public boolean create(Object entity) {

        Lejeaftale lejeaftale = (Lejeaftale) entity;

        java.sql.Date mySQLDate = new java.sql.Date(lejeaftale.getOprettelsesDato().getTime());
        try{
            String sql = "INSERT INTO lejeaftaler(`kunde_id`, `bil_id`, `abonnement_id`, `tilstandsrapport`, `prisoverslag_id`, `afhentningssted`, `oprettelsesdato`) " +
                    "VALUES ('" + lejeaftale.getKunde().getId() + "', " +
                    "'" + lejeaftale.getBil().getId() + "', " +
                    "'" + lejeaftale.getAbonnement().getId() + "', " +
                    "'" + lejeaftale.getTilstandsRapport().getID() + "', " +
                    "'" + lejeaftale.getPrisoverslag().getId() + "', " +
                    "'" + lejeaftale.getAfhentningsSted() + "', " +
                    "'" + mySQLDate + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public Object getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
