package go.party.tcs.controller;

import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@org.springframework.stereotype.Controller
public class Controller {
    
    @GetMapping("/login")
    public String homePage(){
        return "login";
    }

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/login";
    }

   

    
    

   
    

    
}
