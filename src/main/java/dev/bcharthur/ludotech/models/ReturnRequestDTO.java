package dev.bcharthur.ludotech.models;

public class ReturnRequestDTO {
    private String barcode;
    private String etat; // "neuf", "correct", "inlouable"

    // Getters & Setters
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
}
