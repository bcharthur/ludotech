// ExemplaireRepository.java
package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    // Méthodes personnalisées si nécessaire
}
