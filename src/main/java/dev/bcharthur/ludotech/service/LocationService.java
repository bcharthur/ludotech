// LocationService.java
package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.datatable.LocationDataTable;
import dev.bcharthur.ludotech.models.*;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import dev.bcharthur.ludotech.repository.LocationRepository;
import dev.bcharthur.ludotech.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PanierRepository panierRepository;

    public Location createLocation(Location location) {
        if (location.getExemplaires().size() > 5) {
            throw new IllegalArgumentException("Un client ne peut louer plus de 5 jeux simultanément.");
        }
        return locationRepository.save(location);
    }

    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location non trouvée"));
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public List<LocationDataTable> getLocationsDataTable() {
        List<Location> locations = locationRepository.findAll();
        List<LocationDataTable> dataTable = new ArrayList<>();
        for (Location loc : locations) {
            LocationDataTable dto = new LocationDataTable();
            dto.setId(loc.getId());
            dto.setClientName(loc.getClient().getFirstName() + " " + loc.getClient().getLastName());
            dto.setDateDebut(loc.getDateDebut());
            dto.setDateRetour(loc.getDateRetour());
            dto.setTarifJour(loc.getTarifJour());
            dto.setJeux(loc.getExemplaires()
                    .stream()
                    .map(ex -> ex.getJeu().getTitre())
                    .toList());
            dataTable.add(dto);
        }
        return dataTable;
    }

    /**
     * Retourne la liste des dates réservées (format yyyy-MM-dd) pour un jeu donné.
     */
    public List<String> getReservedDatesForJeu(Integer jeuId) {
        List<Location> locations = locationRepository.findAll();
        Set<String> reservedDates = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Location loc : locations) {
            boolean containsJeu = loc.getExemplaires().stream()
                    .anyMatch(ex -> ex.getJeu().getId().equals(jeuId));
            if (containsJeu && loc.getDateDebut() != null && loc.getDateRetour() != null) {
                LocalDate start = loc.getDateDebut().toLocalDate();
                LocalDate end = loc.getDateRetour().toLocalDate();
                while (!start.isAfter(end)) {
                    reservedDates.add(start.format(formatter));
                    start = start.plusDays(1);
                }
            }
        }
        List<String> result = new ArrayList<>(reservedDates);
        Collections.sort(result);
        return result;
    }

    /**
     * Retourne la liste des exemplaires disponibles (où louable == true) pour le jeu donné.
     */
    public List<Exemplaire> getAvailableExemplairesForJeu(Integer jeuId) {
        return exemplaireRepository.findByJeu_IdAndLouableTrue(jeuId);
    }

    public Location reserverJeu(ReservationRequestDTO dto, Client client) {
        LocalDate startDate = LocalDate.parse(dto.getDateDebut());
        LocalDate endDate = startDate.plusDays(dto.getDuration() - 1);

        // Récupérer les exemplaires louables pour ce jeu
        List<Exemplaire> exemplaires = exemplaireRepository.findByJeu_IdAndLouableTrue(dto.getJeuId());

        // Pour chaque exemplaire, vérifier la disponibilité sur la période
        for (Exemplaire exemplaire : exemplaires) {
            if (isExemplaireDisponible(exemplaire, startDate, endDate)) {
                // Met à jour le statut de l'exemplaire pour qu'il ne soit plus louable
                exemplaire.setLouable(false);
                exemplaireRepository.save(exemplaire);

                // Créer et sauvegarder la réservation
                Location location = new Location();
                location.setDateDebut(startDate.atStartOfDay());
                location.setDateRetour(endDate.atTime(23, 59, 59));
                location.setTarifJour(exemplaire.getJeu().getTarifJour());
                location.setClient(client);
                location.getExemplaires().add(exemplaire);
                return locationRepository.save(location);
            }
        }
        throw new IllegalStateException("Aucun exemplaire n'est disponible pour la période demandée.");
    }

    private boolean isExemplaireDisponible(Exemplaire exemplaire, LocalDate startDate, LocalDate endDate) {
        // Requête personnalisée pour vérifier les chevauchements
        List<Location> reservations = locationRepository.findByExemplaireAndDateRange(
                exemplaire.getId(),
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
        return reservations.isEmpty();
    }

    /**
     * Réservation pour le panier du client.
     * Cette méthode sélectionne pour chaque jeu du panier un exemplaire disponible,
     * le marque comme non louable (louable = false) et crée une Location.
     */
    public Location reserverPanier(ReservationRequestDTO dto, Client client) {
        // Récupérer le panier du client
        Panier panier = panierRepository.findByClient(client);
        if (panier == null || panier.getJeux().isEmpty()) {
            throw new IllegalStateException("Le panier est vide.");
        }

        // Créer une nouvelle Location
        Location location = new Location();
        location.setClient(client);

        // Convertir la date de début du DTO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dto.getDateDebut(), formatter);
        location.setDateDebut(startDate.atStartOfDay());
        location.setDateRetour(startDate.plusDays(dto.getDuration()).atStartOfDay());

        // Calculer le tarif global par jour : on somme le tarif de chaque jeu
        BigDecimal totalTarifJour = panier.getJeux().stream()
                .map(Jeu::getTarifJour)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        location.setTarifJour(totalTarifJour);

        // Pour chaque jeu dans le panier, sélectionner un exemplaire disponible et le marquer comme non louable
        Set<Exemplaire> exemplaires = new HashSet<>();
        for (Jeu jeu : panier.getJeux()) {
            List<Exemplaire> disponibles = exemplaireRepository.findByJeu_IdAndLouableTrue(jeu.getId());
            if (disponibles.isEmpty()) {
                throw new IllegalStateException("Aucun exemplaire disponible pour le jeu : " + jeu.getTitre());
            }
            // Sélectionner le premier exemplaire disponible
            Exemplaire exemplaire = disponibles.get(0);
            // Marquer cet exemplaire comme réservé (non louable)
            exemplaire.setLouable(false);
            // Sauvegarder la modification de l'exemplaire
            exemplaireRepository.save(exemplaire);
            exemplaires.add(exemplaire);
        }
        location.setExemplaires(exemplaires);

        // Sauvegarder la location
        Location savedLocation = locationRepository.save(location);

        // Vider le panier
        panier.getJeux().clear();
        panierRepository.save(panier);

        return savedLocation;
    }

}
