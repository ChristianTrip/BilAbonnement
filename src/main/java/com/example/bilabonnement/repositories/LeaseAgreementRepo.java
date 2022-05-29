package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.priceEstimates.PriceEstimate;
import com.example.bilabonnement.models.subscriptions.Subscription;
import com.example.bilabonnement.models.subscriptions.LimitedSubscription;
import com.example.bilabonnement.models.subscriptions.UnlimitedSubscription;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaseAgreementRepo implements CRUDInterface <LeaseAgreement>{

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;


    @Override
    public boolean create(LeaseAgreement leaseAgreement) {

        java.sql.Date approvalDate = java.sql.Date.valueOf(leaseAgreement.getApprovalDate());
        java.sql.Date startDate = java.sql.Date.valueOf(leaseAgreement.getStartDate());

        boolean allIsWell = true;
        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO lejeaftaler(`oprettelsesdato`,`startdato`, `kunde_cpr`, `bil_stel_nummer`) " +
                    "VALUES ('" + approvalDate + "', " +
                            "'" + startDate + "', " +
                            "'" + leaseAgreement.getCustomer().getCpr() + "', " +
                            "'" + leaseAgreement.getCar().getChassisNumber() + "');";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int lejeaftaleId = getLastIndex();

            Customer customer = leaseAgreement.getCustomer();

            Car car = leaseAgreement.getCar();
            car.setAgreementId(lejeaftaleId);

            Subscription subscription = leaseAgreement.getSubscription();
            subscription.setAgreementId(lejeaftaleId);

            PickupPlace pickupPlace = leaseAgreement.getPickupPlace();
            pickupPlace.setAgreementId(lejeaftaleId);

            PriceEstimate priceEstimate = leaseAgreement.getPriceEstimate();
            priceEstimate.setAgreementId(lejeaftaleId);

            SurveyReport surveyReport = new SurveyReport(lejeaftaleId);
            surveyReport.setAgreementId(lejeaftaleId);


            if (!insertCustomer(customer) ||
                !insertCar(car) ||
                !insertSubscription(subscription) ||
                !insertPickupPlace(pickupPlace) ||
                !insertPriceEstimate(priceEstimate) ||
                !insertSurveyReport(surveyReport))
            {
                allIsWell = false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return allIsWell;
    }

    @Override
    public LeaseAgreement getSingleEntityById(int id) {

        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM lejeaftaler WHERE lejeaftale_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int lejeaftale_id = rs.getInt(1);
                java.sql.Date oprettelsesdato = rs.getDate(2);
                java.sql.Date startdato = rs.getDate(3);
                String kundeCPR = rs.getString(4);
                String bilStelNummer = rs.getString(5);

                Customer customer = getCustomer(kundeCPR);
                Car car = getCar(bilStelNummer);
                SurveyReport surveyReport = getSurveyReport(lejeaftale_id);
                Subscription subscription = getSubscription(id);
                PriceEstimate priceEstimate = getPriceEstimate(id);
                PickupPlace pickupPlace = getPickupPlace(id);

                return new LeaseAgreement(lejeaftale_id, customer, car, surveyReport, subscription, priceEstimate, pickupPlace, oprettelsesdato.toLocalDate(), startdato.toLocalDate());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde lejeaftale med id: " + id);
        }
        return null;
    }

    @Override
    public List<LeaseAgreement> getAllEntities() {

        ArrayList<LeaseAgreement> leaseAgreements = new ArrayList<>();
        try {
            conn = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM lejeaftaler;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int lejeaftaleId = rs.getInt(1);

                leaseAgreements.add(getSingleEntityById(lejeaftaleId));
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunder");
        }
        return leaseAgreements;
    }

    @Override
    public boolean update(LeaseAgreement leaseAgreement) {

        Car car = leaseAgreement.getCar();
        Customer customer = leaseAgreement.getCustomer();
        Subscription subscription = leaseAgreement.getSubscription();
        PickupPlace pickupPlace = leaseAgreement.getPickupPlace();
        SurveyReport surveyReport = leaseAgreement.getSurveyReport();
        PriceEstimate priceEstimate = leaseAgreement.getPriceEstimate();


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

    private boolean insertCar(Car car){

        try{
            String sql = "INSERT IGNORE biler(`lejeaftale_id`, `bil_stelnummer`, `bil_name`, `bil_model`) " +
                    "VALUES (" +
                    "'" + car.getAgreementId() + "', " +
                    "'" + car.getChassisNumber() + "', " +
                    "'" + car.getBrand() + "', " +
                    "'" + car.getModel() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bil med stelnummer " + car.getChassisNumber() + " i databasen");
        }
        return false;
    }

    private boolean insertCustomer(Customer customer){

        try{
            String sql = "INSERT IGNORE INTO kunder(`for_navn`, `efter_navn`, `adresse`, `post_nummer`, `by_navn`, `email`, `mobil`, `cpr`, `reg_nummer`, `konto_nummer`) " +
                    "VALUES ('" + customer.getFirstName() + "', " +
                    "'" + customer.getLastName() + "', " +
                    "'" + customer.getAddress() + "', " +
                    "'" + customer.getPostalCode() + "', " +
                    "'" + customer.getCity() + "', " +
                    "'" + customer.getEmail() + "', " +
                    "'" + customer.getPhoneNumber() + "', " +
                    "'" + customer.getCpr() + "', " +
                    "'" + customer.getRegNumber() + "', " +
                    "'" + customer.getAccountNumber() + "');";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette kunde med cpr nummer " + customer.getCpr() + " i databasen");
        }
        return false;
    }

    private boolean insertSubscription(Subscription subscription){

        boolean isLimited = true;
        if (subscription.getClass().equals(UnlimitedSubscription.class)){
            isLimited = false;
        }

        try{
            String sql = "INSERT INTO abonnementer(`lejeaftale_id`, `lav_selvrisiko`, `afleveringsforsikring`, `lejeperiode_mdr`, `valgt_farve`, `is_limited`) " +
                    "VALUES (" +
                    "'" + subscription.getAgreementId() + "', " +
                    ""  + subscription.hasLowDeductible() + ", " +
                    ""  + subscription.hasDeliveryInsurance() + ", " +
                    "'" + subscription.getLengthInMonths() + "', " +
                    "" + subscription.hasStandardColor() + ", " +
                    " " + isLimited + ");";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette abonnement tilhørende lejeaftale med id " + subscription.getAgreementId() + " i databasen");
        }
        return false;
    }

    private boolean insertPickupPlace(PickupPlace pickupPlace) {

        try{
            String sql = "INSERT INTO afhentningssteder(`lejeaftale_id`, `adresse`, `post_nummer`, `by_navn`, `levering`) " +
                    "VALUES (" +
                    "'" + pickupPlace.getAgreementId() + "', " +
                    "'" + pickupPlace.getAddress() + "', " +
                    "'" + pickupPlace.getPostalCode() + "', " +
                    "'" + pickupPlace.getCity() + "', " +
                    "'" + pickupPlace.getDeliveryPrice() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette afhentningsted med tilhørende lejeaftale id " + pickupPlace.getAgreementId() + " i databasen");
        }
        return false;
    }

    private boolean insertPriceEstimate(PriceEstimate priceEstimate) {
        try{
            String sql = "INSERT INTO prisoverslag(`lejeaftale_id`, `abonnements_længde`, `km_pr_mdr`, `totalpris`) " +
                    "VALUES (" +
                    "'" + priceEstimate.getAgreementId() + "', " +
                    "'" + priceEstimate.getSubscriptionLength() + "', " +
                    "'" + priceEstimate.getkmPerMonth() + "', " +
                    "'" + priceEstimate.getTotalPrice() + "');";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette prisoverslag med tilhørende lejeaftale id " + priceEstimate.getAgreementId() + " i databasen");
        }
        return false;
    }

    private boolean insertSurveyReport(SurveyReport surveyReport) {

        try{
            String sql = "INSERT INTO tilstandsrapporter(`lejeaftale_id`) " +
                    "VALUES ('" + surveyReport.getAgreementId() + "');";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette tilstandsrapport tilhørende lejeaftale id: " + surveyReport.getAgreementId() + " i databasen");
        }
        return false;
    }

    // metoder der henter elementerne der hører til en lejeaftale

    private Car getCar(String chassisNumber) {

        try {
            String sql = "SELECT * FROM biler WHERE bil_stelnummer = '" + chassisNumber + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){

                int bil_id = rs.getInt(1);
                String bil_stelnummer = rs.getString(2);
                String bil_navn = rs.getString(3);
                String bil_model = rs.getString(4);

                return new Car(bil_id, bil_stelnummer, bil_navn, bil_model);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde bil med stelnummer: " + chassisNumber);
        }
        return null;
    }

    private Customer getCustomer(String cpr) {

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

                return new Customer(for_navn, efter_navn, adresse, post_nummer, by_navn, email, mobil, cpr_nummer, reg_nummer, konto_nummer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med cpr nummer: " + cpr);
        }
        return null;
    }

    private Subscription getSubscription(int AgreementId) {

        try {
            String sql = "SELECT * FROM abonnementer WHERE lejeaftale_id = '" + AgreementId + "';";

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
                    return new LimitedSubscription(abonnement_id, lejeaftale_id, lav_selvrisiko, valgt_farve);
                }
                else{
                    return new UnlimitedSubscription(abonnement_id, lejeaftale_id, lejeperiode_mdr, lav_selvrisiko, afleveringsforsikring, valgt_farve);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde abonnement med id: " + AgreementId);
        }
        return null;
    }

    private PickupPlace getPickupPlace(int agreementId) {

        try {
            String sql = "SELECT * FROM afhentningssteder WHERE lejeaftale_id = '" + agreementId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int afhentningssted_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                String adresse = rs.getString(3);
                String post_nummer = rs.getString(4);
                String by_navn = rs.getString(5);
                int levering = rs.getInt(6);

                return new PickupPlace(afhentningssted_id, lejeaftale_id, adresse, post_nummer, by_navn, levering);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde afhentningssted tilhørende lejeaftale id: " + agreementId);
        }
        return null;
    }

    private PriceEstimate getPriceEstimate(int agreementId) {

        try {
            String sql = "SELECT * FROM prisoverslag WHERE lejeaftale_id = '" + agreementId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int prisoverslag_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);
                int abonnements_længde = rs.getInt(3);
                int kmPrMdr = rs.getInt(4);
                int totalpris = rs.getInt(5);

                return new PriceEstimate(prisoverslag_id, lejeaftale_id, abonnements_længde, kmPrMdr, totalpris);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde prisoverslag tilhørende lejeaftale id: " + agreementId);
        }
        return null;
    }


    private SurveyReport getSurveyReport(int agreementId) {
        try {
            String sql = "SELECT * FROM tilstandsrapporter WHERE lejeaftale_id = '" + agreementId + "';";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);

                return new SurveyReport(tilstandsrapport_id, lejeaftale_id);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapport tilhørende lejeaftale id: " + agreementId);
        }
        return null;
    }

}
