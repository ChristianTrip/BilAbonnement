package com.example.bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForretningController {

    @GetMapping("/forretningBruger")
    public String home(){
        return "forretningBruger";
    }
}
