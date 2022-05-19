package com.example.bilabonnement.controllers;

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

        if(bs.validateUserinfo(brugernavn, adgangskode)) {
            return "redirect:/admin";
        }

        return "index";
    }
}
