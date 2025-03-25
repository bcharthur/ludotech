package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientMagasinRepository extends JpaRepository<ClientMagasin, Integer> {
    boolean existsByExemplaire_Id(int exemplaireId);
    Optional<ClientMagasin> findByExemplaire_Id(Integer exemplaireId);
}
