package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Lejeaftale;
import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.services.DataregService;
import com.example.bilabonnement.services.ForretningsService;
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
            System.out.println(godkendteLejeaftaler);
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
    public RedirectView godkendteLejeaftaler(@PathVariable("aftaleNo") String nummer, @ModelAttribute Lejeaftale lejeaftale, RedirectAttributes redirectAttributes){

        int currentNumber = parseInt(nummer);
        System.out.println(nummer);
        Lejeaftale la = dataregService.vælgGodkendt(currentNumber);
        System.out.println(la);

        if(la != null) {
            redirectAttributes.addFlashAttribute("lejeaftale", la);
            return new RedirectView("/seLejeaftale", true);
        } else {
            return new RedirectView("/godkendteLejeaftaler", true);
        }
    }

    /*@PostMapping("/godkendteLejeaftaler/lejeaftale/tilstandsrapport")
    public String opretTilstandsrapportTilLejeaftale(){

        return "redirect:/godkendteLejeaftaler/" + currentNumber + "/tilstandsrapport";
    }*/

    @GetMapping("/godkendteLejeaftaler/{aftaleNo}/tilstandsrapport")
    public String opretTilstandsrapportTilLejeaftale(@PathVariable("aftaleNo") int nummer) {

        return "tilstandsrapport";
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
            System.out.println(lejeaftale + "getmapping");

            m.addAttribute("lejeaftale", lejeaftale);
            m.addAttribute("isGodkendt", true);

            return "lejeaftale";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }
}
