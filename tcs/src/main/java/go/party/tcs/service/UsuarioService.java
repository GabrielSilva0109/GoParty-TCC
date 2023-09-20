package go.party.tcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Usuario;
import go.party.tcs.repository.UsuarioRepository;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(Usuario usuario) {
        //adicionar lógica de validação, como verificar se o usuário já existe, antes de salvar
        usuarioRepository.save(usuario);
        // Salvar o usuário no banco de dados
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    
    public Usuario findByUsuario(String usuarioNome){
        return usuarioRepository.findByNome(usuarioNome);
    }
}

