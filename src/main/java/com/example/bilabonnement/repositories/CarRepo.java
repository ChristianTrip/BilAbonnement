package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarRepo implements CRUDInterface<Car> {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Car car) {

        Properties connectionInfo = null;
        try{
            conn = DatabaseConnectionManager.getConnection();
            connectionInfo = conn.getClientInfo();
            String sql = "INSERT INTO cars(`agreement_id`, `chassis_number`, `brand`, `model`) " +
                    "VALUES (" +
                    "'" + car.getAgreementId() + "', " +
                    "'" + car.getChassisNumber() + "', " +
                    "'" + car.getBrand() + "', " +
                    "'" + car.getModel() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not create car with chassis number '" + car.getChassisNumber() + "' on connection " + connectionInfo);
        }

        return false;
    }

    @Override
    public Car getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM cars WHERE car_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                int carId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                String chassisNumber = rs.getString(3);
                String brand = rs.getString(4);
                String model = rs.getString(5);

                conn.close();
                return new Car(carId, agreementId, chassisNumber, brand, model);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find car with id '" + id + "' in database");
        }
        return null;
    }

    @Override
    public List<Car> getAllEntities() {
        ArrayList<Car> cars = new ArrayList<>();

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM cars;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                int carId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                String chassisNumber = rs.getString(3);
                String brand = rs.getString(4);
                String model = rs.getString(5);

                Car car = new Car(carId, agreementId, chassisNumber, brand, model);
                cars.add(car);
            }
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not retrieve cars from database");
        }
        return cars;
    }

    @Override
    public boolean update(Car car) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE cars " +
                    "SET " +
                    "brand = '" + car.getBrand() + "', " +
                    "model = '" + car.getModel() + "' " +
                    "WHERE chassis_number = '" + car.getChassisNumber() + "';";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            conn.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update car with chassis number '" + car.getChassisNumber() + "'");
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM cars WHERE car_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not delete car with id number '" + id + "'");
            return false;
        }
        return true;
    }

}
