package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.abonnementer.UnlimitedAbonnement;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        try {
            String sql = "SELECT * FROM abonnementer WHERE abonnement_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int abonnement_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                boolean lav_selvrisiko = rs.getBoolean(3);
                boolean afleveringsforsikring = rs.getBoolean(4);
                int lejeperiode_mdr = rs.getInt(5);
                boolean is_limited = rs.getBoolean(6);


/*
                if (is_limited){
                    return new LimitedAbonnement(abonnement_id, lav_selvrisiko);
                }
                else{
                    return new UnlimitedAbonnement(abonnement_id, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring);
                }

 */

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement med id: " + id);
        }
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

    public static void main(String[] args) {
        AbonnementRepo repo = new AbonnementRepo();
        System.out.println(repo.getSingleEntityById(1));
        System.out.println(repo.getSingleEntityById(2));
        System.out.println(repo.getSingleEntityById(3));
    }
}
