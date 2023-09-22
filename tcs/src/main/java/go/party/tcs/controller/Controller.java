package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import go.party.tcs.service.UsuarioService;
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
