// LoginDto.java
package dev.ananas.mystore.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, message = "La longueur minimale du mot de passe est de 6 caractères")
    private String password;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
