package dev.ananas.mystore.models;

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

    private String address;

    // Le mot de passe doit avoir au moins 8 caractères, une minuscule, une majuscule et un chiffre
    @Size(min = 8, message = "La longueur minimale du mot de passe est de 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule et un chiffre")
    private String password;

    private String confirmPassword;

    public @NotEmpty(message = "Le prénom est obligatoire") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "Le prénom est obligatoire") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "Le nom est obligatoire") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "Le nom est obligatoire") String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "L'email est obligatoire") @Email(message = "L'email n'est pas valide") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "L'email est obligatoire") @Email(message = "L'email n'est pas valide") String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @Size(min = 8, message = "La longueur minimale du mot de passe est de 8 caractères") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule et un chiffre") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "La longueur minimale du mot de passe est de 8 caractères") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule et un chiffre") String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
