package go.party.tcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import go.party.tcs.model.Comentario;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.service.ComentarioService;
import go.party.tcs.service.EventoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ComentarioController {
    
    @Autowired
    private EventoService eventoService;

    @Autowired
    private ComentarioService comentarioService;

    //Metodo para Criar um Comentario
    @PostMapping("/evento/{eventoId}/comentar")
    public String criarComentario(@PathVariable Integer eventoId,
        Comentario comentario,
        HttpSession session
    ) {
        // Recupere o usuário da sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Recupere o evento pelo ID
        Evento evento = eventoService.encontrarPorId(eventoId); // Substitua eventoService pelo serviço apropriado
        
        // Associe o usuário e o evento ao comentário
        comentario.setAutor(usuario);
        comentario.setEvento(evento);
        
        // Salve o comentário no banco de dados (você deve implementar esse método em seu serviço)
        comentarioService.save(comentario); // Substitua comentarioService pelo serviço apropriado
        
        // Redirecione de volta para a página do evento
        return "redirect:/perfil";

    }

    @GetMapping("/evento/{eventoId}/comentarios")
    public String exibirComentarios(@PathVariable Integer eventoId, Model model) {
        // Recupere os comentários associados ao evento pelo eventoId
        List<Comentario> comentarios = comentarioService.encontrarComentariosPorEvento(eventoId); // Substitua comentarioService pelo serviço apropriado

        // Adicione os comentários ao modelo para serem exibidos na página
        model.addAttribute("comentarios", comentarios);

        return "evento"; 
    }


}
