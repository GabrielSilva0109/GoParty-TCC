package go.party.tcs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import go.party.tcs.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    
    List<Evento> findByAutorId(Integer userId);
}
