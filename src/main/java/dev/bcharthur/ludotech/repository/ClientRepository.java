package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
}
