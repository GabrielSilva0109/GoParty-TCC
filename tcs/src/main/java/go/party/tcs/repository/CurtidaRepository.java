package go.party.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import go.party.tcs.model.Curtida;
import go.party.tcs.model.Evento;
import go.party.tcs.model.Usuario;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    // Método para buscar uma curtida por evento e usuário
    Curtida findByEventoAndUsuario(Evento evento, Usuario usuario);

    // Método para contar o número de curtidas em um evento
    @Query("SELECT COUNT(c) FROM Curtida c WHERE c.evento = :evento")
    int countByEvento(@Param("evento") Evento evento);
}