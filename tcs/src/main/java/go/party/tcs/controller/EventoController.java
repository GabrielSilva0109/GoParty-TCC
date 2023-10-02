package go.party.tcs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;
import go.party.tcs.service.EventoService;
import jakarta.servlet.http.HttpServletRequest;
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

    
    @GetMapping("/eventosUsuario")
    public String eventosUsuario(Model model, HttpSession session, HttpServletRequest request){
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        if (sessionUsuario != null) {
            // Obtenha o ID do usuário da sessão
            Integer userId = sessionUsuario.getId();

            // Use o ID do usuário para buscar eventos criados por esse usuário no banco de dados
            List<Evento> eventosDoUsuario = eventoService.buscarEventosPorAutor(userId);

            // Adicione a lista de eventos ao modelo para exibição na página
            model.addAttribute("eventos", eventosDoUsuario);

            return "perfil";
        } else {
            // O usuário não está autenticado, redirecione para a página de login
            return "redirect:/login";
        }
        
    }
    
    @GetMapping("/evento-imagem/{eventoId}")
    public ResponseEntity<byte[]> getImagemEvento(@PathVariable Integer eventoId) {
        // Recupere os detalhes do evento com base no ID do evento
        Evento evento = eventoService.encontrarPorId(eventoId);

        // Verifique se o evento foi encontrado
        if (evento != null) {
            // Recupere a imagem do evento
            byte[] imagemEvento = evento.getFotoEvento();

            if (imagemEvento != null && imagemEvento.length > 0) {
                // Defina os cabeçalhos de resposta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // ou MediaType.IMAGE_PNG, dependendo do tipo de imagem

                // Retorna a imagem como uma resposta HTTP
                return new ResponseEntity<>(imagemEvento, headers, HttpStatus.OK);
            }
        }

        // Se o evento não for encontrado ou não tiver uma imagem, retorne uma resposta vazia ou um erro
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
