package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.repositories.BrugerRepo;
import com.example.bilabonnement.services.BrugerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        System.out.println("inside index controller");
        return "index";
    }


    @PostMapping("/login")
    public String login(HttpSession session, WebRequest request, Model model){

        BrugerService bs = new BrugerService();

        String brugernavn = request.getParameter("brugernavn");
        String adgangskode = request.getParameter("adgangskode");


        BrugerType brugerType = bs.determineBrugertype(brugernavn, adgangskode);

            if(brugerType.equals(BrugerType.ADMIN)) {
                return "redirect:/admin";
            }
            else if(brugerType.equals(BrugerType.DATAREG)) {
                System.out.println("datareg");
                return "redirect:/admin";
            }
            else if(brugerType.equals(BrugerType.FORRETNING)) {
                System.out.println("forretningsreg");
                return "redirect:/admin";
            }
            else if(brugerType.equals(BrugerType.SKADEREG)) {
                System.out.println("skadereg");
                return "redirect:/admin";
            }
        return "index";
    }
}
