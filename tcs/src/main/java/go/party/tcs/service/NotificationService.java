package go.party.tcs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Notification;
import go.party.tcs.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String message, Integer userId, byte[] fotoPerfil) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDate.now()); 
        notification.setUserId(userId);
        notification.setVisualizado(false);
        notification.setFotoPerfil(fotoPerfil);
        notificationRepository.save(notification);
    }

    public void marcarNotificacoesComoVisualizadas(Integer userId) {
        List<Notification> notificacoes = notificationRepository.findByUserId(userId);
        for (Notification notification : notificacoes) {
            notification.setVisualizado(true);
        }
        notificationRepository.saveAll(notificacoes);
    }

    public int contarNotificacoesNaoVisualizadas(Integer userId) {
        return notificationRepository.countByUserIdAndVisualizadoFalse(userId);
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    

}
