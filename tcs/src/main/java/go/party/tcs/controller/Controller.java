package go.party.tcs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;
import go.party.tcs.service.EventoService;
import go.party.tcs.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoService eventoService;
    
    //Mapea o Login
    @GetMapping("/login")
    public String homePage(){
        return "login";
    }

    @GetMapping("/trocaDeSenha")
    public String trocarSenha(){
        return "trocaDeSenha";
    }

    @GetMapping("/digitarCodigo")
    public String digitarCodigo() {
        return "codigoRecuperacao"; // Retorna o nome do arquivo HTML
    }

    @GetMapping("/recuperarSenha")
    public String recuperarPage() {
        return "recuperarSenha"; // Retorna o nome do arquivo HTML
    }

    //Sem mapeamento redireciona para o Login
    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/login";
    }

    @GetMapping("/perfilUsuario/{id}")
    public String exibirPerfil(@PathVariable Integer id, Model model) {
        
        // Buscar o usuário com o ID especificado no banco de dados
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

           // int seguidores = usuarioService.contarSeguidores(usuario.getId());
           // int seguindo = usuarioService.contarSeguindo(usuario.getId());

            model.addAttribute("seguidores", 0);
            model.addAttribute("seguindo", 0);


            // Adicionar o usuário ao modelo para que ele possa ser exibido na página de perfil
            model.addAttribute("usuario", usuario);

            // Buscar os eventos criados por esse usuário com base no ID do usuário
            List<Evento> eventosDoUsuario = eventoService.buscarEventosPorAutor(id);

            // Adicionar a lista de eventos ao modelo para exibição na página
            model.addAttribute("eventos", eventosDoUsuario);

            return "perfilUsuario"; // Isso renderizará a página de perfil do usuário específico
        } else {
            // Lide com o caso em que o usuário não foi encontrado
            return "redirect:/usuarios"; // Redirecione para uma página de lista de usuários, por exemplo
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
 
}
