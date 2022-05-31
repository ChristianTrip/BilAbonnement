package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.users.User;
import com.example.bilabonnement.models.users.UserType;
import com.example.bilabonnement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


    private HttpSession session;
    private User currentUser;
    private UserType userType;


   @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/login-submit")
    public RedirectView submitLogin(HttpServletRequest request, RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("user", new User("john", "password", UserType.ADMIN));
        return new RedirectView("/login-success", true);

/*
        UserService userService = new UserService();

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.validateUserinfo(userName, password);

        if (user != null) {
            redirectAttributes.addFlashAttribute("user", user);
            return new RedirectView("/login-success", true);
        } else {
            System.out.println("Login failed: wrong user information");
            return new RedirectView("/", true);
        }*/
    }



    @GetMapping({"/login-success"})
    public String getLoginSuccess(HttpServletRequest request) {

        if (currentUser != null){
            session = request.getSession(false);
            return "home";
        }
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            currentUser = (User) inputFlashMap.get("user");
            userType = currentUser.getUserType();
            session = request.getSession();
            System.out.println(currentUser.getName() + " is now logged in " + userType + " user");
            return "home";
        }
        else {
            return "redirect:/login-submit";
        }
    }

    @PostMapping("/log-out")
    public String logOut(){

        session.invalidate();
        System.out.println(currentUser.getName() + " er nu logget ud");
        currentUser = null;
        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(HttpSession session, WebRequest request){

        UserService bs = new UserService();

        String brugernavn = request.getParameter("brugernavn");
        String adgangskode = request.getParameter("adgangskode");

       UserType userType = bs.determineBrugertype(brugernavn, adgangskode);

            if(userType.equals(UserType.ADMIN)) {
                session.setAttribute("admin", userType);
                return "redirect:/home";
            }
            else if(userType.equals(UserType.LEASEAPPROVER)) {
                System.out.println("datareg");
                return "redirect:/dataRegBruger";
            }
            else if(userType.equals(UserType.BUSINESS)) {
                System.out.println("forretningsreg");
                return "redirect:/forretningBruger";
            }
            else if(userType.equals(UserType.SURVEYREPORTER)) {
                System.out.println("skadereg");
                return "redirect:/skadeRegBruger";
            }

        return "redirect:/";
    }
}
