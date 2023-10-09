package go.party.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import go.party.tcs.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer>{
    
}
