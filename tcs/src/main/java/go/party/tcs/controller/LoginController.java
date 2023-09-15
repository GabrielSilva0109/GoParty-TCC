package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/loginValida")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/loginValida")
    public String login(@RequestParam("nome") String nome, @RequestParam("senha") String senha, Model model) {
        // Consulte o banco de dados para verificar se o usuário existe e a senha está correta
        Usuario usuario = usuarioRepository.findByNome(nome);

        if (usuario != null && senha.equals(usuario.getSenha())) {
            // Autenticação bem-sucedida, redirecione para a página de sucesso ou dashboard
            return "redirect:/home";
        } else {
            // Autenticação falhou, adicione uma mensagem de erro ao modelo e retorne para a página de login
            model.addAttribute("error", "Nome de usuário ou senha incorretos.");
            return "login";
        }
    }
}



