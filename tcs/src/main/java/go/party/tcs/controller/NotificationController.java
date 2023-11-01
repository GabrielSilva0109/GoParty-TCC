package go.party.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import go.party.tcs.service.NotificationService;

@Controller
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @DeleteMapping("/excluir-notificacao")
    public String excluirNotificacao(@RequestParam("id") Long id) {
        notificationService.excluirNotificacao(id); 
        return "redirect:/notificacoes"; 
    }
    
}
