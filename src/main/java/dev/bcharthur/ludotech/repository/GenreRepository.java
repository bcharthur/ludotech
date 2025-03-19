package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    // Vous pouvez ajouter ici des méthodes de recherche personnalisées si nécessaire
}
