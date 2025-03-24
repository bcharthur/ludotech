package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.*;
import dev.bcharthur.ludotech.repository.ClientMagasinRepository;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import dev.bcharthur.ludotech.repository.LocationRepository;
import dev.bcharthur.ludotech.service.ClientMagasinService;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jeu")
public class ReservationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMagasinRepository clientMagasinRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Retourne la liste des dates réservées (format yyyy-MM-dd) pour le jeu donné.
     */
    @GetMapping("/{jeuId}/reservations/dates")
    public ResponseEntity<List<String>> getReservedDatesForJeu(@PathVariable Integer jeuId) {
        List<String> reservedDates = locationService.getReservedDatesForJeu(jeuId);
        return ResponseEntity.ok(reservedDates);
    }

    /**
     * Retourne le nombre d'exemplaires disponibles pour le jeu donné.
     */
    @GetMapping("/{jeuId}/exemplaires/disponibles")
    public ResponseEntity<Integer> getAvailableExemplairesForJeu(@PathVariable Integer jeuId) {
        int count = locationService.getAvailableExemplairesForJeu(jeuId).size();
        return ResponseEntity.ok(count);
    }

    /**
     * Réservation d'un jeu pour le client connecté.
     */
    @PostMapping("/reserver")
    public ResponseEntity<Map<String, Object>> reserverJeu(@RequestBody ReservationRequestDTO dto, Principal principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Client client = clientService.findByEmail(principal.getName());
            Location location = locationService.reserverJeu(dto, client);

            response.put("message", "Réservation effectuée avec succès !");
            response.put("location", location);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalStateException e) {
            response.put("error", "Aucun exemplaire disponible pour la période demandée.");
            response.put("details", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);

        } catch (Exception e) {
            response.put("error", "Une erreur est survenue lors de la réservation.");
            response.put("details", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Réservation d'un lot de jeux (panier) pour le client connecté.
     */
    @PostMapping("/reserverPanier")
    public ResponseEntity<Map<String, Object>> reserverPanier(@RequestBody ReservationRequestDTO dto, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client client = clientService.findByEmail(principal.getName());
            Location location = locationService.reserverPanier(dto, client);
            response.put("message", "Réservation effectuée avec succès !");
            response.put("location", location);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            response.put("error", "Aucun exemplaire disponible pour la période demandée.");
            response.put("details", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("error", "Une erreur est survenue lors de la réservation.");
            response.put("details", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/louer-exemplaire")
    public ResponseEntity<?> louerExemplairePourClientMagasin(@RequestBody Map<String, Integer> payload) {
        Integer clientId = payload.get("clientMagasinId");
        Integer exemplaireId = payload.get("exemplaireId");

        if (clientId == null || exemplaireId == null) {
            return ResponseEntity.badRequest().body("Paramètres manquants.");
        }

        try {
            ClientMagasin client = clientMagasinRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client introuvable"));
            Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId)
                    .orElseThrow(() -> new RuntimeException("Exemplaire introuvable"));

            if (!exemplaire.getLouable()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Exemplaire déjà loué.");
            }

            Location location = new Location();
            location.setClientMagasin(client);
            location.setDateDebut(LocalDateTime.now());
            location.setDateRetour(LocalDateTime.now().plusDays(7));
            location.setTarifJour(exemplaire.getJeu().getTarifJour());
            location.getExemplaires().add(exemplaire);

            exemplaire.setLouable(false);
            exemplaireRepository.save(exemplaire);
            locationRepository.save(location);

            return ResponseEntity.ok("Exemplaire loué avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }


}
