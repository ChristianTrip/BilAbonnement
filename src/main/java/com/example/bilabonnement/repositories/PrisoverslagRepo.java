package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PrisoverslagRepo implements CRUDInterface<Prisoverslag> {


    @Override
    public boolean create(Prisoverslag prisoverslag) {
        try{
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `total_pris`, `abonnements_længde`) " +
                    "VALUES (" +
                    "'" + prisoverslag.getLejeaftaleId() + "', " +
                    "'" + prisoverslag.getTotalpris() + "', " +
                    "'" + prisoverslag.getAbonnementsLaengde() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
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
    public Prisoverslag getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Prisoverslag> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Prisoverslag prisoverslag) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
