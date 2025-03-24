package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Panier;
import dev.bcharthur.ludotech.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    Panier findByClient(Client client);
}
