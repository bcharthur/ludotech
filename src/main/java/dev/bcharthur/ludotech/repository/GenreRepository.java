package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByLibelle(String libelle);
}
