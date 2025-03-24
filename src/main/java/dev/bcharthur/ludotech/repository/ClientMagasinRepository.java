package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.ClientMagasin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientMagasinRepository extends JpaRepository<ClientMagasin, Integer> {
    boolean existsByExemplaire_Id(int exemplaireId);
}
