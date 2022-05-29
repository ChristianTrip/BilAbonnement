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
            String sql = "INSERT INTO brugere(`bruger_type`, `bruger_navn`, `adgangskode`) " +
                    "VALUES ('" + user.getUserType() + "', " +
                    "'" + user.getName() + "', " +
                    "'" + user.getPassword() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bruger med id " + user.getId() + " i databasen");
        }

        return false;
    }

    @Override
    public User getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM brugere WHERE bruger_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                int bruger_id = rs.getInt(1);
                String bruger_type = rs.getString(2);
                String navn = rs.getString(3);
                String adgangskode = rs.getString(4);

                UserType type;
                type = UserType.valueOf(bruger_type);

                return new User(bruger_id, navn, adgangskode, type);
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

        ArrayList<User> users = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM brugere;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int bruger_id = rs.getInt(1);
                String bruger_type = rs.getString(2);
                String navn = rs.getString(3);
                String adgangskode = rs.getString(4);

                UserType type;
                type = UserType.valueOf(bruger_type);

                User user = new User(bruger_id, navn, adgangskode, type);

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
            String sql = "UPDATE brugere " +
                    "SET " +
                    "bruger_navn = '" + user.getName() + "', " +
                    "adgangskode = '" + user.getPassword() + "' " +
                    "WHERE bruger_id = " + user.getId() + ";";
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
            String sql = "DELETE FROM brugere WHERE bruger_id = " + id + ";";
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
