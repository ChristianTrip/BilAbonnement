package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Kunde;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class KundeRepo implements CRUDInterface{

    @Override
    public boolean create(Object entity) {

        Kunde kunde = (Kunde) entity;

        try{
            String sql = "INSERT INTO kunder(`for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
                    "VALUES ('" + kunde.getForNavn() + "', " +
                    "'" + kunde.getEfterNavn() + "', " +
                    "'" + kunde.getAdresse() + "', " +
                    "'" + kunde.getPostNummer() + "', " +
                    "'" + kunde.getBy() + "', " +
                    "'" + kunde.getEmail() + "', " +
                    "'" + kunde.getMobil() + "', " +
                    "'" + kunde.getCpr() + "', " +
                    "'" + kunde.getRegNummer() + "', " +
                    "'" + kunde.getKontoNummer() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    

    @Override
    public Object getSingleEntityById(int id) {
        return null;
    }

    @Override
    public List getAllEntities() {
        return null;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
