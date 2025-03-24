package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.models.Location;
import dev.bcharthur.ludotech.models.ReturnRequestDTO;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private ClientService clientService;

    /**
     * Réserver un exemplaire pour un client en scannant le code barre.
     * Le payload doit contenir "barcode" et optionnellement "clientEmail" si différent du client connecté.
     */
    @PostMapping("/reservation/byBarcode")
    public ResponseEntity<?> reserveByBarcode(@RequestBody Map<String, String> payload, Principal principal) {
        String barcode = payload.get("barcode");
        String clientEmail = payload.get("clientEmail");
        if (barcode == null || barcode.isEmpty()) {
            return ResponseEntity.badRequest().body("Barcode manquant.");
        }
        try {
            Client client;
            if (clientEmail != null && !clientEmail.isEmpty()) {
                client = clientService.findByEmail(clientEmail);
                if (client == null) {
                    return ResponseEntity.badRequest().body("Client introuvable.");
                }
            } else {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String email = ((User) auth.getPrincipal()).getUsername();
                client = clientService.findByEmail(email);
            }
            Location location = employeService.reserveExemplaireByBarcode(barcode, client);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la réservation : " + e.getMessage());
        }
    }

    /**
     * Traitement du retour d'un exemplaire.
     * Le payload doit contenir "barcode" et "etat" (ex. "neuf", "correct", "inlouable").
     */
    @PostMapping("/return")
    public ResponseEntity<?> processReturn(@RequestBody ReturnRequestDTO dto) {
        try {
            Exemplaire updated = employeService.processReturn(dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du traitement du retour : " + e.getMessage());
        }
    }

    /**
     * Retourne l'inventaire complet des exemplaires.
     */
    @GetMapping("/inventory")
    public ResponseEntity<List<Exemplaire>> getInventory() {
        List<Exemplaire> inventory = employeService.getInventory();
        return ResponseEntity.ok(inventory);
    }
}
