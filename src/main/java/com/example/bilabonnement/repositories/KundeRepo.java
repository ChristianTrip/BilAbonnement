package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Kunde;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KundeRepo implements CRUDInterface <Kunde>{

    @Override
    public boolean create(Kunde kunde) {

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
            System.out.println("Kunne ikke oprette bruger med id " + kunde.getId() + " i databasen");
        }

        return false;
    }

    @Override
    public Kunde getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM kunder WHERE kunde_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int kunde_id = rs.getInt(1);
                String for_navn = rs.getString(2);
                String efter_navn = rs.getString(3);
                String adresse = rs.getString(4);
                int post_nummer = rs.getInt(5);
                String by = rs.getString(6);
                String email = rs.getString(7);
                int mobil = rs.getInt(8);
                String cpr = rs.getString(9);
                int reg_nummer = rs.getInt(10);
                int konto_nummer = rs.getInt(11);

                return new Kunde(kunde_id, for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr, reg_nummer, konto_nummer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med id: " + id);
        }
        return null;
    }

    @Override
    public List<Kunde> getAllEntities() {
        ArrayList<Kunde> kunder = new ArrayList<>();

        try {
            String sql = "SELECT * FROM kunder;";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int kunde_id = rs.getInt(1);
                String for_navn = rs.getString(2);
                String efter_navn = rs.getString(3);
                String adresse = rs.getString(4);
                int post_nummer = rs.getInt(5);
                String by = rs.getString(6);
                String email = rs.getString(7);
                int mobil = rs.getInt(8);
                String cpr = rs.getString(9);
                int reg_nummer = rs.getInt(10);
                int konto_nummer = rs.getInt(11);

                Kunde kunde = new Kunde(kunde_id, for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr, reg_nummer, konto_nummer);
                kunder.add(kunde);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunder");
        }
        return kunder;
    }

    @Override
    public boolean update(Kunde kunde) {
        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE kunder " +
                            "SET " +
                            "for_navn = '" + kunde.getForNavn()         + "', " +
                            "efter_navn = '" + kunde.getEfterNavn()     + "', " +
                            "adresse = '" + kunde.getAdresse()          + "', " +
                            "post_nummer = '" + kunde.getPostNummer()   + "', " +
                            "by_navn = '" + kunde.getBy()               + "', " +
                            "email = '" + kunde.getEmail()              + "', " +
                            "mobil = '" + kunde.getMobil()              + "', " +
                            "cpr = '" + kunde.getCpr()                  + "', " +
                            "reg_nummer = '" + kunde.getRegNummer()     + "', " +
                            "konto_nummer = '" + kunde.getKontoNummer() + "' " +
                            "WHERE kunde_id = " + kunde.getId()         + ";";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med id: " + kunde.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM kunder WHERE kunde_id = " + id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette kunde med id: " + id);
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        KundeRepo repo = new KundeRepo();
        //Kunde kunde = new Kunde("Hans", "Petersen", "Holmbladsgade 3", 2300, "København S", "hansP@mail.dk", 12345678, "0812882395", 1234, 12345678);
        Kunde hans = repo.getSingleEntityById(4);
        hans.setBy("Aarhus");
        repo.update(hans);
    }
}
