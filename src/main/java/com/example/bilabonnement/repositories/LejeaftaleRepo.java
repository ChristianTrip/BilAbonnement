package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.abonnementer.LimitedAbonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LejeaftaleRepo implements CRUDInterface <Lejeaftale>{

    private BilRepo bilRepo = new BilRepo();
    private KundeRepo kundeRepo = new KundeRepo();
    private TilstandsRapportRepo tilstandsRapportRepo = new TilstandsRapportRepo();
    private AbonnementRepo abonnementRepo = new AbonnementRepo();
    private AfhentningsstedRepo afhentningsstedRepo = new AfhentningsstedRepo();
    private PrisoverslagRepo prisoverslagRepo = new PrisoverslagRepo();


    @Override
    public boolean create(Lejeaftale lejeaftale) {

        java.sql.Date mySQLDate = new java.sql.Date(lejeaftale.getOprettelsesDato().getTime());
        int lejeaftaleId = -1;
        Connection conn = DatabaseConnectionManager.getConnection();


        try{
            String sql = "INSERT INTO lejeaftaler(`oprettelsesdato`) " +
                    "VALUES ('" + mySQLDate + "');";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "SELECT LAST_INSERT_ID();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                lejeaftaleId = rs.getInt(1);
            }

            Kunde kunde = lejeaftale.getKunde();
            kunde.setLejeaftaleId(lejeaftaleId);
            kundeRepo.create(kunde);

            Bil bil = lejeaftale.getBil();
            bil.setLejeaftaleId(lejeaftaleId);
            bilRepo.create(bil);

            Tilstandsrapport tilstandsrapport = lejeaftale.getTilstandsrapport();
            tilstandsrapport.setLejeaftaleId(lejeaftaleId);
            tilstandsRapportRepo.create(tilstandsrapport);

            Abonnement abonnement = lejeaftale.getAbonnement();
            abonnement.setLejeaftaleId(lejeaftaleId);
            abonnementRepo.create(abonnement);

            AfhentningsSted afhentningsSted = lejeaftale.getAfhentningsSted();
            afhentningsSted.setLejeaftaleId(lejeaftaleId);
            afhentningsstedRepo.create(afhentningsSted);

            Prisoverslag prisoverslag = lejeaftale.getPrisoverslag();
            prisoverslag.setLejeaftaleId(lejeaftaleId);
            prisoverslagRepo.create(prisoverslag);

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
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int lejeaftale_id = rs.getInt(1);
                Date oprettelsesdato = rs.getDate(2);

                Kunde kunde = new KundeRepo().getSingleEntityById(id);
                Bil bil = new BilRepo().getSingleEntityById(id);
                Tilstandsrapport tilstandsrapport = new TilstandsRapportRepo().getSingleEntityById(id);
                Abonnement abonnement = null;
                Prisoverslag prisoverslag = null;
                AfhentningsSted afhentningsSted = null;
                int totalPris = -1;

                return new Lejeaftale(lejeaftale_id, kunde, bil, tilstandsrapport, abonnement, prisoverslag, afhentningsSted, totalPris, oprettelsesdato);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke finde kunde med id: " + id);
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
        return false;
    }


    public static void main(String[] args) {
        LejeaftaleRepo repo = new LejeaftaleRepo();
        Kunde kunde = new Kunde("John", "Andersen", "Holmbladsgade 30", 2300, "Kbh S", "Johnandersen@mail.dk", "12345678", "0911883485", "1234", "1234567890");
        Bil bil = new Bil("ZW00003344KL", "Fiat", "grand");
        Tilstandsrapport tilstandsrapport = new Tilstandsrapport();
        Abonnement abonnement = new LimitedAbonnement(true);
        Prisoverslag prisoverslag = new Prisoverslag(4000, 3);
        AfhentningsSted afhentningsSted = new AfhentningsSted("Lergravsvej 3", "2300", "København S", 300);
        Lejeaftale lejeaftale = new Lejeaftale(kunde, bil, tilstandsrapport, abonnement, prisoverslag, afhentningsSted);
        repo.create(lejeaftale);

    }


}
