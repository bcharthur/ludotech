package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    int countByJeu_IdAndLouableTrue(Integer jeuId);
    List<Exemplaire> findByJeu_IdAndLouableTrue(Integer jeuId);
    Exemplaire findByCodebarreAndLouableTrue(String codebarre);
    Exemplaire findByCodebarre(String codebarre);
}
