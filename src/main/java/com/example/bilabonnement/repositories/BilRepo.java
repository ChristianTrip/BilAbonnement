package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Bil;
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
            String sql = "INSERT INTO biler(`lejeaftale_id`, `bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + bil.getLejeaftaleId() + "', " +
                    "'" + bil.getStelnummer() + "', " +
                    "'" + bil.getName() + "', " +
                    "'" + bil.getModel() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + bil.getStelnummer() + " i databasen");
        }

        return false;
    }

    @Override
    public Bil getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM biler WHERE bil_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                int bil_id = rs.getInt(1);
                String bil_stelnummer = rs.getString(2);
                String bil_navn = rs.getString(3);
                String bil_model = rs.getString(4);

                return new Bil(bil_id, bil_stelnummer, bil_navn, bil_model);
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
        ArrayList<Bil> biler = new ArrayList<>();

        try {
            String sql = "SELECT * FROM biler;";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String stelnummer = rs.getString(1);
                String navn = rs.getString(2);
                String model = rs.getString(3);

                Bil bil = new Bil(stelnummer, navn, model);

                biler.add(bil);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde brugere");
        }
        return biler;
    }

    @Override
    public boolean update(Bil bil) {

        try {

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE biler " +
                    "SET " +
                    "bil_stelnummer = '" + bil.getStelnummer() + "', " +
                    "bil_name = '" + bil.getName() + "', " +
                    "bil_model = '" + bil.getModel() + "' " +
                    "WHERE bil_id = " + bil.getId() + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere bil med id: " + bil.getId());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM biler WHERE bil_id = " + id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette bil med id: " + id);
            return false;
        }

        return true;
    }


    /*public static void main(String[] args) {
        BilRepo bil = new BilRepo();

        //Testing

        //Create
        //bil.create(new Bil("2jkh32hj", "Bentley", "F67"));
        //bil.create(new Bil("3kj43jk3", "Fiat", "O99"));

        //GetSingleEntity
        //System.out.println(bil.getSingleEntityById(1));

        //GetAllEntities
        //System.out.println(bil.getAllEntities());

        //Update
        *//*Bil nyBil = bil.getSingleEntityById(1);

        System.out.println(nyBil);

        nyBil.setName("Ford");
        nyBil.setModel("G7");

        bil.update(nyBil);*//*

        //deleteById
        //bil.deleteById(1);


    }*/

}
