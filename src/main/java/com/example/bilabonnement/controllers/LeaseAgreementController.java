package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.LeaseAgreement;
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
    private ArrayList<LeaseAgreement> agreedLeases;
    private ArrayList<LeaseAgreement> leases;


    private HttpSession session;


    @GetMapping("/allLeaseAgreements")
    public String getAllLeases(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("leaseApproved", false);
        return "leaseAgreements";
    }

    @GetMapping("/nonAgreedLeases")
    public String getNonAgreedLeases(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        leases = leaseAgreementService.getNonAgreedleases();

        model.addAttribute("leaseApproved", false);
        model.addAttribute("nonAgreedLeases", leases);
        System.out.println(model.getAttribute("leaseApproved"));
        System.out.println(model.getAttribute("nonAgreedLeases"));

        return "leaseAgreements";
    }

    @PostMapping("/nonAgreedLeases/{leaseNo}")
    public String postNonAgreedLease(@PathVariable("leaseNo") String leaseNumber){

        return "redirect:/showNonAgreedLease?index=" + leaseNumber;
    }

    @GetMapping("/showNonAgreedLease")
    public String getNonAgreedLease(@RequestParam int index, Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("leaseAgreement", leases.get(index));
        model.addAttribute("leaseApproved", false);

        session.setAttribute("indexNummer", index);

        return "leaseAgreement";
    }

    @PostMapping("/addToDatabase")
    public String addLeaseToDatabase(HttpServletRequest request, HttpSession session){
        Date startDate = java.sql.Date.valueOf(request.getParameter("startDato"));
        int index = (int) session.getAttribute("indexNummer");

        System.out.println("index number inside tilføj db" + index);
        leaseAgreementService.addLeaseAgreementToDataBase(index, startDate);


        return "redirect:/nonAgreedLeases";
    }

    @GetMapping("/agreedLeases")
    public String getAgreedLeases(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        try{
        agreedLeases = leaseAgreementService.getAllLeaseAgreements();

            int totalPrice = businessService.getTotalPrice(agreedLeases);
            int numberOfRentedOutCars = businessService.getNumberOfRentedOutCars(agreedLeases);

            model.addAttribute("leaseApproved", true);
            model.addAttribute("activeLeases", leaseAgreementService.getAllActiveAgreements(agreedLeases));
            model.addAttribute("endedLeases", leaseAgreementService.getAllEndedAgreements(agreedLeases));
            model.addAttribute("hasNoReport", "Ingen registrerede skader/mangler");
            model.addAttribute("hasReport", "Har skader/mangler");

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("numberOfRentedOutCars", numberOfRentedOutCars);

            return "leaseAgreements";
        }
        catch (Exception e){
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "error";
    }


    @PostMapping("/agreedLeases/{leaseNo}")
    public RedirectView postAgreedLease(@PathVariable("leaseNo") String number, RedirectAttributes redirectAttributes){

        int id = parseInt(number);
        LeaseAgreement agreedLease = leaseAgreementService.getLeaseAgreementById(id);


        if(agreedLease != null) {
            redirectAttributes.addFlashAttribute("agreedLease", agreedLease);
            return new RedirectView("/showAgreedLease", true);
        } else {
            return new RedirectView("/agreedLeases", true);
        }

    }

    @GetMapping("/showAgreedLease")
    public String getAgreedLeases(HttpServletRequest request, Model m){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            LeaseAgreement leaseAgreement = (LeaseAgreement) inputFlashMap.get("agreedLease");

            m.addAttribute("leaseAgreement", leaseAgreement);
            m.addAttribute("leaseApproved", true);

            return "leaseAgreement";

        } else {
            return "redirect:/agreedLeases";
        }
    }


}
