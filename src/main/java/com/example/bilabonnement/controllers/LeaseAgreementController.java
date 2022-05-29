package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.LeaseAgreement;
import com.example.bilabonnement.models.Deficiency;
import com.example.bilabonnement.models.Injury;
import com.example.bilabonnement.models.SurveyReport;
import com.example.bilabonnement.models.users.User;
import com.example.bilabonnement.models.users.UserType;
import com.example.bilabonnement.services.LeaseAgreementService;
import com.example.bilabonnement.services.BusinessService;
import com.example.bilabonnement.services.SurveyReportService;
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
public class LeaseAgreementController {

    private LeaseAgreementService leaseAgreementService = new LeaseAgreementService();
    private BusinessService businessService = new BusinessService();
    private SurveyReportService surveyReportService = new SurveyReportService();
    private ArrayList<LeaseAgreement> godkendteLejeaftaler;
    private ArrayList<LeaseAgreement> ikkeGodkendteLejeaftaler;


    private HttpSession session;


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

        ikkeGodkendteLejeaftaler = leaseAgreementService.getNonAgreedleases();

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

        m.addAttribute("leaseAgreement", ikkeGodkendteLejeaftaler.get(nr));
        m.addAttribute("isGodkendt", false);

        session.setAttribute("indexNummer", nr);

        return "lejeaftale";
    }

    @PostMapping("/tilfoejDB")
    public String tilfoejTilDatabase(HttpServletRequest request, HttpSession session){
        Date startDate = java.sql.Date.valueOf(request.getParameter("startDato"));
        int index = (int) session.getAttribute("indexNummer");

        System.out.println("index number inside tilføj db" + index);
        leaseAgreementService.addLeaseAgreementToDataBase(index, startDate);


        return "redirect:/ikkeGodkendteLejeaftaler";
    }

    @GetMapping("/godkendteLejeaftaler")
    public String godkendteLejeaftaler(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        godkendteLejeaftaler = leaseAgreementService.getAllLeaseAgreements();

        int totalpris = businessService.getTotalPrice(godkendteLejeaftaler);

        int antalUdlejedeBiler = businessService.getNumberOfRentedOutCars(godkendteLejeaftaler);

       /* boolean isSkadeRegBruger = true;


        model.addAttribute("isSkadeRegBruger", false);
*/
        model.addAttribute("isGodkendt", true);
        model.addAttribute("igangvaerende", leaseAgreementService.getAllActiveAgreements(godkendteLejeaftaler));
        model.addAttribute("afsluttede", leaseAgreementService.getAllEndedAgreements(godkendteLejeaftaler));
        model.addAttribute("manglerTilstandsrapport", "Ingen registrerede skader/mangler");
        model.addAttribute("harTilstandsrapport", "Har skader/mangler");


        model.addAttribute("totalpris", totalpris);

        model.addAttribute("antalUdlejedeBiler", antalUdlejedeBiler);


        return "alleLejeaftaler";
    }

    @PostMapping("/godkendteLejeaftaler/{aftaleNo}")
    public RedirectView godkendteLejeaftaler(@PathVariable("aftaleNo") String number, RedirectAttributes redirectAttributes){

        int id = parseInt(number);
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(id);


        if(leaseAgreement != null) {
            redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);
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
            LeaseAgreement leaseAgreement = (LeaseAgreement) inputFlashMap.get("lejeaftale");

            m.addAttribute("leaseAgreement", leaseAgreement);
            m.addAttribute("isGodkendt", true);

            return "lejeaftale";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }


}
