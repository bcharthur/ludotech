package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.RegisterDto;
import dev.bcharthur.ludotech.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class AuthRestController {

    @Autowired
    private ClientRepository repo;

    @GetMapping("/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        Client user = repo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cet email n'est pas enregistré"));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-password")
    @ResponseBody
    public ResponseEntity<?> checkPassword(@RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        Client user = repo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cet email n'est pas enregistré"));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Mot de passe erroné"));
        }
    }



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
        Client client = repo.findByEmail(registerDto.getEmail());
        if (client != null) {
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
            Client newUser = new Client();
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
