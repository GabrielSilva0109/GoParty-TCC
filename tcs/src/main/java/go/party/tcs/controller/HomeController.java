package go.party.tcs.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import go.party.tcs.model.Usuario;
import go.party.tcs.service.UsuarioService;
import io.micrometer.core.ipc.http.HttpSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    //Pega funções do UsuarioService
    private final UsuarioService usuarioService;
    
    public HomeController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }


    @GetMapping("/home")
    public String paginaHome(Model model, HttpSession session, HttpServletRequest request){
        Usuario tmp = (Usuario) session.getAttribute("usuario");
        if(tmp != null){
            Usuario sessionUsuario = usuarioService.findByUsuario(tmp.getNome());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma, MMMM d, yyyy");
            model.addAttribute("formatter", formatter);
            model.addAttribute("currentPath", request.getRequestURI());
            model.addAttribute("sessionUsuario", sessionUsuario);
            model.addAttribute("ususuario", sessionUsuario);
            return "home";
        } else {
            return "redirect:login";
            }
    } 


}
