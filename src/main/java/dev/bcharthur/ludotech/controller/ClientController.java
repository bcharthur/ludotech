package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.repository.ClientRepository;
import dev.bcharthur.ludotech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getClientById(@PathVariable int id) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editClient(@RequestBody Client client) {
        Client existing = clientRepo.findById(client.getId()).orElse(null);
        if (existing == null) {
            return ResponseEntity.badRequest().body("Client introuvable");
        }
        existing.setFirstName(client.getFirstName());
        existing.setLastName(client.getLastName());
        existing.setEmail(client.getEmail());
        existing.setPhone(client.getPhone());
        existing.setAdresse(client.getAdresse());
        clientRepo.save(existing);
        return ResponseEntity.ok("Client modifié avec succès");
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        Client saved = clientRepo.save(client);
        return ResponseEntity.ok("Client ajouté avec succès");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteClient(@PathVariable int id) {
        if (!clientRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Client introuvable");
        }
        clientRepo.deleteById(id);
        return ResponseEntity.ok("Client supprimé avec succès");
    }
}
