package dev.ananas.mystore.controller;

import dev.ananas.mystore.models.AppUser;
import dev.ananas.mystore.models.RegisterDto;
import dev.ananas.mystore.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/")
public class AuthRestController {

    @Autowired
    private AppUserRepository repo;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result) {

        // Vérification de la correspondance des mots de passe
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(new FieldError("registerDto", "confirmPassword", "Les mots de passe et leur confirmation ne correspondent pas"));
        }

        // Vérification si l'email existe déjà
        AppUser appUser = repo.findByEmail(registerDto.getEmail());
        if (appUser != null) {
            result.addError(new FieldError("registerDto", "email", "Cet email est déjà utilisé"));
        }

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Création du compte
            BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setAddress(registerDto.getAddress());
            newUser.setRole("client");
            newUser.setCreatedAT(new Date());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

            repo.save(newUser);

            // Construction manuelle des autorités et authentification de l'utilisateur
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + newUser.getRole().toUpperCase()));
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(newUser, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("redirectUrl", "/");
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", "Une erreur est survenue : " + ex.getMessage()));
        }
    }
}
