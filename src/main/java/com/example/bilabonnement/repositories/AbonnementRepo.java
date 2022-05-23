package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.abonnementer.UnlimitedAbonnement;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbonnementRepo implements CRUDInterface<Abonnement> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Abonnement abonnement) {

        boolean isLimited = true;
        if (abonnement.getClass().equals(UnlimitedAbonnement.class)){
            isLimited = false;
        }

        try{
            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `afleveringsforsikring`, `lejeperiode_mdr`, `valgt_farve`, `is_limited`) " +
                    "VALUES (" +
                    "'" + abonnement.getLejeaftaleId() + "', " +
                    ""  + abonnement.isLavSelvrisiko() + ", " +
                    ""  + abonnement.isAfleveringsforsikring() + ", " +
                    "'" + abonnement.getLejeperiodeMdr() + "', " +
                    "" + abonnement.isValgtFarve() + ", " +
                    " " + isLimited + ");";


            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
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
    public Abonnement getSingleEntityById(int lejeaftaleId) {

        try {
            String sql = "SELECT * FROM abonnementer WHERE lejeaftale_id = '" + lejeaftaleId + "';";
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
                    return new LimitedAbonnement(abonnement_id, lejeaftale_id, lav_selvrisiko, valgt_farve);
                }
                else{
                    return new UnlimitedAbonnement(abonnement_id, lejeaftaleId, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    @Override
    public List<Abonnement> getAllEntities() {

        List<Abonnement> abonnementer = new ArrayList<>();

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
                    abonnementer.add(new LimitedAbonnement(abonnement_id, lejeaftale_id, lav_selvrisiko, valgt_farve));
                }
                else{
                    abonnementer.add(new UnlimitedAbonnement(abonnement_id, lejeaftale_id, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve));
                }

            }
            return abonnementer;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnementer");
        }

        return null;
    }

    @Override
    public boolean update(Abonnement abonnement) {

        boolean isLimited = false;

        if (abonnement.getClass() == LimitedAbonnement.class){
            isLimited = true;
        }

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE abonnementer " +
                    "SET " +
                    "lav_selvrisiko = '" + abonnement.isLavSelvrisiko() + "', " +
                    "afleveringsforsikring = '" + abonnement.isAfleveringsforsikring() + "', " +
                    "lejeperiode = '" + abonnement.getLejeperiodeMdr() + "', " +
                    "valgt_farve = '" + abonnement.isValgtFarve() + "', " +
                    "is_limited = '" + isLimited + "'" +
                    "WHERE abonnement_id = " + abonnement.getId() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere abonnement med id " + abonnement.getId());
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
        AbonnementRepo repo = new AbonnementRepo();
        System.out.println(repo.getAllEntities());
    }
}
