package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.Skade;
import com.example.bilabonnement.models.Tilstandsrapport;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.repositories.TilstandsRapportRepo;
import com.example.bilabonnement.services.DataregService;
import com.example.bilabonnement.services.ForretningsService;
import com.example.bilabonnement.services.SkaderegService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        return "index";
    }

    @GetMapping("/alleLejeaftaler")
    public String alleLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession();

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("isGodkendt", false);
        return "alleLejeaftaler";
    }

    @GetMapping("/ikkeGodkendteLejeaftaler")
    public String seAlleLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession();

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

        session = request.getSession();

        if (session == null){
            return "redirect:/";
        }

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
    public String godkendteLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession();

        if (session == null){
            return "redirect:/";
        }

        if (godkendteLejeaftaler == null){
            godkendteLejeaftaler = dataregService.seAlleGodkendte();
        }
        int totalpris = forretningsService.udregnTotalPris(godkendteLejeaftaler);

        int antalUdlejedeBiler = forretningsService.getCount();

       /* boolean isSkadeRegBruger = true;


        model.addAttribute("isSkadeRegBruger", false);
*/
        model.addAttribute("isGodkendt", true);
        model.addAttribute("lejeaftaler", godkendteLejeaftaler);

        model.addAttribute("totalpris", totalpris);

        model.addAttribute("antalUdlejedeBiler", antalUdlejedeBiler);


        return "alleLejeaftaler";
    }

    @PostMapping("/godkendteLejeaftaler/{aftaleNo}")
    public RedirectView godkendteLejeaftaler(@PathVariable("aftaleNo") String nummer, RedirectAttributes redirectAttributes){

        int currentNumber = parseInt(nummer);
        Lejeaftale la = dataregService.vælgGodkendt(currentNumber + 1);


        if(la != null) {
            redirectAttributes.addFlashAttribute("lejeaftale", la);
            return new RedirectView("/seLejeaftale", true);
        } else {
            return new RedirectView("/godkendteLejeaftaler", true);
        }
    }

    @GetMapping("/seLejeaftale")
    public String godkendteLejeaftaler(HttpServletRequest request, Model m){

        session = request.getSession();

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
        Lejeaftale lejeaftale = dataregService.vælgGodkendt(id);

        if(lejeaftale != null){
            redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);
            return new RedirectView("/rediger-tilstandsrapport", true);
        }
        else{
            return new RedirectView("/seLejeaftale", true);
        }

    }

    @GetMapping("/rediger-tilstandsrapport")
    public String redigerTilstandsrapport(HttpServletRequest request, Model m){
        session = request.getSession();

        if (session == null){
            return "redirect:/";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Lejeaftale lejeaftale = (Lejeaftale) inputFlashMap.get("lejeaftale");

            m.addAttribute("lejeaftale", lejeaftale);
            m.addAttribute("tilstandsrapport",new TilstandsRapportRepo().getSingleEntityById(lejeaftale.getId()));
            m.addAttribute("isGodkendt", true);
            skaderegService.setRapport(lejeaftale.getId());


            return "tilstandsrapport";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }

    @PostMapping("/tilføj-skade/{tilstandsrapportId}")
    public RedirectView tilføjSkade(HttpServletRequest request, @PathVariable int tilstandsrapportId, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");



        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();
        Lejeaftale lejeaftale = dataregService.vælgGodkendt(tilstandsrapport.getLejeaftaleId());
        redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);

        if(titel.equals("") || beskrivelse.equals("") || pris.equals("")){
            return new RedirectView("/rediger-tilstandsrapport", true);
        }
        skaderegService.createSkade(titel,beskrivelse,parseInt(pris));
        skaderegService.opdaterTilstandsRapport();

        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/tilføj-mangel/{tilstandsrapportId}")
    public RedirectView tilføjMangel(HttpServletRequest request, @PathVariable int tilstandsrapportId, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");



        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();
        Lejeaftale lejeaftale = dataregService.vælgGodkendt(tilstandsrapport.getLejeaftaleId());
        redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);

        if(titel.equals("") || beskrivelse.equals("") || pris.equals("")){
            return new RedirectView("/rediger-tilstandsrapport", true);
        }
        skaderegService.createMangel(titel,beskrivelse,parseInt(pris));
        skaderegService.opdaterTilstandsRapport();

        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/fjern-skade/{skadeIdx}")
    public RedirectView fjernSkade(@PathVariable int skadeIdx, RedirectAttributes redirectAttributes){


        Tilstandsrapport tilstandsrapport = skaderegService.getRapport();
        System.out.println(tilstandsrapport);

        Skade skade = tilstandsrapport.getSkader().get(skadeIdx);
        System.out.println(skade);

        skaderegService.removeSkade(skade);
        skaderegService.opdaterTilstandsRapport();

        Lejeaftale lejeaftale = dataregService.vælgGodkendt(tilstandsrapport.getLejeaftaleId());
        redirectAttributes.addFlashAttribute("lejeaftale",lejeaftale);



        return new RedirectView("/rediger-tilstandsrapport", true);

    }



}
