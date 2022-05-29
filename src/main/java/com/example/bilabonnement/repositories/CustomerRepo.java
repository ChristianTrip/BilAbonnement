package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Customer;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo implements CRUDInterface <Customer>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Customer customer) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO kunder(`lejeaftale_id`, `for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by_navn`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
                    "VALUES ('" + customer.getAgreementId() + "', " +
                    "'" + customer.getFirstName() + "', " +
                    "'" + customer.getLastName() + "', " +
                    "'" + customer.getAddress() + "', " +
                    "'" + customer.getPostalCode() + "', " +
                    "'" + customer.getCity() + "', " +
                    "'" + customer.getEmail() + "', " +
                    "'" + customer.getPhoneNumber() + "', " +
                    "'" + customer.getCpr() + "', " +
                    "'" + customer.getRegNumber() + "', " +
                    "'" + customer.getAccountNumber() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bruger med cpr nummer " + customer.getCpr() + " i databasen");
        }

        return false;
    }

    @Override
    public Customer getSingleEntityById(int cpr) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM kunder WHERE cpr = '" + cpr + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                String for_navn = rs.getString(1);
                String efter_navn = rs.getString(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by = rs.getString(5);
                String email = rs.getString(6);
                String mobil = rs.getString(7);
                String cpr_nummer = rs.getString(8);
                String reg_nummer = rs.getString(9);
                String konto_nummer = rs.getString(10);

                return new Customer(for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr_nummer, reg_nummer, konto_nummer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med cpr nummer: " + cpr);
        }
        return null;
    }

    @Override
    public List<Customer> getAllEntities() {

        ArrayList<Customer> customers = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM kunder;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                String for_navn = rs.getString(1);
                String efter_navn = rs.getString(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by = rs.getString(5);
                String email = rs.getString(6);
                String mobil = rs.getString(7);
                String cpr = rs.getString(8);
                String reg_nummer = rs.getString(9);
                String konto_nummer = rs.getString(10);

                Customer customer = new Customer(for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr, reg_nummer, konto_nummer);
                customers.add(customer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde customers");
        }
        return customers;
    }

    @Override
    public boolean update(Customer customer) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE kunder " +
                            "SET " +
                            "for_navn = '" + customer.getFirstName()         + "', " +
                            "efter_navn = '" + customer.getLastName()     + "', " +
                            "adresse = '" + customer.getAddress()          + "', " +
                            "post_nummer = '" + customer.getPostalCode()   + "', " +
                            "by_navn = '" + customer.getCity()               + "', " +
                            "email = '" + customer.getEmail()              + "', " +
                            "mobil = '" + customer.getPhoneNumber()              + "', " +
                            "cpr = '" + customer.getCpr()                  + "', " +
                            "reg_nummer = '" + customer.getRegNumber()     + "', " +
                            "konto_nummer = '" + customer.getAccountNumber() + "' " +
                            "WHERE cpr = " + customer.getCpr() + ";";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med cpr nummer: " + customer.getCpr());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(int cpr) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM kunder WHERE cpr = " + cpr + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette kunde med cpr nummer: " + cpr);
            return false;
        }
        return true;
    }
}
