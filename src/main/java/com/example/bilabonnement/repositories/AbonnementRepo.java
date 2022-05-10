package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AbonnementRepo implements CRUDInterface<Abonnement> {


    @Override
    public boolean create(Abonnement abonnement) {

        int lavSelrisiko = 0;
        if (abonnement.isLavSelvrisiko()){
            lavSelrisiko = 1;
        }

        try{
            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `lejeperiode_mdr`) " +
                    "VALUES (" +
                    "'" + abonnement.getLejeaftaleId() + "', " +
                    "'" + lavSelrisiko + "', " +
                    "'" + abonnement.getLejeperiodeMdr() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette abonnement tilhørende lejeaftale med id " + abonnement.getLejeaftaleId() + " i databasen");
        }

        return false;
    }

    @Override
    public Abonnement getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Abonnement> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Abonnement abonnement) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
