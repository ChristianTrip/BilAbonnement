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
import java.util.Date;
import java.util.List;

public class LejeaftaleRepo implements CRUDInterface <Lejeaftale>{


    /*
    design af oprettelsen af lejeaftale repo
    database hierarki (foreign key)
    connection bliver kaldt hver gang
    session - have en forbindelse hele session
     */


    private BilRepo bilRepo = new BilRepo();
    private KundeRepo kundeRepo = new KundeRepo();
    private TilstandsRapportRepo tilstandsRapportRepo = new TilstandsRapportRepo();
    private AbonnementRepo abonnementRepo = new AbonnementRepo();
    private AfhentningsstedRepo afhentningsstedRepo = new AfhentningsstedRepo();
    private PrisoverslagRepo prisoverslagRepo = new PrisoverslagRepo();

    private Connection conn;


    @Override
    public boolean create(Lejeaftale lejeaftale) {

        java.sql.Date mySQLDate = new java.sql.Date(lejeaftale.getOprettelsesDato().getTime());

        conn = DatabaseConnectionManager.getConnection();

        try{
            String sql = "INSERT INTO lejeaftaler(`oprettelsesdato`, `kunde_cpr`, `bil_stel_nummer`) " +
                    "VALUES ('" + mySQLDate + "', " +
                            "'" + lejeaftale.getKunde().getCpr() + "', " +
                            "'" + lejeaftale.getBil().getStelNummer() + "');";



            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();


            int lejeaftaleId = getLastIndex();

            Kunde kunde = lejeaftale.getKunde();
            insertKunde(kunde);

            Bil bil = lejeaftale.getBil();
            bil.setLejeaftaleId(lejeaftaleId);
            insertBil(bil);

            Tilstandsrapport tilstandsrapport = lejeaftale.getTilstandsrapport();
            tilstandsrapport.setLejeaftaleId(lejeaftaleId);
            tilstandsRapportRepo.create(tilstandsrapport);

            Abonnement abonnement = lejeaftale.getAbonnement();
            abonnement.setLejeaftaleId(lejeaftaleId);
            insertAbonnement(abonnement);

            AfhentningsSted afhentningsSted = lejeaftale.getAfhentningsSted();
            afhentningsSted.setLejeaftaleId(lejeaftaleId);
            insertAfhentningssted(afhentningsSted);

            Prisoverslag prisoverslag = lejeaftale.getPrisoverslag();
            prisoverslag.setLejeaftaleId(lejeaftaleId);
            insertPrisoverslag(prisoverslag);

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();

        }

        return false;
    }

    @Override
    public Lejeaftale getSingleEntityById(int id) {

        try {
            String sql = "SELECT * FROM lejeaftaler WHERE lejeaftale_id = '" + id + "';";

            conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int lejeaftale_id = rs.getInt(1);
                Date oprettelsesdato = rs.getDate(2);
                String kundeCPR = rs.getString(3);

                Kunde kunde = getKunde(kundeCPR);
                Bil bil = getBil(id);
                Tilstandsrapport tilstandsrapport = null;
                Abonnement abonnement = getAbonnement(id);
                Prisoverslag prisoverslag = null;
                AfhentningsSted afhentningsSted = null;


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
        return null;
    }

    @Override
    public boolean update(Lejeaftale lejeaftale) {
        return false;
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

    private void insertBil(Bil bil){

        try{
            String sql = "INSERT INTO biler(`lejeaftale_id`, `bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + bil.getLejeaftaleId() + "', " +
                    "'" + bil.getStelNummer() + "', " +
                    "'" + bil.getName() + "', " +
                    "'" + bil.getModel() + "');";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + bil.getStelNummer() + " i databasen");
        }
    }

    private void insertKunde(Kunde kunde){

        try{
            String sql = "INSERT IGNORE INTO kunder(`for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by_navn`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
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


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette kunde med cpr nummer " + kunde.getCpr() + " i databasen");
        }
    }

    private void insertAbonnement(Abonnement abonnement){

        int lavSelrisiko = 0;
        if (abonnement.isLavSelvrisiko()){
            lavSelrisiko = 1;
        }

        try{
            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `lejeperiode_mdr`) " +
                    "VALUES (" +
                    "'" + abonnement.getLejeaftaleId() + "', " +
                    "'" + lavSelrisiko + "', " +
                    "'" + abonnement.getLejeperiodeMdr() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette abonnement tilhørende lejeaftale med id " + abonnement.getLejeaftaleId() + " i databasen");
        }

    }

    private void insertAfhentningssted(AfhentningsSted afhentningsSted) {

        try{
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + afhentningsSted.getLejeaftaleId() + "', " +
                    "'" + afhentningsSted.getAdresse() + "', " +
                    "'" + afhentningsSted.getPostNummer() + "', " +
                    "'" + afhentningsSted.getBy() + "', " +
                    "'" + afhentningsSted.getLevering() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette afhentningsted med tilhørende lejeaftale id " + afhentningsSted.getLejeaftaleId() + " i databasen");
        }
    }

    private void insertPrisoverslag(Prisoverslag prisoverslag) {
        try{
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `total_pris`, `abonnements_længde`) " +
                    "VALUES (" +
                    "'" + prisoverslag.getLejeaftaleId() + "', " +
                    "'" + prisoverslag.getTotalPris() + "', " +
                    "'" + prisoverslag.getAbonnementsLængde() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette prisoverslag med tilhørende lejeaftale id " + prisoverslag.getLejeaftaleId() + " i databasen");
        }
    }


    // metoder der henter elementerne der hører til en lejeaftale

    private Bil getBil(int id) {

        try {
            String sql = "SELECT * FROM biler WHERE lejeaftale_id = '" + id + "';";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

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
            System.out.println("Kunne ikke finde bil med id: " + id);
        }
        return null;
    }

    private Kunde getKunde(String cpr) {

        try {
            String sql = "SELECT * FROM kunder WHERE cpr = '" + cpr + "';";

            conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

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

    private Abonnement getAbonnement(int id) {

        try {
            String sql = "SELECT * FROM abonnementer WHERE lejeaftale_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int abonnement_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                boolean lav_selvrisiko = rs.getBoolean(3);
                boolean afleveringsforsikring = rs.getBoolean(4);
                int lejeperiode_mdr = rs.getInt(5);
                boolean is_limited = rs.getBoolean(6);



                if (is_limited){
                    return new LimitedAbonnement(abonnement_id, lav_selvrisiko);
                }
                else{
                    return new UnlimitedAbonnement(abonnement_id, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement med id: " + id);
        }
        return null;
    }



    public static void main(String[] args) {
        LejeaftaleRepo repo = new LejeaftaleRepo();

        Kunde kunde = new Kunde("John", "Andersen", "Holmbladsgade 30", "2300", "Kbh S", "Johnandersen@mail.dk", "12345678", "0910883485", "1234", "1234567890");
        Bil bil = new Bil("ZW00003344KL", "Fiat", "grand");
        Tilstandsrapport tilstandsrapport = new Tilstandsrapport();
        Abonnement abonnement = new LimitedAbonnement(true);
        Prisoverslag prisoverslag = new Prisoverslag(4000, 3);
        AfhentningsSted afhentningsSted = new AfhentningsSted("Lergravsvej 3", "2300", "København S", 300);
        //repo.create(lejeaftale);

        Kunde lars_allan = repo.getKunde("1204887375");
        Lejeaftale lejeaftale = new Lejeaftale(lars_allan, bil, tilstandsrapport, abonnement, prisoverslag, afhentningsSted);

        repo.create(lejeaftale);

        System.out.println(repo.getSingleEntityById(1));

    }


}
