package go.party.tcs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;

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
        Usuario usuario = usuarioRepository.findByEmail(email);
        return true;
    }

   
}

