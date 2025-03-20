package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService; // Assurez-vous que ClientService possède une méthode findByEmail()

    @GetMapping({"", "/"})
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof User) {

            User userDetails = (User) authentication.getPrincipal();
            String email = userDetails.getUsername();
            // Récupérer le client par email
            Client client = clientService.findByEmail(email);
            if (client != null) {
                model.addAttribute("prenom", client.getFirstName());
            } else {
                model.addAttribute("prenom", "User");
            }
        }
        return "index";
    }
}
