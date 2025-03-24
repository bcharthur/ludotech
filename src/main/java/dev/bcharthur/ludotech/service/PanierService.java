package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.models.Panier;
import dev.bcharthur.ludotech.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    public Panier getPanierByClient(Client client) {
        Panier panier = panierRepository.findByClient(client);
        if (panier == null) {
            panier = new Panier(client);
            panierRepository.save(panier);
        }
        return panier;
    }

    public Panier addJeuToPanier(Client client, Jeu jeu) {
        Panier panier = getPanierByClient(client);
        if (panier.getJeux().size() >= 5) {
            throw new IllegalArgumentException("Le panier ne peut contenir que 5 jeux maximum.");
        }
        panier.getJeux().add(jeu);
        return panierRepository.save(panier);
    }

    public Panier removeJeuFromPanier(Client client, Jeu jeu) {
        Panier panier = getPanierByClient(client);
        panier.getJeux().remove(jeu);
        return panierRepository.save(panier);
    }

    public void clearPanier(Client client) {
        Panier panier = getPanierByClient(client);
        panier.getJeux().clear();
        panierRepository.save(panier);
    }
}
