package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Deficiency;
import com.example.bilabonnement.models.Injury;
import com.example.bilabonnement.models.LeaseAgreement;
import com.example.bilabonnement.models.SurveyReport;
import com.example.bilabonnement.services.LeaseAgreementService;
import com.example.bilabonnement.services.SurveyReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class SurveyReportController {

    private SurveyReportService surveyReportService = new SurveyReportService();
    private LeaseAgreementService leaseAgreementService = new LeaseAgreementService();
    private HttpSession session;


    @PostMapping("/surveyReport/{id}")
    public RedirectView seTilstandsrapport(@PathVariable int id, RedirectAttributes redirectAttributes){
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(id);
        SurveyReport surveyReport = leaseAgreement.getSurveyReport();

        if(leaseAgreement != null){
            redirectAttributes.addFlashAttribute("leaseAgreement", leaseAgreement);
            //redirectAttributes.addFlashAttribute("surveyReport", surveyReport);
            return new RedirectView("/edit-survey-report", true);
        }
        else{
            return new RedirectView("/showAgreedLease", true);
        }
    }

    @GetMapping("/edit-survey-report")
    public String redigerTilstandsrapport(HttpServletRequest request, Model m){
        session = request.getSession(false);

        if (session == null){
            return "redirect:/";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            LeaseAgreement leaseAgreement = (LeaseAgreement) inputFlashMap.get("leaseAgreement");
            SurveyReport surveyReport = leaseAgreement.getSurveyReport();
            surveyReportService.setSurveyReport(surveyReport);


            m.addAttribute("leaseAgreement", leaseAgreement);
            m.addAttribute("surveyReport", surveyReport);
            m.addAttribute("approvedLease", true);

            System.out.println(leaseAgreement);

            return "surveyReports";

        } else {
            return "redirect:/agreedLeases";
        }
    }

    @PostMapping("/add-injury")
    public RedirectView tilføjSkade(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String price = request.getParameter("price");


        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        if(surveyReportService.addInjury(title, description, price)){

            LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
            redirectAttributes.addFlashAttribute("leaseAgreement", leaseAgreement);
        }
        return new RedirectView("/edit-survey-report", true);
    }

    @PostMapping("/add-deficiency")
    public RedirectView tilføjMangel(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String price = request.getParameter("price");


        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        if(surveyReportService.addDeficiency(title, description, price)){

            LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
            redirectAttributes.addFlashAttribute("leaseAgreement", leaseAgreement);
        }

        return new RedirectView("/edit-survey-report", true);

    }

    @PostMapping("/remove-injury/{injuryIndex}")
    public RedirectView fjernSkade(@PathVariable int injuryIndex, RedirectAttributes redirectAttributes){


        SurveyReport surveyReport = surveyReportService.getSurveyReport();
        System.out.println(surveyReport);

        Injury injury = surveyReport.getInjuries().get(injuryIndex);
        System.out.println(injury);

        surveyReportService.removeInjury(injury);


        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
        redirectAttributes.addFlashAttribute("leaseAgreement", leaseAgreement);



        return new RedirectView("/edit-survey-report", true);

    }

    @PostMapping("/remove-deficiency/{deficiencyIndex}")
    public RedirectView fjernMangel(@PathVariable int deficiencyIndex, RedirectAttributes redirectAttributes){
        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        Deficiency deficiency = surveyReport.getDeficiencies().get(deficiencyIndex);

        surveyReportService.removeDeficiency(deficiency);

        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
        redirectAttributes.addFlashAttribute("leaseAgreement", leaseAgreement);

        return new RedirectView("/edit-survey-report", true);
    }

}
