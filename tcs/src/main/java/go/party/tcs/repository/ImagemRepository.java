package go.party.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import go.party.tcs.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    Imagem findByImageName(String imageName);
}
