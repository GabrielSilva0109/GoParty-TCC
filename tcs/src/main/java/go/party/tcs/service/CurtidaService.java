package go.party.tcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Curtida;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.CurtidaRepository;
import go.party.tcs.repository.EventoRepository;

@Service
public class CurtidaService {
    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;


    public void curtirEvento(Usuario usuario, Evento evento) {

        if (evento != null && !this.usuarioJaCurtiuEvento(evento.getId(), usuario)) {
                Curtida curtida = new Curtida();
                curtida.setUsuario(usuario);
                curtida.setEvento(evento);

                curtidaRepository.save(curtida);
        }
    }

    
    public void descurtirEvento(Usuario usuario, Evento evento) {
        // Implemente a lógica para descurtir o evento
        // Por exemplo, você pode remover a curtida do evento associada ao usuário
        Curtida curtida = curtidaRepository.findByUsuarioAndEvento(usuario, evento);
        if (curtida != null) {
            curtidaRepository.delete(curtida);
        }
    }

    public long contarCurtidasDoEvento(Evento evento) {
        return curtidaRepository.countByEvento(evento);
    }

    private boolean usuarioJaCurtiuEvento(Integer eventoId, Usuario usuario) {
        Evento evento = eventoService.encontrarPorId(eventoId);
        return curtidaRepository.findByEventoAndUsuario(evento, usuario) != null;
    }
}
