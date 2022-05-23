package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.services.DataregService;
import com.example.bilabonnement.services.ForretningsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class AdminController {

    /*
    Kan se alle leje aftaler
    Kan rediegre i alle lejeaftaler - slette, oprette, updatere
    Kan oprette en tilstandsrapport til en lejeaftale
    Skal kunne se den samlede pris for alle lejeaftaler
     */

    private DataregService dataregService = new DataregService();
    private ForretningsService forretningsService = new ForretningsService();
    private ArrayList<Lejeaftale> godkendteLejeaftaler;
    private ArrayList<Lejeaftale> ikkeGodkendteLejeaftaler;


    @GetMapping("/admin")
    public String home(){
        return "admin";
    }

    @GetMapping("logUd")
    public String logUd(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("/alleLejeaftaler")
    public String alleLejeaftaler(Model model){
        model.addAttribute("isGodkendt", false);
        return "alleLejeaftaler";
    }

    @GetMapping("/ikkeGodkendteLejeaftaler")
    public String seAlleLejeaftaler(Model model){

        ikkeGodkendteLejeaftaler = dataregService.læscsv();

        model.addAttribute("isGodkendt", false);
        model.addAttribute("igLejeaftaler", ikkeGodkendteLejeaftaler);

        return "alleLejeaftaler";
    }

    @PostMapping("/ikkeGodkendteLejeaftaler/{aftaleNo}")
    public String lejeaftale(@PathVariable("aftaleNo") String nummer, Model m){

        return "redirect:/visLejeaftale?nr=" + nummer;
    }

    @GetMapping("/visLejeaftale")
    public String testLeje(@RequestParam int nr, Model m, HttpServletRequest request){
        HttpSession session = request.getSession();
        m.addAttribute("lejeaftale", ikkeGodkendteLejeaftaler.get(nr));
        m.addAttribute("isGodkendt", false);

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

    @PostMapping("/tilfoejDB")
    public String tilfoejTilDatabase(Model model, HttpSession session){

        int index = (int) session.getAttribute("indexNummer");
        dataregService.addLejeaftaleToDB(index);


        return "redirect:/ikkeGodkendteLejeaftaler";
    }

    @GetMapping("/godkendteLejeaftaler")
    public String godkendteLejeaftaler(Model model){

        if (godkendteLejeaftaler == null){
            godkendteLejeaftaler = dataregService.seAlleGodkendte();
        }
        int totalpris = forretningsService.udregnTotalPris(godkendteLejeaftaler);

        model.addAttribute("isGodkendt", true);
        model.addAttribute("lejeaftaler", godkendteLejeaftaler);

        model.addAttribute("totalpris", totalpris);

        return "alleLejeaftaler";
    }

    @PostMapping("/godkendteLejeaftaler/{aftaleNo}")
    public String godkendteLejeaftaler(@PathVariable("aftaleNo") String nummer){

        return "redirect:/seLejeaftale?nr=" + nummer;
    }

    @GetMapping("/seLejeaftale")
    public String godkendteLejeaftaler(@RequestParam int nr, Model m, HttpServletRequest request){
        HttpSession session = request.getSession();
        //m.addAttribute("lejeaftale", dataregService.vælgGodkendt(nr + 1));
        m.addAttribute("lejeaftale", godkendteLejeaftaler.get(nr));
        m.addAttribute("isGodkendt", true);

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

}
