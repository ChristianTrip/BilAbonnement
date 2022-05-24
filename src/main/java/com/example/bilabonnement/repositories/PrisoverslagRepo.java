package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrisoverslagRepo implements CRUDInterface<Prisoverslag> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Prisoverslag prisoverslag) {
        try{
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `total_pris`, `abonnements_længde`) " +
                    "VALUES (" +
                    "'" + prisoverslag.getLejeaftaleId() + "', " +
                    "'" + prisoverslag.getTotalpris() + "', " +
                    "'" + prisoverslag.getAbonnementsLaengde() + "');";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette prisoverslag med tilhørende lejeaftale id " + prisoverslag.getLejeaftaleId() + " i databasen");
        }

        return false;
    }

    @Override
    public Prisoverslag getSingleEntityById(int lejeaftaleId) {

        try {

            String sql = "SELECT * FROM prisoverslag WHERE lejeaftale_id = '" + lejeaftaleId + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                return new Prisoverslag(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde prisoverslag tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    @Override
    public List<Prisoverslag> getAllEntities() {

        List<Prisoverslag> allePrisoverslag = new ArrayList<>();

        try {
            String sql = "SELECT * FROM prisoverslag;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                allePrisoverslag.add(new Prisoverslag(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris));
            }
            return allePrisoverslag;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde alle prisoverslag");
        }

        return null;
    }

    @Override
    public boolean update(Prisoverslag prisoverslag) {

        try{
            String sql =    "UPDATE prisoverslag " +
                            "SET " +
                            "abonnements_længde = '" + prisoverslag.getAbonnementsLaengde() + "', " +
                            "km_pr_mdr = '" + prisoverslag.getKmPrMdr() + "', " +
                            "totalpris = '" + prisoverslag.getTotalpris() + "', " +
                            "WHERE prisoverslag_id = " + prisoverslag.getId() + ";";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere abonnement med id " + prisoverslag.getId());
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

    public static void main(String[] args) {
        PrisoverslagRepo repo = new PrisoverslagRepo();
        System.out.println(repo.getAllEntities());
    }

}
