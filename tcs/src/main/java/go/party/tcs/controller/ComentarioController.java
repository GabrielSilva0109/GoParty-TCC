package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import go.party.tcs.model.Comentario;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.service.ComentarioService;
import go.party.tcs.service.EventoService;
import go.party.tcs.service.NotificationService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ComentarioController {
    
    @Autowired
    private EventoService eventoService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ComentarioService comentarioService;

    private Usuario usuarioLogado = new Usuario();

    private Integer eventoId;

    //Metodo para Criar um Comentario
    @PostMapping("/evento/{eventoId}/comentar")
    public String criarComentario(@PathVariable Integer eventoId,
        Comentario comentario,
        HttpSession session,
        Model model
    ) {
        // Recupere o usuário da sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuarioLogado = usuario;
        eventoId = eventoId;
        
        // Recupere o evento pelo ID
        Evento evento = eventoService.encontrarPorId(eventoId); // Substitua eventoService pelo serviço apropriado
        
        // Associe o usuário e o evento ao comentário
        comentario.setAutor(usuario);
        comentario.setEvento(evento);
        
        // Salve o comentário no banco de dados (você deve implementar esse método em seu serviço)
        comentarioService.save(comentario); // Substitua comentarioService pelo serviço apropriado

        // NOTIFICAR O USUÀRIO
        // Crie uma notificação
        byte[] fotoPerfil = usuario.getFotoPerfil();
        String message = usuario.getUsername()+" fez um comentário: " +comentario.getTexto()+
         " no seu post: "+evento.getTitulo();
        Integer userIdToNotify =  evento.getAutor().getId();

        notificationService.createNotification(message, userIdToNotify, fotoPerfil);

        model.addAttribute("sessionUsuario", usuario);
        // Redirecione de volta para a página do evento
        return "redirect:/home";

    }



}
