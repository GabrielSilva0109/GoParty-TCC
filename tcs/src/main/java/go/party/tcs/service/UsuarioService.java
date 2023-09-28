package go.party.tcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(Usuario usuario) {

        //Validação por CPF
        boolean existeUsuario = usuarioRepository.existsByCpf(usuario.getCpf());
        

        if (existeUsuario) {
    
        } else {
           usuarioRepository.save(usuario);
        // Salvar o usuário no banco de dados
        }
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

   
}

