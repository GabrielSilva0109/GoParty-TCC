package go.party.tcs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import go.party.tcs.model.Ingresso;
import go.party.tcs.repository.IngressoRepository;

@Service
public class IngressoService {
    private final IngressoRepository ingressoRepository;

    public IngressoService(IngressoRepository ingressoRepository) {
        this.ingressoRepository = ingressoRepository;
    }

    public List<Ingresso> getIngressosDoEvento(Integer eventoId) {
        return ingressoRepository.findByEventoId(eventoId);
    }
}