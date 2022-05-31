package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.leaseAgreementElements.*;
import com.example.bilabonnement.models.surveyReportElements.SurveyReport;
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
            String sql = "INSERT INTO lease_agreements(`approval_date`,`start_date`, `customer_cpr`, `chassis_number`) " +
                    "VALUES ('" + approvalDate + "', " +
                            "'" + startDate + "', " +
                            "'" + leaseAgreement.getCustomer().getCpr() + "', " +
                            "'" + leaseAgreement.getCar().getChassisNumber() + "');";


            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            int agreementId = getLastIndex();

            Customer customer = leaseAgreement.getCustomer();

            Car car = leaseAgreement.getCar();
            car.setAgreementId(agreementId);

            Subscription subscription = leaseAgreement.getSubscription();
            subscription.setAgreementId(agreementId);

            PickupPlace pickupPlace = leaseAgreement.getPickupPlace();
            pickupPlace.setAgreementId(agreementId);

            PriceEstimate priceEstimate = leaseAgreement.getPriceEstimate();
            priceEstimate.setAgreementId(agreementId);

            SurveyReport surveyReport = new SurveyReport(agreementId);
            surveyReport.setAgreementId(agreementId);


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
            String sql = "SELECT * FROM lease_agreements WHERE agreement_id = '" + id + "';";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int agreement_id = rs.getInt(1);
                java.sql.Date oprettelsesdato = rs.getDate(2);
                java.sql.Date startdato = rs.getDate(3);
                String cpr = rs.getString(4);
                String chassisNumber = rs.getString(5);

                Customer customer = getCustomer(cpr);
                Car car = getCar(chassisNumber);
                SurveyReport surveyReport = getSurveyReport(agreement_id);
                Subscription subscription = getSubscription(id);
                PriceEstimate priceEstimate = getPriceEstimate(id);
                PickupPlace pickupPlace = getPickupPlace(id);

                return new LeaseAgreement(agreement_id, customer, car, surveyReport, subscription, priceEstimate, pickupPlace, oprettelsesdato.toLocalDate(), startdato.toLocalDate());
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
            String sql = "SELECT * FROM lease_agreements;";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int agreementId = rs.getInt(1);

                leaseAgreements.add(getSingleEntityById(agreementId));
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

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql =    "UPDATE lease_agreements " +
                    "SET " +
                    "start_date = '" + leaseAgreement.getStartDate()                    + "', " +
                    "customer_cpr = '" + leaseAgreement.getCustomer().getCpr()          + "', " +
                    "chassis_number = '" + leaseAgreement.getCar().getChassisNumber()   + "'" +
                    "WHERE agreement_id = " + leaseAgreement.getId() + ";";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke updatere kunde med cpr nummer: " + leaseAgreement.getId());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(int id) {

        try{
            conn = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM lease_agreements WHERE agreement_id = " + id + ";";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke slette skade med id nummer: " + id);
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


    // metoder der indsætter elementerne der hører til en lejeaftale

    private boolean insertCar(Car car){

        try{
            String sql = "INSERT IGNORE cars(`agreement_id`, `chassis_number`, `brand`, `model`) " +
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
            String sql = "INSERT IGNORE INTO customers(`first_name`, `last_name`, `address`, `postal_code`, `city`, `email`, `phone`, `cpr`, `reg_number`, `account_number`) " +
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
            String sql = "INSERT INTO subscriptions(`agreement_id`, `low_deductible`, `delivery_insurance`, `length_in_months`, `has_standard_color`, `is_limited`) " +
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
            String sql = "INSERT INTO pickup_places(`agreement_id`, `address`, `postal_code`, `city`, `delivery_cost`) " +
                    "VALUES (" +
                    "'" + pickupPlace.getAgreementId() + "', " +
                    "'" + pickupPlace.getAddress() + "', " +
                    "'" + pickupPlace.getPostalCode() + "', " +
                    "'" + pickupPlace.getCity() + "', " +
                    "'" + pickupPlace.getDeliveryCost() + "');";


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
            String sql = "INSERT INTO price_estimates(`agreement_id`, `subscription_length`, `km_per_month`, `total_price`) " +
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
            String sql = "INSERT INTO survey_reports(`agreement_id`) " +
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
            String sql = "SELECT * FROM cars WHERE chassis_number = '" + chassisNumber + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){

                int carId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                String chassisNo = rs.getString(3);
                String brand = rs.getString(4);
                String model = rs.getString(5);

                return new Car(carId, agreementId, chassisNo, brand, model);
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
            String sql = "SELECT * FROM customers WHERE cpr = '" + cpr + "';";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String address = rs.getString(4);
                String postalCode = rs.getString(5);
                String city = rs.getString(6);
                String email = rs.getString(7);
                String phone = rs.getString(8);
                String cprNumber = rs.getString(9);
                String regNumber = rs.getString(10);
                String accountNuber = rs.getString(11);

                return new Customer(customerId, firstName, lastName, address, postalCode, city, email, phone, cprNumber, regNumber, accountNuber);
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
            String sql = "SELECT * FROM subscriptions WHERE agreement_id = '" + AgreementId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int subscriptionId = rs.getInt(1);
                int agreementId = rs.getInt(2);
                boolean lowDeductible = rs.getBoolean(3);
                boolean deliveryInsurance = rs.getBoolean(4);
                int lengthInMonths = rs.getInt(5);
                boolean standartColor = rs.getBoolean(6);
                boolean isLimited = rs.getBoolean(7);


                if (isLimited){
                    return new LimitedSubscription(subscriptionId, agreementId, lowDeductible, standartColor);
                }
                else{
                    return new UnlimitedSubscription(subscriptionId, agreementId, lengthInMonths, lowDeductible, deliveryInsurance, standartColor);
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
            String sql = "SELECT * FROM pickup_places WHERE agreement_id = '" + agreementId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int pickupId = rs.getInt(1);
                int leaseAgreementId = rs.getInt(2);
                String address = rs.getString(3);
                String postalCode = rs.getString(4);
                String city = rs.getString(5);
                int deliveryCost = rs.getInt(6);

                return new PickupPlace(pickupId, leaseAgreementId, address, postalCode, city, deliveryCost);
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
            String sql = "SELECT * FROM price_estimates WHERE agreement_id = '" + agreementId + "';";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int priceEstimateId = rs.getInt(1);
                int leaseAgreementId = rs.getInt(2);
                int subscriptionLength = rs.getInt(3);
                int kmPerMonth = rs.getInt(4);
                int totalPrice = rs.getInt(5);

                return new PriceEstimate(priceEstimateId, leaseAgreementId, subscriptionLength, kmPerMonth, totalPrice);
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
            String sql = "SELECT * FROM survey_reports WHERE agreement_id = '" + agreementId + "';";

            conn = DatabaseConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int reportId = rs.getInt(1);
                int leaseAgreementId = rs.getInt(2);

                return new SurveyReport(reportId, leaseAgreementId);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde tilstandsrapport tilhørende lejeaftale id: " + agreementId);
        }
        return null;
    }

}
