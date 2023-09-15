package go.party.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import go.party.tcs.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNome(String nome);
}
