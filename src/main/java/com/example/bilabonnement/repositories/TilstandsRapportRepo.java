package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Tilstandsrapport;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TilstandsRapportRepo implements CRUDInterface<Tilstandsrapport>{

    @Override
    public boolean create(Tilstandsrapport tilstandsrapport) {
        try{
            String sql = "INSERT INTO tilstandsrapporter(`mangler_id`, `skader_id`) " +
                    "VALUES ('" + tilstandsrapport.getId() + "', " +
                    "'" + tilstandsrapport.getEfterNavn() + "', " +
                    "'" + tilstandsrapport.getAdresse() + "', " +
                    "'" + tilstandsrapport.getKontoNummer() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bruger med id " + tilstandsrapport.getId() + " i databasen");
        }

        return false;
    }


    @Override
    public Tilstandsrapport getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List<Tilstandsrapport> getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Tilstandsrapport tilstandsrapport) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
