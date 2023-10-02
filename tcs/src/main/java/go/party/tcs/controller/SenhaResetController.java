package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

import go.party.tcs.service.EmailService;

@Controller
public class SenhaResetController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/recuperaSenha")
    public String enviarEmailDeRecuperacao(@RequestParam("email") String email) {
        // Gere um código de recuperação (pode ser aleatório) e envie-o por e-mail
        String codigoRecuperacao = gerarCodigoRecuperacao();

        String assunto = "Recuperação de senha | GoParty";
        String mensagem = "Use o código a seguir para redefinir sua senha: " + codigoRecuperacao;

        emailService.sendPasswordResetEmail(email, assunto, mensagem);

        // Redirecione para uma página de confirmação ou retorne uma resposta apropriada
        return "redirect:/pagina-de-confirmacao";
    }

    //METODO DE GERACAO DE CODIGOS ALEATORIOS

public String gerarCodigoRecuperacao() {
    // Gere um UUID (Universally Unique Identifier) aleatório e retorne sua representação como string
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
}
}


