package go.party.tcs.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Notification;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;
import go.party.tcs.repository.NotificationRepository;
import go.party.tcs.repository.UsuarioRepository;
import go.party.tcs.service.EmailService;
import go.party.tcs.service.EventoService;
import go.party.tcs.service.NotificationService;
import go.party.tcs.service.UsuarioService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;
    
    @Autowired
    private EventoService eventoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    Usuario usuarioLogado = new Usuario();

    Usuario usuarioPerfilVisitado = new Usuario();

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro() {
        return "login"; // Retorne a página que contém o formulário de login e cadastro
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) throws MessagingException {
    // Criptografar a senha antes de salvar
       String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
       usuario.setSenha(senhaCriptografada);

       //ENVIO DE EMAIL QUANDO O USUÁRIO EXCLUI CONTA
        String assunto = "Bem-vindo ao GoParty - Sua Rede Social de Eventos e Festas!";

        String mensagem = "Olá " + usuario.getUsername() + ",\r\n" + //
                "\r\n" + //
                "Bem-vindo ao GoParty! Estamos empolgados por você ter se cadastrado em nossa plataforma dedicada a tornar seus eventos e festas ainda mais incríveis.\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "Seja bem-vindo ao GoParty, onde cada evento se torna uma celebração memorável.\r\n" + //
                "\r\n" + //
                "Atenciosamente,\r\n" + //
                "\r\n" + //
                "A Equipe GoParty";

         //Validação por CPF
        boolean existeUsuario = usuarioRepository.existsByCpf(usuario.getCpf());
        
        if (existeUsuario) {
           model.addAttribute("mensagem", "Já existe um usuário cadastrado com o CPF informado!.");
            return "login"; // Redirecionar para a página de login após o cadastro

        }else{
            //SERVICE DE SALVAR USUÁRIO
            usuarioService.cadastrarUsuario(usuario, model);
            emailService.sendEmailToClient(usuario.getEmail(), assunto, mensagem);

             return "redirect:/login"; // Redirecionar para a página de login após o cadastro
        }
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
        usuarioLogado = usuario;
        boolean valida = false;

        List<Notification> notifications = notificationRepository.findByUserId(usuario.getId());
        

        //MOSTRAR CONTADOR DE SEGUIDORES
        List<Usuario> followers = usuarioService.getFollowers(usuario);
        List<Usuario> following = usuarioService.getFollowing(usuario);

        usuario.setSeguidores(followers.size());;
        usuario.setSeguindo(following.size());

        if (usuario != null) {
            // Verificar se a senha fornecida corresponde à senha criptografada no banco de dados
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                // Autenticação bem-sucedida
                session.setAttribute("usuario", usuario); // Armazena o usuário na sessão
                model.addAttribute("notifications", notifications);
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
            List<Evento> eventos = eventoService.getAllEventos(); 
            model.addAttribute("eventos", eventos); 
            return "home"; 
        } else {
            return "redirect:/loginValida";
        }
    }
    
    //Metodo para Editar a Conta do Usuario
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

        // Passo 6: Atualize as alterações no banco de dados.
        usuarioService.atualizarUsuario(usuarioNoBanco); // Substitua 'usuarioService' pelo seu serviço de usuário.

        // Passo 7: Atualize a sessão com o usuário atualizado (opcional).
        session.setAttribute("usuario", usuarioNoBanco);

        return "redirect:/perfil";
    }

    //Metodo para Excluir a Conta do Usuario
    @DeleteMapping("/deletar")
    public String deletarUsuario(Model model, HttpSession session, HttpServletRequest request) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        //ENVIO DE EMAIL QUANDO O USUÁRIO EXCLUI CONTA
        String assunto = "Exclusão de conta | GoParty";
        String mensagem = "Você deletou sua conta no GoParty!"
                        + "Esperamos que você volte em breve. ";
        
        if (sessionUsuario != null) {
            try {
        
            // Excluir todos os eventos associados ao usuário
            eventoRepository.deleteByAutor(sessionUsuario);

            // Em seguida, excluir o usuário
            usuarioRepository.delete(sessionUsuario);
            emailService.sendEmailToClient(sessionUsuario.getEmail(), assunto, mensagem);       
                
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
                return "perfil";
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

    @GetMapping("/perfil-imagem-session/{usuarioId}")
    public ResponseEntity<byte[]> getImagemPerfilSession(@PathVariable Integer usuarioId, HttpSession session) {
        // Recupere o usuário da sessão
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");
        
        // Verifique se o usuário na sessão corresponde ao ID especificado na rota
        if (sessionUsuario != null && sessionUsuario.getId().equals(usuarioId)) {
            // Recupere a imagem de perfil do usuário da sessão
            byte[] imagemPerfil = sessionUsuario.getFotoPerfil();
            
            if (imagemPerfil != null && imagemPerfil.length > 0) {
                // Defina os cabeçalhos de resposta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // ou MediaType.IMAGE_PNG, dependendo do tipo de imagem

                // Retorna a imagem como uma resposta HTTP
                return new ResponseEntity<>(imagemPerfil, headers, HttpStatus.OK);
            }
        }
        
        // Se o usuário na sessão não corresponder ao ID, retorne uma resposta vazia ou um erro
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/perfil-imagem/{usuarioId}")
    public ResponseEntity<byte[]> getImagemPerfil(@PathVariable Integer usuarioId) {
        // Recupere os detalhes do usuário com base no ID do usuário
        Usuario usuario = usuarioService.encontrarId(usuarioId);
        usuarioPerfilVisitado = usuario;

        // Verifique se o usuário foi encontrado
        if (usuario != null) {
            // Recupere a imagem de perfil do usuário
            byte[] imagemPerfil = usuario.getFotoPerfil();

            if (imagemPerfil != null && imagemPerfil.length > 0) {
                // Defina os cabeçalhos de resposta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // ou MediaType.IMAGE_PNG, dependendo do tipo de imagem

                // Retorna a imagem como uma resposta HTTP
                return new ResponseEntity<>(imagemPerfil, headers, HttpStatus.OK);
            }
        }
        // Se o usuário não for encontrado ou não tiver uma imagem de perfil, retorne uma resposta vazia ou um erro
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TESTE DE SEGUIDORES NO SISTEMA

    @PostMapping("/follow")
    public String followUser() {

        Usuario follower = usuarioLogado;
        Usuario following = usuarioPerfilVisitado;

        usuarioService.follow(follower, following);

        // NOTIFICAR O USUÀRIO

        // Crie uma notificação
        String message = follower.getUsername()+" seguiu você";
        Integer userIdToNotify =  usuarioPerfilVisitado.getId();

        notificationService.createNotification(message, userIdToNotify);
        return "redirect:/home";
    }

    @PostMapping("/unfollow")
    public String unfollowUser() {
       Usuario follower = usuarioLogado;
        Usuario following = usuarioPerfilVisitado;

        usuarioService.unfollow(follower, following);
        return "redirect:/home";
    }

}

