package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.models.abonnementer.Abonnement;
import com.example.bilabonnement.models.prisoverslag.Prisoverslag;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LejeaftaleRepo implements CRUDInterface <Lejeaftale>{


    @Override
    public boolean create(Lejeaftale lejeaftale) {

        java.sql.Date mySQLDate = new java.sql.Date(lejeaftale.getOprettelsesDato().getTime());

        try{
            String sql = "INSERT INTO lejeaftaler(`kunde_id`, `bil_stel_nummer`, `abonnement_id`, `tilstandsrapport`, `prisoverslag_id`, `afhentningssted`, `oprettelsesdato`) " +
                    "VALUES ('" + lejeaftale.getKunde().getId() + "', " +
                    "'" + lejeaftale.getBil().getStelNummer() + "', " +
                    "'" + lejeaftale.getAbonnement().getId() + "', " +
                    "'" + lejeaftale.getTilstandsrapport().getId() + "', " +
                    "'" + lejeaftale.getPrisoverslag().getId() + "', " +
                    "'" + lejeaftale.getAfhentningsSted() + "', " +
                    "'" + mySQLDate + "');";

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
    public Lejeaftale getSingleEntityById(int id) {


        try {
            String sql = "SELECT * FROM lejeaftaler WHERE lejeaftale_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int lejeaftale_id = rs.getInt(1);
                int kunde_id = rs.getInt(2);
                String bil_stel_nummer = rs.getString(3);
                int abonnement_id = rs.getInt(4);
                int tilstandsrapport_id = rs.getInt(5);
                int prisoverslag_id = rs.getInt(6);
                int afhentningssted = rs.getInt(7);
                int totalpris = rs.getInt(8);
                Date oprettelsesdato = rs.getDate(9);

                Kunde kunde = new KundeRepo().getSingleEntityById(kunde_id);
                Bil bil = null;
                Tilstandsrapport tilstandsrapport = null;
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


}
