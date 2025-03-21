package dev.bcharthur.ludotech.models;

public class ReservationRequestDTO {
    private Integer jeuId;
    private String dateDebut; // Format attendu : "yyyy-MM-dd"
    private int duration;

    // Getters et Setters

    public Integer getJeuId() {
        return jeuId;
    }

    public void setJeuId(Integer jeuId) {
        this.jeuId = jeuId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
