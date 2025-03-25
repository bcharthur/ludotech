// ClientMagasinService.java
package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.repository.ClientMagasinRepository;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientMagasinService {

    @Autowired
    private ClientMagasinRepository clientMagasinRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public List<ClientMagasin> getAllFiches() {
        return clientMagasinRepository.findAll();
    }

    public Optional<ClientMagasin> getById(Integer id) {
        return clientMagasinRepository.findById(id);
    }

    public ClientMagasin create(ClientMagasin fiche, Integer exemplaireId) {
        if (exemplaireId != null) {
            if (clientMagasinRepository.existsByExemplaire_Id(exemplaireId)) {
                throw new IllegalStateException("Cet exemplaire est déjà associé à un autre client magasin.");
            }
            Exemplaire ex = exemplaireRepository.findById(exemplaireId)
                    .orElseThrow(() -> new IllegalStateException("Exemplaire introuvable."));
            fiche.setExemplaire(ex);
        } else {
            fiche.setExemplaire(null);
        }
        return clientMagasinRepository.save(fiche);
    }

    public ClientMagasin update(ClientMagasin fiche) {
        // Récupérer l'entité existante en base
        Optional<ClientMagasin> optional = clientMagasinRepository.findById(fiche.getId());
        if (!optional.isPresent()) {
            throw new IllegalStateException("Fiche client magasin introuvable.");
        }
        ClientMagasin existing = optional.get();
        // Mettre à jour uniquement les champs éditables
        existing.setPrenom(fiche.getPrenom());
        existing.setNom(fiche.getNom());
        existing.setEmail(fiche.getEmail());
        existing.setTelephone(fiche.getTelephone());
        // Ne pas toucher à l'attribut exemplaire
        return clientMagasinRepository.save(existing);
    }

    public void delete(Integer id) {
        clientMagasinRepository.deleteById(id);
    }

    public List<ClientMagasin> getAllUsers() {
        return clientMagasinRepository.findAll();
    }
}
