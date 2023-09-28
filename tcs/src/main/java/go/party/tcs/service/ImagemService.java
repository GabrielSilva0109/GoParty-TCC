package go.party.tcs.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import go.party.tcs.model.Imagem;
import go.party.tcs.repository.ImagemRepository;
import go.party.tcs.repository.UsuarioRepository;

@Service
public class ImagemService {
    
    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Imagem salvarImagem(MultipartFile file) {
        return null;
    }



    


}
