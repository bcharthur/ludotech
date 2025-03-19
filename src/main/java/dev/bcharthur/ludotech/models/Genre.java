package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_genre")
    private Integer id;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "genres")
    private Set<Jeu> jeux;

    // Getters et setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public Set<Jeu> getJeux() {
        return jeux;
    }
    public void setJeux(Set<Jeu> jeux) {
        this.jeux = jeux;
    }
}
