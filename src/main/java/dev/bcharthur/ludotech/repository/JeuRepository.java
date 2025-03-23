package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JeuRepository extends JpaRepository<Jeu, Integer> {
    // Vous pouvez ajouter ici des méthodes de recherche personnalisées si nécessaire
    boolean existsByTitre(String titre);
}
