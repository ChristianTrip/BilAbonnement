package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.users.User;
import com.example.bilabonnement.models.users.UserType;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CRUDInterface<User>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(User user) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO users(`user_type`, `name`, `password`) " +
                    "VALUES ('" + user.getUserType() + "', " +
                    "'" + user.getName() + "', " +
                    "'" + user.getPassword() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette user med id " + user.getId() + " i databasen");
        }

        return false;
    }

    @Override
    public User getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM users WHERE user_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                int userId = rs.getInt(1);
                String userType = rs.getString(2);
                String name = rs.getString(3);
                String password = rs.getString(4);

                UserType type;
                type = UserType.valueOf(userType);

                return new User(userId, name, password, type);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde user med id: " + id);
        }
        return null;
    }

    @Override
    public List getAllEntities() {

        ArrayList<User> users = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM users;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int userId = rs.getInt(1);
                String userType = rs.getString(2);
                String name = rs.getString(3);
                String password = rs.getString(4);

                UserType type;
                type = UserType.valueOf(userType);

                User user = new User(userId, name, password, type);

                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde users");
        }
        return users;
    }

    @Override
    public boolean update(User user) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE users " +
                    "SET " +
                    "name = '" + user.getName() + "', " +
                    "password = '" + user.getPassword() + "' " +
                    "WHERE user_id = " + user.getId() + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med id: " + user.getId());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM users WHERE user_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette kunde med id: " + id);
            return false;
        }
        return true;
    }

}
