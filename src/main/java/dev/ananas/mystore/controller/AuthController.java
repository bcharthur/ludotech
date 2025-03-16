package dev.ananas.mystore.controller;

import dev.ananas.mystore.models.User;
import dev.ananas.mystore.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerDto")) {
            model.addAttribute("registerDto", new User());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") User registerUser,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        // Vérifier que le password et son confirmPassword correspondent
        if (!registerUser.getPassword().equals(registerUser.getConfirmPassword())) {
            result.addError(new FieldError("registerDto", "confirmPassword", "Les mots de passe ne correspondent pas"));
        }

        // Vérifier que l'email n'est pas déjà utilisé
        User existingUser = repo.findByEmail(registerUser.getEmail());
        if (existingUser != null) {
            result.addError(new FieldError("registerDto", "email", "Adresse mail déjà utilisée, connectez-vous"));
        }

        if (result.hasErrors()) {
            // Agréger les messages d'erreur
            StringBuilder errorMsg = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMsg.append(error.getDefaultMessage()).append("<br>"));
            redirectAttributes.addFlashAttribute("error", errorMsg.toString());
            return "redirect:/register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();
            // Encoder le password et définir les autres attributs
            registerUser.setPassword(bCryptEncoder.encode(registerUser.getPassword()));
            registerUser.setRole("client");
            registerUser.setCreatedAT(new Date());

            repo.save(registerUser);

            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/register";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'inscription : " + ex.getMessage());
            return "redirect:/register";
        }
    }

}
