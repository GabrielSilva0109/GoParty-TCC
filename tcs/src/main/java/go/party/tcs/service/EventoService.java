package go.party.tcs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.EventoRepository;
import go.party.tcs.repository.UsuarioRepository;

@Service
public class EventoService {
   @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    public void criarEvento(Evento evento, Model model){
        eventoRepository.save(evento);
    }

    public void atualizarEvento(Evento evento){
        eventoRepository.save(evento);
    }

    public List<Evento> buscarEventosPorAutor(Integer userId) {
        // Utilize o método personalizado do seu repositório para buscar eventos por autor
        return eventoRepository.findByAutorId(userId);
    }

    public String getNomeAutorPorId(Integer autorId) {
        Optional<Usuario> autorOptional = usuarioRepository.findById(autorId);
        return autorOptional.map(Usuario::getUsername).orElse("Autor Desconhecido");
    }

    public Evento encontrarPorId(Integer eventoId) {
        return eventoRepository.getById(eventoId);
    }

    public void excluirEvento(Integer id) {
        // Lógica para excluir o evento com base no ID
        eventoRepository.deleteById(id);
    }
}
