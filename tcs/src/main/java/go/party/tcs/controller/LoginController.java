package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;
import go.party.tcs.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/loginValida")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/loginValida")
    public String login(@RequestParam("nome") String nome, @RequestParam("senha") String senha, Model model, HttpSession session) {
        // Consulte o banco de dados para verificar se o usuário existe
        Usuario usuario = usuarioService.findByUsuario(nome);
        boolean valida = false;
        if (usuario != null) {
            // Verificar se a senha fornecida corresponde à senha criptografada no banco de dados
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                // Autenticação bem-sucedida
                session.setAttribute("usuario", usuario); // Armazena o usuário na sessão
                valida  = true;
            }
        }
        if (valida) {
            return "redirect:/home";
        } else {
            // Autenticação falhou
            model.addAttribute("error", "Usuário ou senha incorretos!");
            return "login";
        }
    }

    @GetMapping("/home")
    public String paginaHome(Model model, HttpSession session, HttpServletRequest request){
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        if(sessionUsuario != null){
            // ... outras atribuições ao modelo
            model.addAttribute("sessionUsuario", sessionUsuario);
            // ...
            return "home";
        } else {
            return "redirect:/loginValida";
        }
    }

    @GetMapping("/evento")
    public String paginaEvento(Model model, HttpSession session, HttpServletRequest request){
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        if(sessionUsuario != null){
            // ... outras atribuições ao modelo
            model.addAttribute("sessionUsuario", sessionUsuario);
            // ...
            return "evento";
        } else {
            return "redirect:/home";
        }
    }
    
    
}
