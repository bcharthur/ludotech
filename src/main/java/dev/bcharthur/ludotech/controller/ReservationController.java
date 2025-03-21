package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Location;
import dev.bcharthur.ludotech.models.ReservationRequestDTO;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/jeu")
public class ReservationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientService clientService;

    /**
     * Retourne la liste des dates réservées (format yyyy-MM-dd) pour le jeu donné.
     * URL: /api/jeu/{jeuId}/reservations/dates
     */
    @GetMapping("/{jeuId}/reservations/dates")
    public ResponseEntity<List<String>> getReservedDatesForJeu(@PathVariable Integer jeuId) {
        List<String> reservedDates = locationService.getReservedDatesForJeu(jeuId);
        return ResponseEntity.ok(reservedDates);
    }

    /**
     * Retourne la liste des exemplaires disponibles pour le jeu donné.
     * URL: /api/jeu/{jeuId}/exemplaires/disponibles
     */
    @GetMapping("/{jeuId}/exemplaires/disponibles")
    public ResponseEntity<?> getAvailableExemplairesForJeu(@PathVariable Integer jeuId) {
        return ResponseEntity.ok(locationService.getAvailableExemplairesForJeu(jeuId));
    }

    @PostMapping("/reserver")
    public ResponseEntity<?> reserverJeu(@RequestBody ReservationRequestDTO dto, Principal principal) {
        // Récupérez le client connecté via 'principal' ou via un service dédié
        Client client = clientService.findByEmail(principal.getName());
        try {
            Location location = locationService.reserverJeu(dto, client);
            return new ResponseEntity<>(location, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}
