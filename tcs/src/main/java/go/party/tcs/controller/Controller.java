package go.party.tcs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private String nomeDigitadoP;
    
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

            // Adicionar o usuário ao modelo para que ele possa ser exibido na página de perfil
            model.addAttribute("usuario", usuario);

            // Buscar os eventos criados por esse usuário com base no ID do usuário
            List<Evento> eventosDoUsuario = eventoService.buscarEventosPorAutor(id);

            // Adicionar a lista de eventos ao modelo para exibição na página
            model.addAttribute("eventos", eventosDoUsuario);

            //MOSTRAR CONTADOR DE SEGUIDORES
            List<Usuario> followers = usuarioService.getFollowers(usuario);
            List<Usuario> following = usuarioService.getFollowing(usuario);

            model.addAttribute("followersCount", followers.size());
            model.addAttribute("followingCount", following.size());

            return "perfilUsuario"; // Isso renderizará a página de perfil do usuário específico
        } else {
            return "redirect:/usuarios";
            // Lide com o caso em que o usuário não foi encontrado return "redirect:/usuarios"; // Redirecione para uma página de lista de usuários, por exemplo
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

    //CONTINUAR

    @PostMapping("/usuarios")
    public String pesquisarUsuarios(@RequestParam("nomeDigitado") String nomeDigitado, Model model) {

        nomeDigitadoP = nomeDigitado;

        List<Usuario> usuarios = usuarioRepository.findByNomeContaining(nomeDigitadoP);
        model.addAttribute("usuarios", usuarios);

        return "usuarios";
    }


    //Pagina Usuarios
     @GetMapping("/usuarios")
     public String listarUsuarios(Model model, HttpSession session, HttpServletRequest request) {
     Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

    if (sessionUsuario != null && nomeDigitadoP == null) {
        model.addAttribute("sessionUsuario", sessionUsuario);

        // Aqui você pode buscar a lista de usuários da mesma forma que antes
        List<Usuario> usuarios = usuarioService.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getId() == sessionUsuario.getId()) {
                 usuarios.remove(sessionUsuario);
            }
        }

        model.addAttribute("usuarios", usuarios);

        return "usuarios";
    }else {
       return "redirect:/login";
    }
  }

 
}