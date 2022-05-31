package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.leaseAgreements.Customer;
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
            String sql = "INSERT INTO customers(`agreement_id`, `first_name`, `last_name`, `address`, `postal_code`, `city`, `email`, `phone`, `cpr`, `reg_number`, `account_number`) " +
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
            System.out.println("Could not create customer with cpr number '" + customer.getCpr()+ "' in database");
        }

        return false;
    }

    @Override
    public Customer getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM customers WHERE customer_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String address = rs.getString(4);
                String postalCode = rs.getString(5);
                String city = rs.getString(6);
                String email = rs.getString(7);
                String phone = rs.getString(8);
                String cpr = rs.getString(9);
                String regNumber = rs.getString(10);
                String accountNuber = rs.getString(11);

                return new Customer(customerId, firstName, lastName, address, postalCode, city, email, phone, cpr, regNumber, accountNuber);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find customer with id '" + id + "' in database");
        }
        return null;
    }

    @Override
    public List<Customer> getAllEntities() {

        ArrayList<Customer> customers = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM customers;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String address = rs.getString(4);
                String postalCode = rs.getString(5);
                String city = rs.getString(6);
                String email = rs.getString(7);
                String phone = rs.getString(8);
                String cpr = rs.getString(9);
                String regNumber = rs.getString(10);
                String accountNuber = rs.getString(11);

                Customer customer = new Customer(customerId, firstName, lastName, address, postalCode, city, email, phone, cpr, regNumber, accountNuber);
                customers.add(customer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not retrieve customers from database");
        }
        return customers;
    }

    @Override
    public boolean update(Customer customer) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE customers " +
                            "SET " +
                            "first_name = '" + customer.getFirstName()         + "', " +
                            "last_name = '" + customer.getLastName()     + "', " +
                            "address = '" + customer.getAddress()          + "', " +
                            "postal_code = '" + customer.getPostalCode()   + "', " +
                            "city = '" + customer.getCity()               + "', " +
                            "email = '" + customer.getEmail()              + "', " +
                            "phone = '" + customer.getPhoneNumber()              + "', " +
                            "cpr = '" + customer.getCpr()                  + "', " +
                            "reg_number = '" + customer.getRegNumber()     + "', " +
                            "account_number = '" + customer.getAccountNumber() + "' " +
                            "WHERE cpr = '" + customer.getCpr() + "';";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not update customer with cpr number '" + customer.getCpr() + "'");
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM customers WHERE customer_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not delete customer with id number '" + id + "' from database");
            return false;
        }
        return true;
    }

}
