package go.party.tcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class IngressoController {
    

    @PostMapping("/comprar-ingresso")
    private String comprarIngresso(HttpSession session){

        return "ingresso";
    }
}
