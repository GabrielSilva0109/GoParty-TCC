package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import go.party.tcs.model.Usuario;
import go.party.tcs.service.UsuarioService;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    // Injeta o serviço do usuário
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String exibirFormularioCadastro() {
        return "login"; // Retorne a página que contém o formulário de login e cadastro
    }

    @PostMapping
    public String cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
    // Criptografar a senha antes de salvar
    String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    usuarioService.cadastrarUsuario(usuario);
    return "redirect:/login"; // Redirecionar para a página de login após o cadastro
}

}

