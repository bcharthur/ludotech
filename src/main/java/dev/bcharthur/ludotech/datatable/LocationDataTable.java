package dev.bcharthur.ludotech.datatable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LocationDataTable {
    private Long id;
    private String clientName;
    private LocalDateTime dateDebut;
    private LocalDateTime dateRetour;
    private BigDecimal tarifJour;
    private List<String> jeux; // Liste des titres des jeux loués

    public LocationDataTable() {
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public List<String> getJeux() {
        return jeux;
    }

    public void setJeux(List<String> jeux) {
        this.jeux = jeux;
    }
}
