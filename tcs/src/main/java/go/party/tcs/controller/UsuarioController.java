package go.party.tcs.controller;

import java.io.IOException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;
import go.party.tcs.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro() {
        return "login"; // Retorne a página que contém o formulário de login e cadastro
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
    // Criptografar a senha antes de salvar
    String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);

        try {
         usuarioService.cadastrarUsuario(usuario, model);
            
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "O username já está em uso. Escolha outro username.");
            // Trate outras exceções aqui, se necessário
        }


    return "redirect:/login"; // Redirecionar para a página de login após o cadastro
}

    //Caso de erro no login, ele retorna para pagina de login
    @GetMapping("/loginValida")
    public String loginPage() {
        return "login";
    }

    //Método para validar as informações do Usuario para o Login 
    @PostMapping("/loginValida")
    public String login(@RequestParam("usuarioNome") String usuarioNome, @RequestParam("senha") String senha, Model model, HttpSession session) {
        // Consulte o banco de dados para verificar se o usuário existe
        Usuario usuario = usuarioService.findByUsername(usuarioNome);
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

    //Método para atribuir uma sessão ao usuario que fizer login
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

    //Método para Editar as informações do Usuario
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
        // Passo 1: Recupere o usuário da sessão.
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        // Passo 2: Obtenha o ID do usuário da sessão.
        Integer userId = sessionUsuario.getId();

        // Passo 3: Use o ID para carregar o usuário correspondente do banco de dados.
        Usuario usuarioNoBanco = usuarioService.encontrarId(userId); // Substitua 'usuarioService' pelo seu serviço de usuário.

        // Passo 4: Atualize as informações do usuário com os novos valores.
        if (novoUsuarioNome != null && !novoUsuarioNome.isEmpty()) {
            usuarioNoBanco.setUsername(novoUsuarioNome);
        }
        if (novoEmail != null && !novoEmail.isEmpty()) {
            usuarioNoBanco.setEmail(novoEmail);
        }
        if (novaDescricao != null && !novaDescricao.isEmpty()) {
            usuarioNoBanco.setDescricao(novaDescricao);
        }
        if (novaIdade != null && !novaIdade.isEmpty()) {
            LocalDate idade = LocalDate.parse(novaIdade);
            usuarioNoBanco.setIdade(idade);
        }
        if (novaSenha != null && !novaSenha.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            usuarioNoBanco.setSenha(senhaCriptografada);
        }
        

        // Passo 6: Salve as alterações no banco de dados.
        usuarioService.cadastrarUsuario(usuarioNoBanco, model); // Substitua 'usuarioService' pelo seu serviço de usuário.

        // Passo 7: Atualize a sessão com o usuário atualizado.
        session.setAttribute("usuario", usuarioNoBanco);

        return "redirect:/perfil";
    }


    @DeleteMapping("/deletar")
    public String deletarUsuario(Model model, HttpSession session, HttpServletRequest request) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        
        if (sessionUsuario != null) {
            try {
                // Aqui você pode usar o repositório JPA para deletar o usuário
                usuarioRepository.delete(sessionUsuario);
                
                // Remova o usuário da sessão
                session.removeAttribute("usuario");
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        }

        return "redirect:/login";
    }

    //Metodo para fazer o logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Remova o usuário da sessão para efetuar logout
        session.removeAttribute("usuario");
        return "redirect:/loginValida";
    }
    
    @PostMapping("/upload")
    public String uploadFotoPerfil(@RequestParam("fotoPerfil") MultipartFile fotoPerfil, Model model, HttpSession session) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        
        if (sessionUsuario != null) {
            try {
                // Faça a validação da imagem, se necessário
                if (!fotoPerfil.isEmpty()) {
                    // Converta a imagem para um array de bytes
                    byte[] fotoBytes = fotoPerfil.getBytes();
                    
                    // Associe a imagem de perfil ao usuário
                    sessionUsuario.setFotoPerfil(fotoBytes);
                    
                    // Salve o usuário no banco de dados
                    usuarioService.atualizarUsuario(sessionUsuario);;
                    
                    // Atualize a sessão com o usuário atualizado
                    session.setAttribute("usuario", sessionUsuario);
                }
                
                model.addAttribute("sessionUsuario", sessionUsuario);
                return "home";
            } catch (IOException e) {
                // Lida com exceções de IO, se ocorrerem
                e.printStackTrace();
                // Redireciona ou exibe uma mensagem de erro, conforme necessário
                return "redirect:/error";
            }
        } else {
            return "redirect:/loginValida";
        }
    }

    
}

