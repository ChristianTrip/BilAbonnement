package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.repositories.LejeaftaleRepo;
import com.example.bilabonnement.services.DataregService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

@Controller
public class AdminController {

    /*
    Kan se alle leje aftaler
    Kan rediegre i alle lejeaftaler - slette, oprette, updatere
    Kan oprette en tilstandsrapport til en lejeaftale
    Skal kunne se den samlede pris for alle lejeaftaler
     */

    private DataregService dataregService = new DataregService();
    private LejeaftaleRepo lejeaftaleRepo = new LejeaftaleRepo();

    @GetMapping("/admin")
    public String home(){
        return "admin";
    }

    @GetMapping("/alleLejeaftaler")
    public String alleLejeaftaler(){
        return "alleLejeaftaler";
    }

    @GetMapping("/ikkeGodkendteLejeaftaler")
    public String seAlleLejeaftaler(Model model){


        model.addAttribute("lejeaftaler", dataregService.læscsv());

        return "alleLejeaftaler";
    }

    @PostMapping("/ikkeGodkendteLejeaftaler/{aftaleNo}")
    public String lejeaftale(@PathVariable("aftaleNo") String nummer, Model m){

        return "redirect:/visLejeaftale?nr=" + nummer;
    }

    @GetMapping("/visLejeaftale")
    public String testLeje(@RequestParam int nr, Model m, HttpServletRequest request){
        HttpSession session = request.getSession();
        m.addAttribute("lejeaftale", dataregService.vælgLejeaftale(nr));

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

    @PostMapping("/tilfoejDB")
    public String tilfoejTilDatabase(Model model, HttpSession session){

        int index = (int) session.getAttribute("indexNummer");
        dataregService.addLejeaftaleToDB(index);
        session.invalidate();

        return "redirect:/ikkeGodkendteLejeaftaler";
    }




    @GetMapping("/godkendteLejeaftaler")
    public String godkendteLejeaftaler(Model model){


        model.addAttribute("lejeaftaler", new LejeaftaleRepo().getAllEntities());

        return "alleLejeaftaler";
    }

    @PostMapping("/godkendteLejeaftaler/{aftaleNo}")
    public String godkendteLejeaftaler(@PathVariable("aftaleNo") String nummer){

        return "redirect:/seLejeaftale?nr=" + nummer;
    }

    @GetMapping("/seLejeaftale")
    public String godkendteLejeaftaler(@RequestParam int nr, Model m, HttpServletRequest request){
        HttpSession session = request.getSession();
        m.addAttribute("lejeaftale", new LejeaftaleRepo().getSingleEntityById(nr + 1));

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

}
