package com.example.bilabonnement.controllers;

import com.example.bilabonnement.services.DataregService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        System.out.println("inside index controller");
        return "index";
    }
}
