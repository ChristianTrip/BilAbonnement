package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrugerRepo implements CRUDInterface<Bruger>{

    @Override
    public boolean create(Bruger bruger) {
        try{
            String sql = "INSERT INTO brugere(`bruger_type`, `bruger_navn`, `adgangskode`) " +
                    "VALUES ('" + bruger.getBrugerType() + "', " +
                    "'" + bruger.getNavn() + "', " +
                    "'" + bruger.getAdgangskode() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bruger med id " + bruger.getId() + " i databasen");
        }

        return false;
    }

    @Override
    public Bruger getSingleEntityById(int id) {


        try {
            String sql = "SELECT * FROM brugere WHERE bruger_id = " + id + ";";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                int bruger_id = rs.getInt(1);
                String bruger_type = rs.getString(2);
                String navn = rs.getString(3);
                String adgangskode = rs.getString(4);

                BrugerType brugeradgang;
                brugeradgang = BrugerType.valueOf(bruger_type);

                return new Bruger(bruger_id, navn, adgangskode, brugeradgang);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde bruger med id: " + id);
        }
        return null;
    }

    @Override
    public List getAllEntities() {
        ArrayList<Bruger> brugere = new ArrayList<>();

        try {
            String sql = "SELECT * FROM brugere;";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int bruger_id = rs.getInt(1);
                String bruger_type = rs.getString(2);
                String navn = rs.getString(3);
                String adgangskode = rs.getString(4);

                BrugerType brugerAdgang;
                brugerAdgang = BrugerType.valueOf(bruger_type);

                Bruger bruger = new Bruger(bruger_id, navn, adgangskode, brugerAdgang);

                brugere.add(bruger);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde brugere");
        }
        return brugere;
    }

    @Override
    public boolean update(Bruger bruger) {
        try {

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE brugere " +
                    "SET " +
                    "bruger_navn = '" + bruger.getNavn() + "', " +
                    "adgangskode = '" + bruger.getAdgangskode() + "' " +
                    "WHERE bruger_id = " + bruger.getId() + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med id: " + bruger.getId());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM brugere WHERE bruger_id = " + id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette kunde med id: " + id);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        BrugerRepo br = new BrugerRepo();
        System.out.println(br.getSingleEntityById(2));
    }

}
