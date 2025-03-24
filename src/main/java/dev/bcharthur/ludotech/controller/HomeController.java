package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.models.Panier;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.GenreService;
import dev.bcharthur.ludotech.service.JeuService;
import dev.bcharthur.ludotech.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService; // Assurez-vous que ClientService possède une méthode findByEmail()

    @Autowired
    private GenreService genreService;

    @Autowired
    private JeuService jeuService;

    @Autowired
    private PanierService panierService;

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

    @GetMapping("/api/jeu/{id}")
    @ResponseBody
    public ResponseEntity<?> getJeuById(@PathVariable int id) {
        Jeu jeu = jeuService.getJeuById(id).orElse(null);
        if (jeu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jeu);
    }

    @GetMapping({"", "/"})
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Récupère tous les jeux et genres pour la page d'accueil
        List<Jeu> allJeux = jeuService.getAllJeux();
        model.addAttribute("allJeux", allJeux);
        model.addAttribute("allGenres", genreService.getAllGenres());

        // Prépare une Map associant l'ID du jeu à son nombre d'exemplaires disponibles
        Map<Integer, Integer> exemplairesMap = new HashMap<>();
        for (Jeu jeu : allJeux) {
            // La méthode countAvailableExemplaires doit être implémentée dans JeuService
            int count = jeuService.countAvailableExemplaires(jeu.getId());
            exemplairesMap.put(jeu.getId(), count);
        }
        model.addAttribute("exemplairesMap", exemplairesMap);

        // Si l'utilisateur est authentifié, récupère son prénom
        if (authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof User) {

            User userDetails = (User) authentication.getPrincipal();
            String email = userDetails.getUsername();
            Client client = clientService.findByEmail(email);
            model.addAttribute("prenom", client != null ? client.getFirstName() : "User");
        }

        return "index";
    }

    @GetMapping("/panier")
    public String panier(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
            String email = ((User) auth.getPrincipal()).getUsername();
            Client client = clientService.findByEmail(email);
            Panier panier = panierService.getPanierByClient(client);
            model.addAttribute("panier", panier);
        }
        return "client/panier";
    }
}
