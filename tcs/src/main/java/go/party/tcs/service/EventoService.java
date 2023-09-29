package go.party.tcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public void criarEvento(Evento evento, Model model){
        eventoRepository.save(evento);
    }

    public void atualizarEvento(Evento evento){
        eventoRepository.save(evento);
    }

    public List<Evento> findAll(){
        return eventoRepository.findAll();
    }
}
