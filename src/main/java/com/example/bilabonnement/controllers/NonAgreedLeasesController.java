package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.leaseAgreements.LeaseAgreement;
import com.example.bilabonnement.services.LeaseAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class NonAgreedLeasesController {

    private ArrayList<LeaseAgreement> leases;
    private LeaseAgreementService leaseAgreementService = new LeaseAgreementService();
    private HttpSession session;


    @GetMapping("/non-agreed-leases")
    public String getNonAgreedLeases(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        leases = leaseAgreementService.getNonAgreedleases();

        model.addAttribute("leaseApproved", false);
        model.addAttribute("nonAgreedLeases", leases);

        return "leaseagreements";
    }

    @PostMapping("/non-agreed-leases/{leaseNo}")
    public String postNonAgreedLease(@PathVariable("leaseNo") String leaseNumber){

        return "redirect:/show-non-agreed-lease?index=" + leaseNumber;
    }

    @GetMapping("/show-non-agreed-lease")
    public String getNonAgreedLease(@RequestParam int index, Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("leaseAgreement", leases.get(index));
        model.addAttribute("leaseApproved", false);

        session.setAttribute("indexNumber", index);

        return "leaseagreement";
    }

    @PostMapping("/add-to-database")
    public String addLeaseToDatabase(HttpServletRequest request, HttpSession session){
        Date startDate = java.sql.Date.valueOf(request.getParameter("startDato"));
        int index = (int) session.getAttribute("indexNumber");

        leaseAgreementService.addLeaseAgreementToDataBase(index, startDate);


        return "redirect:/non-agreed-leases";
    }

    @PostMapping("/remove-from-csv")
    public String removeFromCsv(HttpSession session){
        int index = (int) session.getAttribute("indexNumber");

        leaseAgreementService.removeLeaseAgreementFromCsv(index);

        return "redirect:/non-agreed-leases";
    }
}
