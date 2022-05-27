package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Mangel;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.services.DataregService;
import com.example.bilabonnement.services.ForretningsService;
import com.example.bilabonnement.services.SkaderegService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Controller
public class LejeaftalerController {

    private DataregService dataregService = new DataregService();
    private ForretningsService forretningsService = new ForretningsService();
    private SkaderegService skaderegService = new SkaderegService();
    private ArrayList<Lejeaftale> godkendteLejeaftaler;
    private ArrayList<Lejeaftale> ikkeGodkendteLejeaftaler;


    private HttpSession session;
    private Bruger currentUser;
    private BrugerType brugerType;



    @GetMapping({"/login-success"})
    public String getSuccess(HttpServletRequest request) {

        if (currentUser != null){
            session = request.getSession(false);
            return "admin";
        }
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            currentUser = (Bruger) inputFlashMap.get("bruger");
            brugerType = currentUser.getBrugerType();
            session = request.getSession();
            System.out.println(currentUser.getNavn() + " er nu logget ind som " + brugerType + " bruger");
            return "admin";
        }
        else {
            return "redirect:/login-submit";
        }
    }

    @GetMapping("/logUd")
    public String logUd(){
        session.invalidate();
        System.out.println(currentUser.getNavn() + " er nu logget ud");
        currentUser = null;
        return "index";
    }

    @GetMapping("/alleLejeaftaler")
    public String alleLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("isGodkendt", false);
        return "alleLejeaftaler";
    }

    @GetMapping("/ikkeGodkendteLejeaftaler")
    public String seAlleLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

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

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        m.addAttribute("lejeaftale", ikkeGodkendteLejeaftaler.get(nr));
        m.addAttribute("isGodkendt", false);

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

    @PostMapping("/tilfoejDB")
    public String tilfoejTilDatabase(HttpServletRequest request, Model model, HttpSession session){
        Date startDato = java.sql.Date.valueOf(request.getParameter("startDato"));
        int index = (int) session.getAttribute("indexNummer");

        dataregService.addLejeaftaleToDB(index, startDato);


        return "redirect:/ikkeGodkendteLejeaftaler";
    }

    @GetMapping("/godkendteLejeaftaler")
    public String godkendteLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }


        godkendteLejeaftaler = dataregService.seAlleGodkendte();

        int totalpris = forretningsService.udregnTotalPris(godkendteLejeaftaler);

        int antalUdlejedeBiler = forretningsService.getRentedCars(godkendteLejeaftaler);

       /* boolean isSkadeRegBruger = true;


        model.addAttribute("isSkadeRegBruger", false);
*/
        model.addAttribute("isGodkendt", true);
        model.addAttribute("igangvaerende", dataregService.getAllIgangværende(godkendteLejeaftaler));
        model.addAttribute("afsluttede", dataregService.getAllAfsluttede(godkendteLejeaftaler));
        model.addAttribute("manglerTilstandsrapport", "Ingen registrerede skader/mangler");
        model.addAttribute("harTilstandsrapport", "Har skader/mangler");


        model.addAttribute("totalpris", totalpris);

        model.addAttribute("antalUdlejedeBiler", antalUdlejedeBiler);


        return "alleLejeaftaler";
    }

    @PostMapping("/godkendteLejeaftaler/{aftaleNo}")
    public RedirectView godkendteLejeaftaler(@PathVariable("aftaleNo") String nummer, RedirectAttributes redirectAttributes){

        int currentNumber = parseInt(nummer);
        Lejeaftale la = dataregService.getLejeaftale(currentNumber);


        if(la != null) {
            redirectAttributes.addFlashAttribute("lejeaftale", la);
            return new RedirectView("/seLejeaftale", true);
        } else {
            return new RedirectView("/godkendteLejeaftaler", true);
        }
    }

    @GetMapping("/seLejeaftale")
    public String godkendteLejeaftaler(HttpServletRequest request, Model m){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Lejeaftale lejeaftale = (Lejeaftale) inputFlashMap.get("lejeaftale");

            m.addAttribute("lejeaftale", lejeaftale);
            m.addAttribute("isGodkendt", true);

            return "lejeaftale";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }

    @PostMapping("/tilstandsrapport/{id}")
    public RedirectView seTilstandsrapport(@PathVariable int id, RedirectAttributes redirectAttributes){
        Lejeaftale lejeaftale = dataregService.getLejeaftale(id);
        Tilstandsrapport tilstandsrapport = lejeaftale.getTilstandsrapport();

        if(lejeaftale != null){
            redirectAttributes.addFlashAttribute("lejeaftale", lejeaftale);
            //redirectAttributes.addFlashAttribute("tilstandsrapport", tilstandsrapport);
            return new RedirectView("/rediger-tilstandsrapport", true);
        }
        else{
            return new RedirectView("/seLejeaftale", true);
        }
    }

    @GetMapping("/rediger-tilstandsrapport")
    public String redigerTilstandsrapport(HttpServletRequest request, Model m){
        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Lejeaftale lejeaftale = (Lejeaftale) inputFlashMap.get("lejeaftale");
            Tilstandsrapport tilstandsrapport = lejeaftale.getTilstandsrapport();
            skaderegService.setRapport(tilstandsrapport);


            m.addAttribute("lejeaftale", lejeaftale);
            m.addAttribute("tilstandsrapport", tilstandsrapport);
            m.addAttribute("isGodkendt", true);

            System.out.println(lejeaftale);

            return "tilstandsrapport";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }

    @PostMapping("/tilføj-skade")
    public RedirectView tilføjSkade(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");


        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();

        if(skaderegService.tilføjSkade(titel, beskrivelse, pris)){

            Lejeaftale lejeaftale = dataregService.getLejeaftale(tilstandsrapport.getLejeaftaleId());
            redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);
        }


        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/tilføj-mangel")
    public RedirectView tilføjMangel(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");


        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();

        if(skaderegService.tilføjMangel(titel, beskrivelse, pris)){

            Lejeaftale lejeaftale = dataregService.getLejeaftale(tilstandsrapport.getLejeaftaleId());
            redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);
        }

        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/fjern-skade/{skadeIdx}")
    public RedirectView fjernSkade(@PathVariable int skadeIdx, RedirectAttributes redirectAttributes){


        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();
        System.out.println(tilstandsrapport);

        Skade skade = tilstandsrapport.getSkader().get(skadeIdx);
        System.out.println(skade);

        skaderegService.fjernSkade(skade);


        Lejeaftale lejeaftale = dataregService.getLejeaftale(tilstandsrapport.getLejeaftaleId());
        redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);



        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/fjern-mangel/{mangelIdx}")
    public RedirectView fjernMangel(@PathVariable int mangelIdx, RedirectAttributes redirectAttributes){
        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();

        Mangel mangel = tilstandsrapport.getMangler().get(mangelIdx);

        skaderegService.fjernMangel(mangel);

        Lejeaftale lejeaftale = dataregService.getLejeaftale(tilstandsrapport.getLejeaftaleId());
        redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);

        return new RedirectView("/rediger-tilstandsrapport", true);
    }
}
