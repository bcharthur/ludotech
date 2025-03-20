package dev.bcharthur.ludotech.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_genre")
    private Integer id;

    @NotBlank(message = "Le libellé du genre est obligatoire")
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
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
