// ExemplaireController.java
package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import dev.bcharthur.ludotech.service.ExemplaireService;
import dev.bcharthur.ludotech.service.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/exemplaire")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private JeuService jeuService;

    // Récupérer un exemplaire par id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getExemplaireById(@PathVariable int id) {
        Exemplaire exemplaire = exemplaireService.getExemplaireById(id).orElse(null);
        if (exemplaire == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exemplaire);
    }

    // Liste complète pour le DataTable
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllExemplaires() {
        List<Exemplaire> list = exemplaireService.getAllExemplaires();
        return ResponseEntity.ok(list);
    }

    // Ajouter un exemplaire
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addExemplaire(@RequestBody Exemplaire exemplaire) {
        // Récupérer le jeu géré pour être sûr que l'entité est gérée
        Jeu jeu = jeuService.getJeuById(exemplaire.getJeu().getId()).orElse(null);
        if (jeu == null) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        exemplaire.setJeu(jeu);
        exemplaireService.saveExemplaire(exemplaire);
        return ResponseEntity.ok("Exemplaire ajouté avec succès");
    }

    // Modifier un exemplaire
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editExemplaire(@RequestBody Exemplaire exemplaire) {
        Exemplaire existing = exemplaireService.getExemplaireById(exemplaire.getId()).orElse(null);
        if (existing == null) {
            return ResponseEntity.badRequest().body("Exemplaire introuvable");
        }
        existing.setCodebarre(exemplaire.getCodebarre());
        existing.setLouable(exemplaire.getLouable());
        Jeu jeu = jeuService.getJeuById(exemplaire.getJeu().getId()).orElse(null);
        if (jeu == null) {
            return ResponseEntity.badRequest().body("Jeu introuvable");
        }
        existing.setJeu(jeu);
        exemplaireService.saveExemplaire(existing);
        return ResponseEntity.ok("Exemplaire modifié avec succès");
    }

    // Supprimer un exemplaire
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteExemplaire(@PathVariable int id) {
        Exemplaire exemplaire = exemplaireService.getExemplaireById(id).orElse(null);
        if (exemplaire == null) {
            return ResponseEntity.badRequest().body("Exemplaire introuvable");
        }
        exemplaireService.deleteExemplaire(id);
        return ResponseEntity.ok("Exemplaire supprimé avec succès");
    }

    @ModelAttribute("allJeux")
    public List<Jeu> populateJeux() {
        return jeuService.getAllJeux();
    }

    // Endpoint renvoyant le nombre d'exemplaires disponibles (louable == true) pour un jeu donné
    @GetMapping("/disponibilite/{jeuId}")
    public ResponseEntity<Integer> getAvailableCount(@PathVariable Integer jeuId) {
        int count = exemplaireRepository.countByJeu_IdAndLouableTrue(jeuId);
        return ResponseEntity.ok(count);
    }
}
