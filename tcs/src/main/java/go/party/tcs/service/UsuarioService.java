package go.party.tcs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(Usuario usuario, Model model) {
       
       usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    
    public Usuario findByUsuario(String usuarioNome){
        return usuarioRepository.findByNome(usuarioNome);
    }

    public Usuario findByUsername(String usuarioNome){
        return usuarioRepository.findByUsername(usuarioNome);
    }

    public Usuario encontrarId(Integer userId){
        return usuarioRepository.getById(userId);
    }

    // VERIFICAR A EXISTENCIA DE CPF NO BANCO 

    public boolean existeUsuarioComCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    public String getUsernameById(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return usuario.getUsername();
        } else {
            // Lide com o caso em que o usuário não foi encontrado (lançar exceção ou retornar um valor padrão)
            return "Usuário não encontrado";
        }
    }

    public boolean emailExiste(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.isPresent();
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o email: " + email));
    }

    //TESTE DE SEGUIDORES NO SISTEMA

    public void seguir(Long seguidorId, Long seguirId) {
        Usuario seguidor = usuarioRepository.findById(seguidorId);
        Usuario seguir = usuarioRepository.findById(seguirId);
        
        seguidor.getSeguindo().add(seguir);
        usuarioRepository.save(seguidor);
    }
    
    public void deixarDeSeguir(Long seguidorId, Long seguirId) {
        Usuario seguidor = usuarioRepository.findById(seguidorId);
        Usuario seguir = usuarioRepository.findById(seguirId);
        
        seguidor.getSeguindo().remove(seguir);
        usuarioRepository.save(seguidor);
    }

   
}

