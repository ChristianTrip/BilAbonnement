package com.example.bilabonnement.services;

import com.example.bilabonnement.models.surveyReportElements.Deficiency;
import com.example.bilabonnement.models.surveyReportElements.Injury;
import com.example.bilabonnement.models.surveyReportElements.SurveyReport;
import com.example.bilabonnement.repositories.DeficiencyRepo;
import com.example.bilabonnement.repositories.InjuryRepo;

import static java.lang.Integer.parseInt;

public class SurveyReportService {

    private InjuryRepo injuryRepo = new InjuryRepo();
    private DeficiencyRepo deficiencyRepo = new DeficiencyRepo();
    private SurveyReport surveyReport;


    public boolean addInjury(String title, String description, String price){

        if(!title.equals("") && !description.equals("") && !price.equals("") ) {
            Injury injury = new Injury(title, description, parseInt(price));
            injury.setSurveyReportId(surveyReport.getId());
            if (injuryRepo.create(injury)) {
                return true;
            }
        }
        return false;
    }

    public boolean addDeficiency(String titel, String beskrivelse, String pris){

        if(!titel.equals("") && !beskrivelse.equals("") && !pris.equals("")) {
            Deficiency deficiency = new Deficiency(titel, beskrivelse, parseInt(pris));
            deficiency.setReportId(surveyReport.getId());
            if (deficiencyRepo.create(deficiency)){
                return true;
            }
        }
        return false;
    }

    public boolean removeInjury(Injury injury){

        if (injuryRepo.deleteById(injury.getId())){
            return true;
        }
        return false;
    }

    public boolean removeDeficiency(Deficiency deficiency){

        if (deficiencyRepo.deleteById(deficiency.getId())){
            return true;
        }
        return false;
    }

    public void setSurveyReport(SurveyReport surveyReport){
        this.surveyReport = surveyReport;
    }

    public SurveyReport getSurveyReport(){
        return surveyReport;
    }


}
