// Exemplaire.java
package dev.bcharthur.ludotech.models;

import jakarta.persistence.*;

@Entity
@Table(name = "exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_exemplaire")
    private Integer id;

    @Column(name = "codebarre", nullable = false, unique = true)
    private String codebarre;

    @Column(name = "louable")
    private Boolean louable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "no_jeu", nullable = false)
    private Jeu jeu;

    // Getters et setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodebarre() {
        return codebarre;
    }

    public void setCodebarre(String codebarre) {
        this.codebarre = codebarre;
    }

    public Boolean getLouable() {
        return louable;
    }

    public void setLouable(Boolean louable) {
        this.louable = louable;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}
