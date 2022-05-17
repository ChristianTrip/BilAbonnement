package com.example.bilabonnement.controllers;

import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.services.DataregService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static java.lang.Integer.parseInt;

@Controller
public class AdminController {

    /*
    Kan se alle leje aftaler
    Kan rediegre i alle lejeaftaler - slette, oprette, updatere
    Kan oprette en tilstandsrapport til en lejeaftale
    Skal kunne se den samlede pris for alle lejeaftaler
     */

    private DataregService dataregService = new DataregService();
    private LejeaftaleRepo lejeaftaleRepo = new LejeaftaleRepo();

    @GetMapping("/admin")
    public String home(){
        return "admin";
    }

    @GetMapping("/alleLejeaftaler")
    public String alleLejeaftaler(){
        return "alleLejeaftaler";
    }


    @GetMapping("/seAlleLejeaftaler")
    public String seAlleLejeaftaler(Model model){

        System.out.println(lejeaftaleRepo.getAllEntities());
        model.addAttribute("lejeaftaler", dataregService.læscsv());

        return "alleLejeaftaler";
    }

    @GetMapping("/seAlleLejeaftaler/{aftaleNo}")
    public String lejeaftale(@PathVariable("aftaleNo") String nummer, Model m){
        m.addAttribute("valgteAftale", nummer);
        m.addAttribute("lejeaftale", lejeaftaleRepo.getSingleEntityById(parseInt(nummer)));

        return "lejeaftale";
    }
}
