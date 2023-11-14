package go.party.tcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Message;
import go.party.tcs.model.Usuario;
import go.party.tcs.repository.MessageRepository;
import go.party.tcs.repository.UsuarioRepository;

@Service
public class MensagemService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarMensagem(Integer id, String message, Integer idUsuarioSessao) {
      
        //CONTRUCAO DA MENSAGEM
        Message mensagem = new Message();

        mensagem.setContent(message);
        mensagem.setReceiver(id);
        mensagem.setSender(idUsuarioSessao);
        mensagem.setVista(false);

        messageRepository.save(mensagem);
    }
    
}
