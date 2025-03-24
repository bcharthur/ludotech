// ClientMagasinService.java
package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.repository.ClientMagasinRepository;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientMagasinService {

    @Autowired
    private ClientMagasinRepository clientMagasinRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public List<ClientMagasin> getAllFiches() {
        return clientMagasinRepository.findAll();
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

    public List<ClientMagasin> getAllUsers() {
        return clientMagasinRepository.findAll();
    }
}
