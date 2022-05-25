package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.brugere.Bruger;
import com.example.bilabonnement.models.brugere.BrugerType;
import com.example.bilabonnement.repositories.BrugerRepo;
import com.example.bilabonnement.services.BrugerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class IndexController {



    private Bruger currentUser;

   @GetMapping("/")
    public String index(){
        System.out.println("inside index controller");
        return "index";
    }




    @PostMapping("/login-submit")
    public RedirectView submitPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {


        BrugerService bs = new BrugerService();

        String brugernavn = request.getParameter("brugernavn");
        String adgangskode = request.getParameter("adgangskode");
        //BrugerType brugerType = bs.determineBrugertype(brugernavn, adgangskode);
        Bruger bruger = bs.validateUserinfo(brugernavn, adgangskode);


        if (bruger != null) {
            redirectAttributes.addFlashAttribute("bruger", bruger);
            return new RedirectView("/login-success", true);
        } else {
            System.out.println("Login failed: Forkerte bruger information");
            return new RedirectView("/", true);
        }
    }



    @PostMapping("/login")
    public String login(HttpSession session, WebRequest request, Model model){

        BrugerService bs = new BrugerService();

        String brugernavn = request.getParameter("brugernavn");
        String adgangskode = request.getParameter("adgangskode");


       BrugerType brugerType = bs.determineBrugertype(brugernavn, adgangskode);


            if(brugerType.equals(BrugerType.ADMIN)) {
                session.setAttribute("admin", brugerType);
                return "redirect:/admin";
            }
            else if(brugerType.equals(BrugerType.DATAREG)) {
                System.out.println("datareg");
                return "redirect:/dataRegBruger";
            }
            else if(brugerType.equals(BrugerType.FORRETNING)) {
                System.out.println("forretningsreg");
                return "redirect:/forretningBruger";
            }
            else if(brugerType.equals(BrugerType.SKADEREG)) {
                System.out.println("skadereg");
                return "redirect:/skadeRegBruger";
            }

        return "redirect:/";
    }
}
