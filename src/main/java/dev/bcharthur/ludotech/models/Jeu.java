package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "jeu")
public class Jeu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_jeu")
    private Integer id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference; // Par exemple, un code de 13 chiffres (vous pouvez aussi utiliser un type Long)

    @Column(name = "age_min")
    private Integer ageMin;

    @Column(name = "description")
    private String description;

    @Column(name = "duree")
    private Integer duree; // Durée moyenne (en minutes ou en heures selon votre besoin)

    @Column(name = "tarif_jour", precision = 10, scale = 2)
    private BigDecimal tarifJour;

    @ManyToMany
    @JoinTable(
            name = "jeu_genre",
            joinColumns = @JoinColumn(name = "jeu_id", referencedColumnName = "no_jeu"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "no_genre")
    )
    private Set<Genre> genres;

    // Getters et setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public Integer getAgeMin() {
        return ageMin;
    }
    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getDuree() {
        return duree;
    }
    public void setDuree(Integer duree) {
        this.duree = duree;
    }
    public BigDecimal getTarifJour() {
        return tarifJour;
    }
    public void setTarifJour(BigDecimal tarifJour) {
        this.tarifJour = tarifJour;
    }
    public Set<Genre> getGenres() {
        return genres;
    }
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
