package com.example.bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SkadeRegController {

    @GetMapping("/skadeRegBruger")
    public String home(){
        return "skadeRegBruger";
    }

}
