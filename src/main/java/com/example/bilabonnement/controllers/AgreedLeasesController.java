package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.leaseAgreements.LeaseAgreement;
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
public class AgreedLeasesController {

    private LeaseAgreementService leaseAgreementService = new LeaseAgreementService();
    private BusinessService businessService = new BusinessService();
    private ArrayList<LeaseAgreement> agreedLeases;

    private HttpSession session;


    @GetMapping("/agreed-leases")
    public String getAgreedLeases(Model model, HttpServletRequest request){

        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

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

        return "leaseagreements";

    }

    @PostMapping("/agreed-leases/{leaseNo}")
    public RedirectView postAgreedLease(@PathVariable("leaseNo") String number, RedirectAttributes redirectAttributes){

        int id = parseInt(number);
        LeaseAgreement agreedLease = leaseAgreementService.getLeaseAgreementById(id);


        if(agreedLease != null) {
            redirectAttributes.addFlashAttribute("agreedLease", agreedLease);
            return new RedirectView("/show-agreed-lease", true);
        } else {
            return new RedirectView("/agreedLeases", true);
        }

    }

    @GetMapping("/show-agreed-lease")
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

            return "leaseagreement";

        } else {
            return "redirect:/agreed-leases";
        }
    }


}
