package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.userElements.User;
import com.example.bilabonnement.models.userElements.UserType;
import com.example.bilabonnement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

       // for testing purpose you get logged in without submitting login information
        /*
        redirectAttributes.addFlashAttribute("user", new User("john", "password", UserType.ADMIN));
        return new RedirectView("/login-success", true);
        */

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
        }
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
        System.out.println(currentUser.getName() + " is  now logged out");
        currentUser = null;
        return "redirect:/";
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "error";
    }

}
