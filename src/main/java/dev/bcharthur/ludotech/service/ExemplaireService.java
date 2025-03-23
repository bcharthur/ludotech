package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    // Injection du template pour les messages websocket
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Exemplaire> getAllExemplaires() {
        return exemplaireRepository.findAll();
    }

    public Optional<Exemplaire> getExemplaireById(Integer id) {
        return exemplaireRepository.findById(id);
    }

    public Exemplaire saveExemplaire(Exemplaire exemplaire) {
        Exemplaire saved = exemplaireRepository.save(exemplaire);
        // Diffuse la mise à jour de disponibilité pour le jeu concerné
        sendAvailabilityUpdate(saved.getJeu().getId());
        return saved;
    }

    public void deleteExemplaire(Integer id) {
        Optional<Exemplaire> opt = exemplaireRepository.findById(id);
        if(opt.isPresent()){
            Integer jeuId = opt.get().getJeu().getId();
            exemplaireRepository.deleteById(id);
            // Diffuse la mise à jour de disponibilité pour le jeu concerné
            sendAvailabilityUpdate(jeuId);
        }
    }

    // Méthode qui envoie la nouvelle disponibilité via WebSocket
    public void sendAvailabilityUpdate(Integer jeuId) {
        int count = exemplaireRepository.countByJeu_IdAndLouableTrue(jeuId);
        messagingTemplate.convertAndSend("/topic/availability/" + jeuId, count);
    }
}
