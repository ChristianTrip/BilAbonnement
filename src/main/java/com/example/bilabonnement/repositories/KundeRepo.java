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
            String sql = "INSERT INTO kunder(`lejeaftale_id`, `for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by_navn`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
                    "VALUES ('" + kunde.getLejeaftaleId() + "', " +
                    "'" + kunde.getForNavn() + "', " +
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
            System.out.println("Kunne ikke oprette bruger med cpr nummer " + kunde.getCpr() + " i databasen");
        }

        return false;
    }

    @Override
    public Kunde getSingleEntityById(int cpr) {

        try {
            String sql = "SELECT * FROM kunder WHERE cpr = '" + cpr + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

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

                return new Kunde(for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr_nummer, reg_nummer, konto_nummer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med cpr nummer: " + cpr);
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

                Kunde kunde = new Kunde(for_navn, efter_navn, adresse, post_nummer, by, email, mobil, cpr, reg_nummer, konto_nummer);
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
                            "WHERE cpr = " + kunde.getCpr() + ";";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med cpr nummer: " + kunde.getCpr());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int cpr) {

        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM kunder WHERE cpr = " + cpr + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette kunde med cpr nummer: " + cpr);
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        KundeRepo repo = new KundeRepo();
        Kunde kunde = new Kunde("James", "Bond", "Amagerbrogade 69", "2300", "København S", "bond007@agent.mail.dk", "xxxxxxxx", "0812882395", "1234", "12345678");
        repo.create(kunde);
        Kunde hans = repo.getSingleEntityById(4);
        hans.setBy("København S");
        repo.update(hans);
    }
}
