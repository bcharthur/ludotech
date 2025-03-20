package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.repository.JeuRepository;
import dev.bcharthur.ludotech.service.GenreService;
import dev.bcharthur.ludotech.service.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/jeu")
public class JeuController {

    @Autowired
    private JeuRepository jeuRepo;

    @Autowired
    private JeuService jeuService;

    @Autowired
    private GenreService genreService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getJeuById(@PathVariable int id) {
        Jeu jeu = jeuRepo.findById(id).orElse(null);
        if (jeu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jeu);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editJeu(@RequestBody Jeu jeu) {
        Jeu existing = jeuRepo.findById(jeu.getId()).orElse(null);
        if (existing == null) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        existing.setTitre(jeu.getTitre());
        existing.setReference(jeu.getReference());
        existing.setAgeMin(jeu.getAgeMin());
        existing.setDescription(jeu.getDescription());
        existing.setDuree(jeu.getDuree());
        existing.setTarifJour(jeu.getTarifJour());
        // Recharge les genres à partir de la base pour obtenir des entités gérées
        Set<Genre> managedGenres = jeu.getGenres().stream()
                .map(g -> genreService.getGenreById(g.getId()))
                .collect(Collectors.toSet());
        existing.setGenres(managedGenres);

        jeuRepo.save(existing);
        return ResponseEntity.ok("Jeu modifié avec succès");
    }


    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addJeu(@RequestBody Jeu jeu) {
        // Récupère les genres gérés à partir des ids reçus
        Set<Genre> managedGenres = jeu.getGenres().stream()
                .map(genre -> genreService.getGenreById(genre.getId()))
                .collect(Collectors.toSet());
        jeu.setGenres(managedGenres);
        Jeu saved = jeuRepo.save(jeu);
        return ResponseEntity.ok("Jeu ajouté avec succès");
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteJeu(@PathVariable int id) {
        if (!jeuRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        jeuRepo.deleteById(id);
        return ResponseEntity.ok("Jeu supprimé avec succès");
    }

    @ModelAttribute("allGenres")
    public List<Genre> populateGenres() {
        return genreService.getAllGenres();
    }

}
