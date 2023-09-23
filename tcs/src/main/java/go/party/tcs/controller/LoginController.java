package go.party.tcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    //Metodo para fazer o logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Remova o usuário da sessão para efetuar logout
        session.removeAttribute("usuario");
        return "redirect:/loginValida";
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, HttpSession session, HttpServletRequest request) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        if(sessionUsuario != null){
            // ... outras atribuições ao modelo
            model.addAttribute("sessionUsuario", sessionUsuario);
            // ...
            return "perfil";
        } else {
            return "redirect:/login";
        }
    }

    //Pagina Usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model, HttpSession session, HttpServletRequest request) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        
        if (sessionUsuario != null) {
            model.addAttribute("sessionUsuario", sessionUsuario);
            
            // Aqui você pode buscar a lista de usuários da mesma forma que antes
            List<Usuario> usuarios = usuarioService.findAll();
            model.addAttribute("usuarios", usuarios);
        
            return "usuarios";
        } else {
            return "redirect:/login";
        }
    }

    @PutMapping("/editar")
    public String editarUsuario(
        @RequestParam(name = "usuarioNome", required = false) String novoUsuarioNome,
        @RequestParam(name = "email", required = false) String novoEmail,
        @RequestParam(name = "descricao", required = false) String novaDescricao,
        @RequestParam(name = "idade", required = false) String novaIdade,
        @RequestParam(name = "senha", required = false) String novaSenha,
        Model model, 
        HttpSession session, 
        HttpServletRequest request
    ) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        // Verifique quais campos estão preenchidos e atualize-os
        if (novoUsuarioNome != null && !novoUsuarioNome.isEmpty()) {
            sessionUsuario.setUsuarioNome(novoUsuarioNome);
        }
        if (novoEmail != null && !novoEmail.isEmpty()) {
            sessionUsuario.setEmail(novoEmail);
        }
        if (novaDescricao != null && !novaDescricao.isEmpty()) {
            sessionUsuario.setDescricao(novaDescricao);
        }
        if (novaIdade != null && !novaIdade.isEmpty()) {
            // Certifique-se de fazer a conversão adequada, pois idade é um campo numérico
            int idade = Integer.parseInt(novaIdade);
            sessionUsuario.setIdade(idade);
        }
        if (novaSenha != null && !novaSenha.isEmpty()) {
            sessionUsuario.setSenha(novaSenha);
        }

        // Realize a lógica para salvar o objeto de usuário atualizado no banco de dados
        // ...

        return "redirect:/perfil";
    }


    
}
