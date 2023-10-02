package go.party.tcs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;
import go.party.tcs.service.EventoService;
import jakarta.servlet.http.HttpSession;


@Controller
public class EventoController {
    
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;
    
    
    @PostMapping("/criar-evento")
    public String criarEvento(@RequestParam("titulo") String titulo,
                              @RequestParam("descricao") String descricao,
                              @RequestParam("imagemEvento") MultipartFile imagemEvento,
                              HttpSession session) throws IOException {

        // Recupere o usuário da sessão
        // chave "usuario"
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Crie um novo evento
        Evento evento = new Evento(titulo, descricao, usuario);

        // Salve a imagem do evento 
        if (!imagemEvento.isEmpty()) {
            byte[] imagemBytes = imagemEvento.getBytes();
            evento.setFotoEvento(imagemBytes); // Supondo que você tenha um método setImagem para o evento
        }

        // Salve o evento no banco de dados (você deve implementar a lógica de persistência)
        eventoService.criarEvento(evento, null);

        return "redirect:/home"; // Redirecione para uma página de sucesso após criar o evento
    }

    

}
