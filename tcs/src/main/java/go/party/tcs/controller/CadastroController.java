package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import go.party.tcs.model.Usuario;
import go.party.tcs.service.UsuarioService;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    // Injete o serviço do usuário aqui

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String exibirFormularioCadastro() {
        return "login"; // Retorne a página que contém o formulário de login e cadastro
    }

    @PostMapping
    public String cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // Adicione a lógica para cadastrar o usuário usando o serviço
        usuarioService.cadastrarUsuario(usuario);
        return "redirect:/login"; // Redirecionar para a página de login após o cadastro
    }
}

