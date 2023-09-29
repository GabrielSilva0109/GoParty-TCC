package go.party.tcs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import go.party.tcs.repository.EventoRepository;
import go.party.tcs.service.EventoService;


@Controller
public class EventoController {
    
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;
    
    /* 
    @PostMapping("/criar-evento")
    public String criarEvento(@RequestParam("titulo") String titulo,
                              @RequestParam("descricao") String descricao,
                              @RequestParam("imagemEvento") MultipartFile imagemEvento,
                              HttpSession session) throws IOException {

        // Recupere o usuário da sessão (você deve implementar a lógica para armazenar o usuário na sessão)
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Supondo que você armazene o usuário na sessão com a chave "usuario"

        // Crie um novo evento
        Evento evento = new Evento(titulo, descricao, usuario);

        // Salve a imagem do evento (você deve implementar a lógica de armazenamento de imagem)
        if (!imagemEvento.isEmpty()) {
            byte[] imagemBytes = imagemEvento.getBytes();
            evento.setImagem(imagemBytes); // Supondo que você tenha um método setImagem para o evento
        }

        // Salve o evento no banco de dados (você deve implementar a lógica de persistência)
        eventoService.salvarEvento(evento);

        return "redirect:/pagina-de-sucesso"; // Redirecione para uma página de sucesso após criar o evento
    }

    */

}
