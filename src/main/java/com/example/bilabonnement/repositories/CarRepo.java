package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepo implements CRUDInterface<Car> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Car car) {
        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO biler(`lejeaftale_id`, `bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + car.getAgreementId() + "', " +
                    "'" + car.getChassisNumber() + "', " +
                    "'" + car.getBrand() + "', " +
                    "'" + car.getModel() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + car.getChassisNumber() + " i databasen");
        }

        return false;
    }

    @Override
    public Car getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM biler WHERE bil_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){

                int lejeaftale_id = rs.getInt(1);
                String bil_stelnummer = rs.getString(2);
                String bil_navn = rs.getString(3);
                String bil_model = rs.getString(4);

                return new Car(lejeaftale_id, bil_stelnummer, bil_navn, bil_model);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde bil med id: " + id);
        }
        return null;
    }

    @Override
    public List<Car> getAllEntities() {
        ArrayList<Car> cars = new ArrayList<>();

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM biler;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                int lejeAftaleId = rs.getInt(1);
                String stelnummer = rs.getString(2);
                String navn = rs.getString(3);
                String model = rs.getString(4);

                Car car = new Car(lejeAftaleId, stelnummer, navn, model);

                cars.add(car);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde brugere");
        }
        return cars;
    }

    @Override
    public boolean update(Car car) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE biler " +
                    "SET " +
                    "bil_stelnummer = '" + car.getChassisNumber() + "', " +
                    "bil_name = '" + car.getBrand() + "', " +
                    "bil_model = '" + car.getModel() + "' " +
                    "WHERE bil_stelnummer = " + car.getChassisNumber() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere bil med stelnummer: " + car.getChassisNumber());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM biler WHERE bil_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette bil med id: " + id);
            return false;
        }
        return true;
    }

}
