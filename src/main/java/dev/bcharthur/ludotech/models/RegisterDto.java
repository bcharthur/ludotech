package dev.bcharthur.ludotech.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterDto {

    @NotEmpty(message = "Le prénom est obligatoire")
    private String firstName;

    @NotEmpty(message = "Le nom est obligatoire")
    private String lastName;

    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    private String email;

    private String phone;

    // Nouveaux champs pour l'adresse
    private String addressStreet;
    private String addressCity;
    private String addressPostalCode;
    private String addressCountry;

    @Size(min = 8, message = "La longueur minimale du mot de passe est de 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule et un chiffre")
    private String password;

    private String confirmPassword;

    // Getters et setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddressStreet() {
        return addressStreet;
    }
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
    public String getAddressCity() {
        return addressCity;
    }
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
    public String getAddressPostalCode() {
        return addressPostalCode;
    }
    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }
    public String getAddressCountry() {
        return addressCountry;
    }
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
