package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "jeu")
public class Jeu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_jeu")
    private Integer id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(name = "titre", nullable = false)
    private String titre;

    @NotBlank(message = "La référence est obligatoire")
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @NotNull(message = "L'âge minimum est obligatoire")
    @Column(name = "age_min")
    private Integer ageMin;

    @Column(name = "description")
    private String description;

    @NotNull(message = "La durée est obligatoire")
    @Column(name = "duree")
    private Integer duree;

    @NotNull(message = "Le tarif par jour est obligatoire")
    @Column(name = "tarif_jour", precision = 10, scale = 2)
    private BigDecimal tarifJour;

    @Column(name = "image")
    private String image;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "jeu_genre",
            joinColumns = @JoinColumn(
                    name = "jeu_id",
                    referencedColumnName = "no_jeu",
                    foreignKey = @ForeignKey(
                            name = "fk_jeu_genre_jeu",
                            foreignKeyDefinition = "FOREIGN KEY (jeu_id) REFERENCES jeu(no_jeu) ON DELETE CASCADE"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "genre_id",
                    referencedColumnName = "no_genre",
                    foreignKey = @ForeignKey(
                            name = "fk_jeu_genre_genre",
                            foreignKeyDefinition = "FOREIGN KEY (genre_id) REFERENCES genre(no_genre) ON DELETE CASCADE"
                    )
            )
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
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}