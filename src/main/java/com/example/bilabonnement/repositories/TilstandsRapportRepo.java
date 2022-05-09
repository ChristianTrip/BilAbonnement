package com.example.bilabonnement.repositories;

import com.example.bilabonnement.models.Kunde;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;
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

    @Override
    public boolean create(Tilstandsrapport tilstandsrapport) {

        try{
            String sql = "INSERT INTO tilstandsrapporter(`lejeaftale_id`) " +
                    "VALUES ('" + tilstandsrapport.getLejeaftaleId() + "');";

            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Kunne ikke oprette bruger med id " + tilstandsrapport.getId() + " i databasen");
        }

        return false;
    }


    @Override
    public Tilstandsrapport getSingleEntityById(int id) {
        try {
            String sql = "SELECT * FROM tilstandsrapporter WHERE tilstandsrapport_id = '" + id + "';";
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);


                return new Tilstandsrapport(tilstandsrapport_id, lejeaftale_id, getMangler(tilstandsrapport_id), getSkader(tilstandsrapport_id));
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
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int tilstandsrapport_id = rs.getInt(1);
                int lejeaftale_id = rs.getInt(2);

                tilstandsrapporter.add(new Tilstandsrapport(tilstandsrapport_id, getMangler(tilstandsrapport_id), getSkader(tilstandsrapport_id)));
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

           deleteAllMangler(tilstandsrapport.getId());
           deleteAllSkader(tilstandsrapport.getId());

            for (Mangel mangel : tilstandsrapport.getMangler()) {
                mangelRepo.create(mangel);
            }
            for (Skade skade : tilstandsrapport.getSkader()) {
                skadeRepo.create(skade);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Kunne ikke opdatere tilstandsrapport med id " + tilstandsrapport.getId());
        }
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

    private void deleteAllMangler(int tilstandsRapportId){
        for (Mangel mangel : mangelRepo.getAllEntities()) {
            if (mangel.getTilstandsRapportId() == tilstandsRapportId){
                mangelRepo.deleteById(mangel.getTilstandsRapportId());
            }
        }
    }

    private void deleteAllSkader(int tilstandsRapportId){
        for (Skade skade : skadeRepo.getAllEntities()) {
            if (skade.getTilstandsRapportId() == tilstandsRapportId){
                skadeRepo.deleteById(skade.getTilstandsRapportId());
            }
        }
    }

    private ArrayList<Mangel> getMangler(int tilstandsRapportId){
        ArrayList<Mangel> mangler = new ArrayList<>();
        for (Mangel mangel : mangelRepo.getAllEntities()) {
            if (mangel.getTilstandsRapportId() == tilstandsRapportId){
                mangler.add(mangel);
            }
        }
        return mangler;
    }

    private ArrayList<Skade> getSkader(int tilstandsRapportId){
        ArrayList<Skade> skader = new ArrayList<>();
        for (Skade skade : skadeRepo.getAllEntities()) {
            if (skade.getTilstandsRapportId() == tilstandsRapportId){
                skader.add(skade);
            }
        }
        return skader;
    }

}


