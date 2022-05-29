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
            redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);
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
            LeaseAgreement leaseAgreement = (LeaseAgreement) inputFlashMap.get("lejeaftale");
            SurveyReport surveyReport = leaseAgreement.getSurveyReport();
            surveyReportService.setSurveyReport(surveyReport);


            m.addAttribute("leaseAgreement", leaseAgreement);
            m.addAttribute("surveyReport", surveyReport);
            m.addAttribute("isGodkendt", true);

            System.out.println(leaseAgreement);

            return "tilstandsrapport";

        } else {
            return "redirect:/godkendteLejeaftaler";
        }
    }

    @PostMapping("/tilføj-injury")
    public RedirectView tilføjSkade(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");


        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        if(surveyReportService.addInjury(titel, beskrivelse, pris)){

            LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
            redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);
        }
        return new RedirectView("/rediger-tilstandsrapport", true);
    }

    @PostMapping("/tilføj-deficiency")
    public RedirectView tilføjMangel(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String titel = request.getParameter("titel");
        String beskrivelse = request.getParameter("beskrivelse");
        String pris = request.getParameter("pris");


        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        if(surveyReportService.addShortcoming(titel, beskrivelse, pris)){

            LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
            redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);
        }

        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/fjern-injury/{skadeIdx}")
    public RedirectView fjernSkade(@PathVariable int skadeIdx, RedirectAttributes redirectAttributes){


        SurveyReport surveyReport = surveyReportService.getSurveyReport();
        System.out.println(surveyReport);

        Injury injury = surveyReport.getInjuries().get(skadeIdx);
        System.out.println(injury);

        surveyReportService.removeInjury(injury);


        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
        redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);



        return new RedirectView("/rediger-tilstandsrapport", true);

    }

    @PostMapping("/fjern-deficiency/{mangelIdx}")
    public RedirectView fjernMangel(@PathVariable int mangelIdx, RedirectAttributes redirectAttributes){
        SurveyReport surveyReport = surveyReportService.getSurveyReport();

        Deficiency deficiency = surveyReport.getDeficiencies().get(mangelIdx);

        surveyReportService.removeShortcoming(deficiency);

        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(surveyReport.getAgreementId());
        redirectAttributes.addFlashAttribute("lejeaftale", leaseAgreement);

        return new RedirectView("/rediger-tilstandsrapport", true);
    }

}
