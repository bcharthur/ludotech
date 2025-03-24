package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;

import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.HashSet; import java.util.Set;

@Entity @Table(name = "location") public class Location {

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

    // Soit un client en ligne, soit un client magasin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_magasin_id", nullable = true)
    private ClientMagasin clientMagasin;

    @ManyToMany
    @JoinTable(
            name = "location_exemplaire",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "exemplaire_id")
    )
    private Set<Exemplaire> exemplaires = new HashSet<>();

// --- GETTERS & SETTERS ---

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

    public ClientMagasin getClientMagasin() {
        return clientMagasin;
    }

    public void setClientMagasin(ClientMagasin clientMagasin) {
        this.clientMagasin = clientMagasin;
    }

    public Set<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(Set<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }
}
