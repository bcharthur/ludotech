package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.models.Panier;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.JeuService;
import dev.bcharthur.ludotech.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JeuService jeuService;

    // Récupère le panier du client authentifié
    @GetMapping
    public ResponseEntity<Panier> getPanier() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        String email = ((User) auth.getPrincipal()).getUsername();
        Client client = clientService.findByEmail(email);
        Panier panier = panierService.getPanierByClient(client);
        return ResponseEntity.ok(panier);
    }

    // Ajoute un jeu au panier du client authentifié
    @PostMapping("/add/{jeuId}")
    public ResponseEntity<?> addJeuToPanier(@PathVariable Integer jeuId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        String email = ((User) auth.getPrincipal()).getUsername();
        Client client = clientService.findByEmail(email);
        Jeu jeu = jeuService.getJeuById(jeuId).orElse(null);
        if (jeu == null) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        try {
            Panier updatedPanier = panierService.addJeuToPanier(client, jeu);
            return ResponseEntity.ok(updatedPanier);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Retire un jeu du panier du client authentifié
    @PostMapping("/remove/{jeuId}")
    public ResponseEntity<?> removeJeuFromPanier(@PathVariable Integer jeuId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        String email = ((User) auth.getPrincipal()).getUsername();
        Client client = clientService.findByEmail(email);
        Jeu jeu = jeuService.getJeuById(jeuId).orElse(null);
        if (jeu == null) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        Panier updatedPanier = panierService.removeJeuFromPanier(client, jeu);
        return ResponseEntity.ok(updatedPanier);
    }

    // Vide le panier du client authentifié
    @PostMapping("/clear")
    public ResponseEntity<?> clearPanier() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        String email = ((User) auth.getPrincipal()).getUsername();
        Client client = clientService.findByEmail(email);
        panierService.clearPanier(client);
        return ResponseEntity.ok("Panier vidé");
    }
}
