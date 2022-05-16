package com.example.bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        System.out.println("inside index controller");
        return "index";
    }


    @PostMapping("/login")
    public String login(WebRequest request, Model model){
        String brugernavn = request.getParameter("brugernavn");
        String adgangskode = request.getParameter("adgangskode");



        //model.addAttribute("navn", );

        return "redirect:/admin";
    }

    @GetMapping("/loggedIn")
    public String loggedIn(){

        /*
        if (bruger == admin){
            return "/admin";
        }

         */

        return "/admin";
    }
}
