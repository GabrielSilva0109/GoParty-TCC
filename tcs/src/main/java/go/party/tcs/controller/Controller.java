package go.party.tcs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/perfil/{id}")
    public String exibirPerfil(@PathVariable Integer id, Model model) {
        // Buscar o usuário com o ID especificado no banco de dados
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Adicionar o usuário ao modelo para que ele possa ser exibido na página de perfil
            model.addAttribute("usuario", usuario);
            return "perfilUsuarios"; // Isso renderizará a página de perfil
        } else {
            // Lide com o caso em que o usuário não foi encontrado
            return "redirect:/usuarios"; // Redirecione para uma página de lista de usuários, por exemplo
        }
    }

    
    

   
    

    
}
