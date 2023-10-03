package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Random;

import go.party.tcs.service.EmailService;
import jakarta.mail.MessagingException;

@Controller
public class SenhaResetController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/recuperarSenha")
    public String enviarEmailDeRecuperacao(@RequestParam("email") String email) throws MessagingException {
        // Gere um código de recuperação e envie-o por e-mail
        String codigoRecuperacao = gerarCodigoRecuperacao();

        String assunto = "Recuperação de senha | GoParty";
        String mensagem = "Use o código a seguir para redefinir sua senha: " + codigoRecuperacao;

        emailService.sendEmailToClient(email, assunto, mensagem);

        // Redirecione para uma página de confirmação ou retorne uma resposta apropriada
        return "redirect:/login";
    }

    // Método de geração de códigos aleatórios alfanuméricos
    public String gerarCodigoRecuperacao() {
        // Defina os caracteres permitidos no código
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        // Gere um código de 10 caracteres
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }

        return codigo.toString();
    }
}
