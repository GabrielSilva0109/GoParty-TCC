package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Ingresso;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;
import go.party.tcs.repository.IngressoRepository;
import jakarta.mail.search.IntegerComparisonTerm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// Importações omitidas por brevidade

@Controller
public class IngressoController {
    
    @Autowired
    IngressoRepository ingressoRepository;

    @Autowired
    EventoRepository eventoRepository; // Se houver um repositório para Evento

    @GetMapping("/ingressos")
    public String ingressos(Model model, HttpSession session, HttpServletRequest request){
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        if (sessionUsuario != null) {
            List<Ingresso> ingressos = ingressoRepository.findByIdUsuario(sessionUsuario.getId());
            model.addAttribute("ingressos", ingressos);
        }
        model.addAttribute("sessionUsuario", sessionUsuario);
        return "ingressos";
    }


    @PostMapping("/comprar-ingresso")
    private String comprarIngresso(@RequestParam("eventoId") Long eventoId,
                                @RequestParam("cpfComprador") String cpfComprador,
                                Model model,
                                HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getId();
        
        // Buscar o evento no banco de dados com base no eventoId
        Evento evento = eventoRepository.findById(eventoId);
        
        if (evento != null) {
            // Gerar o código aleatório para o ingresso
            String codigoIngresso = Ingresso.gerarCodigoAleatorio();

            // Criar o objeto Ingresso com os IDs já obtidos
            Ingresso ingresso = new Ingresso(codigoIngresso, usuario, evento, cpfComprador);

            ingressoRepository.save(ingresso);
            model.addAttribute("sessionUsuario", usuario);
            return "redirect:/ingressos";
        } else {
            // Lógica para lidar com o evento não encontrado
            return "eventoNaoEncontrado";
        }
    }
}

