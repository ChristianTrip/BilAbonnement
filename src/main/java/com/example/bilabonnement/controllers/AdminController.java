package com.example.bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    /*
    Kan se alle leje aftaler
    Kan rediegre i alle lejeaftaler - slette, oprette, updatere
    Kan oprette en tilstandsrapport til en lejeaftale
    Skal kunne se den samlede pris for alle lejeaftaler
     */

    @GetMapping("/admin")
    public String home(){
        return "admin";
    }

    @GetMapping("/seAlleLejeaftaler")
    public String seAlleLejeaftaler(){
        return "alleLejeaftaler";
    }
}
