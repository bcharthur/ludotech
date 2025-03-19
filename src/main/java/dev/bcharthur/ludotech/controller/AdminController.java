package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.repository.ClientRepository;
import dev.bcharthur.ludotech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/{id}")
    @ResponseBody
    public ResponseEntity<?> getClientById(@PathVariable int id) {
        Client client = repo.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping("/client/edit")
    @ResponseBody
    public ResponseEntity<?> editClient(@RequestBody Client client) {
        Client existing = repo.findById(client.getId()).orElse(null);
        if (existing == null) {
            return ResponseEntity.badRequest().body("Client introuvable");
        }
        existing.setFirstName(client.getFirstName());
        existing.setLastName(client.getLastName());
        existing.setEmail(client.getEmail());
        existing.setPhone(client.getPhone());
        existing.setAdresse(client.getAdresse());
        // Autres modifications (role, password, etc.) si nécessaire

        repo.save(existing);
        return ResponseEntity.ok("Utilisateur modifié avec succès");
    }

    @DeleteMapping("/client/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
}
