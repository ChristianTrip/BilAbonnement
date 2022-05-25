package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TilstandsRapportRepo implements CRUDInterface<Tilstandsrapport>{

    private MangelRepo mangelRepo = new MangelRepo();
    private SkadeRepo skadeRepo = new SkadeRepo();

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public boolean create(Tilstandsrapport tilstandsrapport) {

        try{
            String sql = "INSERT INTO tilstandsrapporter(`lejeaftale_id`) " +
                    "VALUES ('" + tilstandsrapport.getLejeaftaleId() + "');";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int tilstandsrapportId = getLastIndex();

            for (Mangel mangel : tilstandsrapport.getMangler()) {
                mangel.setTilstandsRapportId(tilstandsrapportId);
                insertMangel(mangel);
            }

            for (Skade skade : tilstandsrapport.getSkader()) {
                skade.setTilstandsRapportId(tilstandsrapportId);
                insertSkade(skade);
            }


            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette tilstandsrapport i databasen");
        }

        return false;
    }


    @Override
    public Tilstandsrapport getSingleEntityById(int id) {
        try {
            String sql = "SELECT * FROM tilstandsrapporter WHERE tilstandsrapport_id = '" + id + "';";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);


                ArrayList<Mangel> mangler = getMangler(id);
                ArrayList<Skade> skader = getSkader(id);

                return new Tilstandsrapport(tilstandsrapport_id, lejeaftale_id, mangler, skader);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapport med id: " + id);
        }
        return null;
    }

    @Override
    public List<Tilstandsrapport> getAllEntities() {
        ArrayList<Tilstandsrapport> tilstandsrapporter = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tilstandsrapporter;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);

                tilstandsrapporter.add(getSingleEntityById(tilstandsrapport_id));
            }
            return tilstandsrapporter;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapporter");
        }
        return null;
    }

    @Override
    public boolean update(Tilstandsrapport tilstandsrapport) {


        try{
            conn = DatabaseConnectionManager.getConnection();


            ArrayList<Mangel> mangler = tilstandsrapport.getMangler();
            ArrayList<Skade> skader = tilstandsrapport.getSkader();

            for (Mangel mangel : mangler) {
                insertMangel(mangel);
            }
            for (Skade skade : skader) {
                insertSkade(skade);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        /*try{
            Connection conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE tilstandsrapporter " +
                    "SET " +
                    "for_navn = '" + kunde.getFornavn()         + "', " +
                    "efter_navn = '" + kunde.getEfternavn()     + "', " +
                    "adresse = '" + kunde.getAdresse()          + "', " +
                    "post_nummer = '" + kunde.getPostnummer()   + "', " +
                    "by_navn = '" + kunde.getBy()               + "', " +
                    "email = '" + kunde.getEmail()              + "', " +
                    "mobil = '" + kunde.getMobil()              + "', " +
                    "cpr = '" + kunde.getCpr()                  + "', " +
                    "reg_nummer = '" + kunde.getRegNummer()     + "', " +
                    "konto_nummer = '" + kunde.getKontoNummer() + "' " +
                    "WHERE cpr = " + kunde.getCpr() + ";";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();


            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere tilstandsrapport med id " + tilstandsrapport.getId());
        }*/
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try{

            Connection conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM tilstandsrapporter WHERE tilstandsrapport_id = " + id + ";";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette tilstandsrapport med id: " + id);
            return false;
        }

        return true;
    }

    private int getLastIndex(){

        try {
            String sql = "SELECT LAST_INSERT_ID();";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    private boolean insertSkade(Skade skade){

        try{
            String sql = "INSERT IGNORE skader(`tilstandsrapport_id`, `skade_navn`, `skade_beskrivelse`, `skade_pris`) " +
                    "VALUES (" +
                    "'" + skade.getTilstandsRapportId() + "', " +
                    "'" + skade.getNavn() + "', " +
                    "'" + skade.getBeskrivelse() + "', " +
                    "'" + skade.getPris() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette skade med tilstandsrapport Id " + skade.getTilstandsRapportId() + " i databasen");
        }
        return false;
    }


    private boolean insertMangel(Mangel mangel){

        try{
            String sql = "INSERT IGNORE mangler(`tilstandsrapport_id`, `mangel_navn`, `mangel_beskrivelse`, `mangel_pris`) " +
                    "VALUES (" +
                    "'" + mangel.getTilstandsRapportId() + "', " +
                    "'" + mangel.getNavn() + "', " +
                    "'" + mangel.getBeskrivelse() + "', " +
                    "'" + mangel.getPris() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette mangel med tilstandsrapport Id " + mangel.getTilstandsRapportId() + " i databasen");
        }
        return false;
    }


    private ArrayList<Skade> getSkader(int tilstandsrapportId) {

        ArrayList<Skade> skader = new ArrayList<>();

        try {
            String sql = "SELECT * FROM skader WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int skade_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String skade_navn = rs.getString(3);
                String skade_beskrivelse = rs.getString(4);
                int skade_pris = rs.getInt(5);

                skader.add(new Skade(skade_id, tilstandsrapport_id, skade_navn, skade_beskrivelse, skade_pris));
            }

            return skader;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }

    private ArrayList<Mangel> getMangler(int tilstandsrapportId) {

        ArrayList<Mangel> mangler = new ArrayList<>();

        try {
            String sql = "SELECT * FROM mangler WHERE tilstandsrapport_id = '" + tilstandsrapportId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int mangel_id = rs.getInt(1);
                int tilstandsrapport_id = rs.getInt(2);
                String mangel_navn = rs.getString(3);
                String mangel_beskrivelse = rs.getString(4);
                int mangel_pris = rs.getInt(5);

                mangler.add(new Mangel(mangel_id, tilstandsrapport_id, mangel_navn, mangel_beskrivelse, mangel_pris));
            }

            return mangler;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde skade tilhørende tilstandsrapport id: " + tilstandsrapportId);
        }
        return null;
    }




    public static void main(String[] args) {
        TilstandsRapportRepo repo = new TilstandsRapportRepo();

        ArrayList<Skade> skader = new ArrayList<>();
        ArrayList<Mangel> mangler = new ArrayList<>();
        skader.add(new Skade("flækket rude", "Bagruden er flæket i højre side, kræver udskiftning", 1000));
        mangler.add(new Mangel("Fodunderlag", "underlaget ved højre passagersæde mangler", 500));
        Tilstandsrapport tilstandsrapport = new Tilstandsrapport(1);
        tilstandsrapport.setSkade(skader);
        tilstandsrapport.setMangel(mangler);
        //repo.create(tilstandsrapport);


        System.out.println(repo.getAllEntities());
    }
}


