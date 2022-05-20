package com.example.bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataRegController {

    @GetMapping("/dataRegBruger")
    public String home(){
        return "dataRegBruger";
    }

}
