package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import dev.bcharthur.ludotech.repository.JeuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JeuService {

    @Autowired
    private JeuRepository jeuRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public List<Jeu> getAllJeux() {
        return jeuRepository.findAll();
    }

    public Optional<Jeu> getJeuById(Integer id) {
        return jeuRepository.findById(id);
    }

    public Jeu saveJeu(Jeu jeu) {
        return jeuRepository.save(jeu);
    }

    public void deleteJeu(Integer id) {
        jeuRepository.deleteById(id);
    }

    /**
     * Renvoie le nombre d'exemplaires disponibles (avec louable == true) pour le jeu dont l'ID est donné.
     */
    public int countAvailableExemplaires(Integer jeuId) {
        return exemplaireRepository.countByJeu_IdAndLouableTrue(jeuId);
    }
}
