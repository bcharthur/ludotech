package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.service.ClientMagasinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-magasin")
public class ClientMagasinController {

    @Autowired
    private ClientMagasinService service;

    @GetMapping
    public List<ClientMagasin> all() {
        return service.getAllFiches();
    }

    @PostMapping("/add")
    public ClientMagasin add(@RequestBody ClientMagasin fiche,
                             @RequestParam(value = "exemplaireId", required = false) Integer exemplaireId) {
        return service.create(fiche, exemplaireId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientMagasin> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ClientMagasin fiche) {
        try {
            ClientMagasin updated = service.update(fiche);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Fiche client magasin supprimée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}