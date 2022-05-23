package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.abonnementer.UnlimitedAbonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LejeaftaleRepo implements CRUDInterface <Lejeaftale>{


    /*
    design af oprettelsen af lejeaftale repo
    database hierarki (foreign key)
    connection bliver kaldt hver gang
    session - have en forbindelse hele session
     */


    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;


    @Override
    public boolean create(Lejeaftale lejeaftale) {

        java.sql.Date mySQLDate = new java.sql.Date(lejeaftale.getOprettelsesDato().getTime());

        boolean allIsWell = false;
        try{
            String sql = "INSERT INTO lejeaftaler(`oprettelsesdato`, `kunde_cpr`, `bil_stel_nummer`) " +
                    "VALUES ('" + mySQLDate + "', " +
                            "'" + lejeaftale.getKunde().getCpr() + "', " +
                            "'" + lejeaftale.getBil().getStelnummer() + "');";


            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int lejeaftaleId = getLastIndex();

            Kunde kunde = lejeaftale.getKunde();
            allIsWell = insertKunde(kunde);

            Bil bil = lejeaftale.getBil();
            bil.setLejeaftaleId(lejeaftaleId);
            allIsWell = insertBil(bil);

            Abonnement abonnement = lejeaftale.getAbonnement();
            abonnement.setLejeaftaleId(lejeaftaleId);
            allIsWell = insertAbonnement(abonnement);

            AfhentningsSted afhentningsSted = lejeaftale.getAfhentningsSted();
            afhentningsSted.setLejeaftaleId(lejeaftaleId);
            allIsWell = insertAfhentningssted(afhentningsSted);

            Prisoverslag prisoverslag = lejeaftale.getPrisoverslag();
            prisoverslag.setLejeaftaleId(lejeaftaleId);
            allIsWell = insertPrisoverslag(prisoverslag);

            Tilstandsrapport tilstandsrapport = new Tilstandsrapport(lejeaftaleId);
            tilstandsrapport.setLejeaftaleId(lejeaftaleId);
            allIsWell = insertTilstandsrapport(tilstandsrapport);

        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return allIsWell;
    }

    @Override
    public Lejeaftale getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM lejeaftaler WHERE lejeaftale_id = '" + id + "';";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int lejeaftale_id = rs.getInt(1);
                Date oprettelsesdato = rs.getDate(2);
                String kundeCPR = rs.getString(3);
                String bilStelNummer = rs.getString(4);

                Kunde kunde = getKunde(kundeCPR);
                Bil bil = getBil(bilStelNummer);
                Tilstandsrapport tilstandsrapport = getTilstandsrapport(lejeaftale_id);
                Abonnement abonnement = getAbonnement(id);
                Prisoverslag prisoverslag = getPrisoverslag(id);
                AfhentningsSted afhentningsSted = getAfhentningssted(id);


                return new Lejeaftale(lejeaftale_id, kunde, bil, tilstandsrapport, abonnement, prisoverslag, afhentningsSted, oprettelsesdato);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde lejeaftale med id: " + id);
        }
        return null;
    }

    @Override
    public List<Lejeaftale> getAllEntities() {
        ArrayList<Lejeaftale> lejeaftaler = new ArrayList<>();

        try {
            String sql = "SELECT * FROM lejeaftaler;";
            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()){
                int lejeaftaleId = rs.getInt(1);

                lejeaftaler.add(getSingleEntityById(lejeaftaleId));
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunder");
        }
        return lejeaftaler;
    }

    @Override
    public boolean update(Lejeaftale lejeaftale) {
       /* try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE lejeaftaler " +
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

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med cpr nummer: " + kunde.getCpr());
            return false;
        }*/

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        // hvis en kunde har flere lejeaftaler skal den ikke slette kunden..

        return false;
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

    // metoder der indsætter elementerne der hører til en lejeaftale

    private boolean insertBil(Bil bil){

        try{
            String sql = "INSERT IGNORE biler(`lejeaftale_id`, `bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + bil.getLejeaftaleId() + "', " +
                    "'" + bil.getStelnummer() + "', " +
                    "'" + bil.getName() + "', " +
                    "'" + bil.getModel() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + bil.getStelnummer() + " i databasen");
        }
        return false;
    }

    private boolean insertKunde(Kunde kunde){

        try{
            String sql = "INSERT IGNORE INTO kunder(`for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by_navn`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
                    "VALUES ('" + kunde.getFornavn() + "', " +
                    "'" + kunde.getEfternavn() + "', " +
                    "'" + kunde.getAdresse() + "', " +
                    "'" + kunde.getPostnummer() + "', " +
                    "'" + kunde.getBy() + "', " +
                    "'" + kunde.getEmail() + "', " +
                    "'" + kunde.getMobil() + "', " +
                    "'" + kunde.getCpr() + "', " +
                    "'" + kunde.getRegNummer() + "', " +
                    "'" + kunde.getKontoNummer() + "');";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette kunde med cpr nummer " + kunde.getCpr() + " i databasen");
        }
        return false;
    }

    private boolean insertAbonnement(Abonnement abonnement){

        boolean isLimited = true;
        if (abonnement.getClass().equals(UnlimitedAbonnement.class)){
            isLimited = false;
        }

        try{
            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `afleveringsforsikring`, `lejeperiode_mdr`, `valgt_farve`, `is_limited`) " +
                    "VALUES (" +
                    "'" + abonnement.getLejeaftaleId() + "', " +
                    ""  + abonnement.isLavSelvrisiko() + ", " +
                    ""  + abonnement.isAfleveringsforsikring() + ", " +
                    "'" + abonnement.getLejeperiodeMdr() + "', " +
                    "" + abonnement.isValgtFarve() + ", " +
                    " " + isLimited + ");";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette abonnement tilhørende lejeaftale med id " + abonnement.getLejeaftaleId() + " i databasen");
        }
        return false;
    }

    private boolean insertAfhentningssted(AfhentningsSted afhentningsSted) {

        try{
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + afhentningsSted.getLejeaftaleId() + "', " +
                    "'" + afhentningsSted.getAdresse() + "', " +
                    "'" + afhentningsSted.getPostnummer() + "', " +
                    "'" + afhentningsSted.getBy() + "', " +
                    "'" + afhentningsSted.getLeveringspris() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette afhentningsted med tilhørende lejeaftale id " + afhentningsSted.getLejeaftaleId() + " i databasen");
        }
        return false;
    }

    private boolean insertPrisoverslag(Prisoverslag prisoverslag) {
        try{
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `abonnements_længde`, `km_pr_mdr`, `totalpris`) " +
                    "VALUES (" +
                    "'" + prisoverslag.getLejeaftaleId() + "', " +
                    "'" + prisoverslag.getAbonnementsLaengde() + "', " +
                    "'" + prisoverslag.getKmPrMdr() + "', " +
                    "'" + prisoverslag.getTotalpris() + "');";


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

    private boolean insertTilstandsrapport(Tilstandsrapport tilstandsrapport) {

        try{
            String sql = "INSERT INTO tilstandsrapporter(`lejeaftale_id`) " +
                    "VALUES ('" + tilstandsrapport.getLejeaftaleId() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette tilstandsrapport tilhørende lejeaftale id: " + tilstandsrapport.getLejeaftaleId() + " i databasen");
        }
        return false;
    }

    // metoder der henter elementerne der hører til en lejeaftale

    private Bil getBil(String stelnummer) {

        try {
            String sql = "SELECT * FROM biler WHERE bil_stelnummer = '" + stelnummer + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){

                int bil_id = rs.getInt(1);
                String bil_stelnummer = rs.getString(2);
                String bil_navn = rs.getString(3);
                String bil_model = rs.getString(4);

                return new Bil(bil_id, bil_stelnummer, bil_navn, bil_model);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde bil med stelnummer: " + stelnummer);
        }
        return null;
    }

    private Kunde getKunde(String cpr) {

        try {
            String sql = "SELECT * FROM kunder WHERE cpr = '" + cpr + "';";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                String for_navn = rs.getString(1);
                String efter_navn = rs.getString(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                String email = rs.getString(6);
                String mobil = rs.getString(7);
                String cpr_nummer = rs.getString(8);
                String reg_nummer = rs.getString(9);
                String konto_nummer = rs.getString(10);

                return new Kunde(for_navn, efter_navn, adresse, post_nummer, by_navn, email, mobil, cpr_nummer, reg_nummer, konto_nummer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med cpr nummer: " + cpr);
        }
        return null;
    }

    private Abonnement getAbonnement(int lejeaftaleId) {

        try {
            String sql = "SELECT * FROM abonnementer WHERE lejeaftale_id = '" + lejeaftaleId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int abonnement_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                boolean lav_selvrisiko = rs.getBoolean(3);
                boolean afleveringsforsikring = rs.getBoolean(4);
                int lejeperiode_mdr = rs.getInt(5);
                boolean valgt_farve = rs.getBoolean(6);
                boolean is_limited = rs.getBoolean(7);


                if (is_limited){
                    return new LimitedAbonnement(abonnement_id, lejeaftaleId, lav_selvrisiko, valgt_farve);
                }
                else{
                    return new UnlimitedAbonnement(abonnement_id, lejeaftaleId, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement med id: " + lejeaftaleId);
        }
        return null;
    }

    private AfhentningsSted getAfhentningssted(int lejeaftaleId) {

        try {
            String sql = "SELECT * FROM afhentningssteder WHERE lejeaftale_id = '" + lejeaftaleId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int afhentningssted_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                int levering = rs.getInt(6);

                return new AfhentningsSted(afhentningssted_id, adresse, post_nummer, by_navn, levering);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde afhentningssted tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }

    private Prisoverslag getPrisoverslag(int lejeaftaleId) {

        try {
            String sql = "SELECT * FROM prisoverslag WHERE lejeaftale_id = '" + lejeaftaleId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                return new Prisoverslag(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde prisoverslag tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }


    private Tilstandsrapport getTilstandsrapport(int lejeaftaleId) {
        try {
            String sql = "SELECT * FROM tilstandsrapporter WHERE lejeaftale_id = '" + lejeaftaleId + "';";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);


                return new Tilstandsrapport(tilstandsrapport_id, lejeaftale_id);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapport tilhørende lejeaftale id: " + lejeaftaleId);
        }
        return null;
    }



    public static void main(String[] args) {
        LejeaftaleRepo repo = new LejeaftaleRepo();

        Kunde kunde = new Kunde("John", "Andersen", "Holmbladsgade 30", "2300", "Kbh S", "Johnandersen@mail.dk", "12345678", "0910883485", "1234", "1234567890");
        Bil bil = new Bil("ZW00003344KL", "Fiat", "grand");

        Abonnement abonnement = new LimitedAbonnement(true, true);
        Prisoverslag prisoverslag = new Prisoverslag(3, 4000, 6999);
        AfhentningsSted afhentningsSted = new AfhentningsSted("Lergravsvej 3", "2300", "København S", 300);
        Lejeaftale lejeaftale = new Lejeaftale(kunde, bil, null, abonnement, prisoverslag, afhentningsSted);



    }


}
