package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Random;

import go.party.tcs.service.EmailService;
import go.party.tcs.service.UsuarioService;
import jakarta.mail.MessagingException;

@Controller
public class SenhaResetController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    private String codigoRecuperacao;

    @PostMapping("/recuperarSenha")
    public String enviarEmailDeRecuperacao(@RequestParam("email") String email, Model model) throws MessagingException {

        boolean emailExiste = usuarioService.emailExiste(email);
        // Gere um código de recuperação e envie-o por e-mail
        codigoRecuperacao = gerarCodigoRecuperacao();

        String assunto = "Recuperação de senha | GoParty";
        String mensagem = "Use o código a seguir para redefinir sua senha: " + codigoRecuperacao;

        if (emailExiste){
            emailService.sendEmailToClient(email, assunto, mensagem);
            return "codigoRecuperacao";
        }else {
            model.addAttribute("mensagem", "Não existe um cadastro com este E-mail.");
            return "recuperarSenha";
        }

        // Redirecione para uma página de confirmação ou retorne uma resposta apropriada
        
    }

     @PostMapping("/digitarCodigo")
    public String checarCodigoDigitado(@RequestParam("codigo") String codigoDigitado, Model model) throws MessagingException {

         
        if (codigoDigitado.equalsIgnoreCase(codigoRecuperacao)){
            
            return "trocaDeSenha";
        }else {
            model.addAttribute("mensagemCodigo", "Código inválido!.");
            return "codigoRecuperacao";
        }
        // Redirecione para uma página de confirmação ou retorne uma resposta apropriada
        
    }

    //EM CONSTRUÇAO
    @PutMapping("/trocaDeSenha")
    public String realizarTrocaSenha(@RequestParam("novaSenha") String senhaNova, Model model) throws MessagingException {
     
            return "recuperarSenha";
    }

    // Método de geração de códigos aleatórios alfanuméricos
    public String gerarCodigoRecuperacao() {
        // Defina os caracteres permitidos no código
        String caracteres = "0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        // Gere um código de 6 caracteres
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }

}
