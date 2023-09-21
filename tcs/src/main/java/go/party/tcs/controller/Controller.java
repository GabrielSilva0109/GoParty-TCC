package go.party.tcs.controller;

import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/perfil")
    public String mostrarPerfil() {
        return "perfil";
    }
    
    @GetMapping("/evento")
    public String mostrarEvento() {
        return "evento";
    }

   
    

    
}
