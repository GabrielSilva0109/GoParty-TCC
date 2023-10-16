package go.party.tcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Curtida;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.CurtidaRepository;

@Service
public class CurtidaService {
    @Autowired
    private CurtidaRepository curtidaRepository;

    public void curtirEvento(Usuario usuario, Evento evento) {
        Curtida curtida = new Curtida();
        curtida.setUsuario(usuario);
        curtida.setEvento(evento);
        curtidaRepository.save(curtida);
    }

    public void descurtirEvento(Usuario usuario, Evento evento) {
        curtidaRepository.deleteByUsuarioAndEvento(usuario, evento);
    }

    public long contarCurtidasDoEvento(Evento evento) {
        return curtidaRepository.countByEvento(evento);
    }
}
