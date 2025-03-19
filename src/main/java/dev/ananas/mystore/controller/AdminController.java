package dev.ananas.mystore.controller;

import dev.ananas.mystore.models.AppUser;
import dev.ananas.mystore.repository.AppUserRepository;
import dev.ananas.mystore.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppUserRepository repo;

    @Autowired
    private AppUserService userService;

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        AppUser user = repo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/edit")
    @ResponseBody
    public ResponseEntity<?> editUser(@RequestBody AppUser user) {
        AppUser existing = repo.findById(user.getId()).orElse(null);
        if (existing == null) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setAddress(user.getAddress());
        // Autres modifications (role, password, etc.) si nécessaire

        repo.save(existing);
        return ResponseEntity.ok("Utilisateur modifié avec succès");
    }

    @DeleteMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
}
