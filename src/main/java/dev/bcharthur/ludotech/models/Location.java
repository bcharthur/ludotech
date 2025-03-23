package dev.bcharthur.ludotech.models;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Exemplaire;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_location")
    private Long id;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_retour")
    private LocalDateTime dateRetour;

    @Column(name = "tarif_jour", nullable = false)
    private BigDecimal tarifJour;

    // Chaque location est liée à un client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Une location peut concerner plusieurs exemplaires (maximum 5)
    @ManyToMany
    @JoinTable(
            name = "location_exemplaire",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "exemplaire_id")
    )
    private Set<Exemplaire> exemplaires = new HashSet<>();

    // Constructeurs, getters et setters

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    public BigDecimal getTarifJour() {
        return tarifJour;
    }

    public void setTarifJour(BigDecimal tarifJour) {
        this.tarifJour = tarifJour;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(Set<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }
}
