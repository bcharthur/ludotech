package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.GenreService;
import dev.bcharthur.ludotech.service.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService; // Assurez-vous que ClientService possède une méthode findByEmail()

    @Autowired
    private GenreService genreService;

    @Autowired
    private JeuService jeuService;

    @GetMapping("/api/genres")
    @ResponseBody
    public List<Genre> getAllGenresApi() {
        return genreService.getAllGenres();
    }

    @GetMapping("/api/jeux")
    @ResponseBody
    public List<Jeu> getAllJeuxApi() {
        return jeuService.getAllJeux();
    }



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
            model.addAttribute("allGenres", genreService.getAllGenres());
            model.addAttribute("allJeux", jeuService.getAllJeux());

        }
        return "index";
    }
}
