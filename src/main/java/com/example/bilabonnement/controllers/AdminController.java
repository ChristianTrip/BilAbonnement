package com.example.bilabonnement.controllers;

import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.services.DataregService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

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


        model.addAttribute("lejeaftaler", dataregService.læscsv());

        return "alleLejeaftaler";
    }

    @PostMapping("/seAlleLejeaftaler/{aftaleNo}")
    public String lejeaftale(@PathVariable("aftaleNo") String nummer, Model m){

        return "redirect:/visLejeaftale?nr=" + nummer;
    }

    @GetMapping("/visLejeaftale")
    public String testLeje(@RequestParam int nr, Model m){
        System.out.println(dataregService.vælgLejeaftale(nr));
        m.addAttribute("lejeaftale", dataregService.vælgLejeaftale(nr));

        return "lejeaftale";
    }

    @PostMapping("/tilfoejDB")
    public String tilfoejTilDatabase(){
        //tilføj til database og fjerne fra csv
        return "redirect:/seAlleLejeaftaler";
    }
}
