package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Bil;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BilRepo implements CRUDInterface<Bil> {


    @Override
    public boolean create(Bil bil) {
        try{
            String sql = "INSERT INTO biler(`bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + bil.getStelNummer() + "', " +
                    "'" + bil.getName() + "', " +
                    "'" + bil.getModel() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + bil.getStelNummer() + " i databasen");
        }

        return false;
    }

    @Override
    public Bil getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM biler WHERE bruger_id = " + id + ";";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                String stelnummer = rs.getString(1);
                String navn = rs.getString(2);
                String model = rs.getString(3);

                return new Bruger(stelnummer, navn, model);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde bil med id: " + id);
        }
        return null;
    }

    @Override
    public List<Bil> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Bil bil) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
