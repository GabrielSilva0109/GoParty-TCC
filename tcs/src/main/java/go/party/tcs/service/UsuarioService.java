package go.party.tcs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import go.party.tcs.model.Follower;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.FollowerRepository;
import go.party.tcs.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FollowerRepository followerRepository;

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

     public Usuario getUserById(Integer userId) {
        Optional<Usuario> userOptional = usuarioRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // Você pode tratar o caso em que o usuário não é encontrado, por exemplo, lançando uma exceção.
            return null;
        }

    }

    //TESTE DE SEGUIDORES NO SISTEMA
    public void follow(Usuario follower, Usuario following) {
        // Verifique se o usuário já está seguindo o outro usuário
        Follower existingFollower = followerRepository.findByFollowerAndFollowing(follower, following);

        if (existingFollower == null) {
            // Se não existir uma relação de seguidor, crie uma nova
            Follower newFollower = new Follower();
            newFollower.setFollower(follower);
            newFollower.setFollowing(following);
            followerRepository.save(newFollower);
        } 
    }

    public void unfollow(Usuario follower, Usuario following) {
        // Verifique se o usuário está seguindo o outro usuário
        Follower existingFollower = followerRepository.findByFollowerAndFollowing(follower, following);

        if (existingFollower != null) {
            // Se existir uma relação de seguidor, remova-a
            followerRepository.delete(existingFollower);
        }
    }

    public List<Follower> obterSeguidores(Integer userId) {
        // Aqui, você deve chamar o método do repositório para buscar os seguidores com base no ID do usuário.
        return followerRepository.findByFollowerId(userId);
    }
    

    //OBTER NUMEROS DE SEGUIDORES 
    public List<Usuario> getFollowers(Usuario user) {
        // Obtenha os seguidores do usuário
        List<Follower> followers = followerRepository.findByFollowing(user);

        List<Usuario> followerUsers = followers.stream()
            .map(Follower::getFollower)
            .collect(Collectors.toList());

        return followerUsers;
    }

    // Obtenha quem o usuário está seguindo
    public List<Usuario> getFollowing(Usuario user) {
        
        List<Follower> following = followerRepository.findByFollower(user);

        List<Usuario> followingUsers = following.stream()
            .map(Follower::getFollowing)
            .collect(Collectors.toList());

        return followingUsers;
    }

    public int getFollowersCount(Usuario user) {
        List<Usuario> followers = getFollowers(user);
        return followers.size();
    }

    public int getFollowingCount(Usuario user) {
        List<Usuario> following = getFollowing(user);
        return following.size();
    }


}

