package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.services.SkaderegService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
//@RequestMapping(value = "/skadeRegBruger")
public class SkadeRegController {


    private ArrayList<Lejeaftale> godkendteLejeaftaler;
    private SkaderegService skaderegService = new SkaderegService();

    @GetMapping("/skadeRegBruger")
    public String home(){
        return "skadeRegBruger";
    }


/*
    @GetMapping(value = "/lejeaftale/{aftaleId}")
    public @ResponseBody Lejeaftale getLejeaftale(@PathVariable Integer id) {
        ArrayList<Lejeaftale> lejeaftaler = (ArrayList<Lejeaftale>) new LejeaftaleRepo().getAllEntities();
        Lejeaftale lejeaftale = lejeaftaler.get(id);

        return lejeaftale;
    }

 */




}
