package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "panier")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association OneToOne avec le client (chaque client a un panier unique)
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    // Relation ManyToMany avec les jeux
    @ManyToMany
    @JoinTable(
            name = "panier_jeu",
            joinColumns = @JoinColumn(name = "panier_id"),
            inverseJoinColumns = @JoinColumn(name = "jeu_id")
    )
    private Set<Jeu> jeux = new HashSet<>();

    public Panier() {}

    public Panier(Client client) {
        this.client = client;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Jeu> getJeux() {
        return jeux;
    }

    public void setJeux(Set<Jeu> jeux) {
        this.jeux = jeux;
    }
}
