package go.party.tcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import go.party.tcs.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNome(String nome);

    Usuario findByUsername(String usuarioNome);

    boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

}
