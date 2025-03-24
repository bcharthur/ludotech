package dev.bcharthur.ludotech.service;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Exemplaire;
import dev.bcharthur.ludotech.models.Location;
import dev.bcharthur.ludotech.models.ReturnRequestDTO;
import dev.bcharthur.ludotech.repository.ExemplaireRepository;
import dev.bcharthur.ludotech.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Réserver un exemplaire en se basant sur son code barre pour un client donné.
     * Pour simplifier, une réservation par défaut de 7 jours est réalisée.
     */
    public Location reserveExemplaireByBarcode(String barcode, Client client) {
        Exemplaire exemplaire = exemplaireRepository.findByCodebarreAndLouableTrue(barcode);
        if (exemplaire == null) {
            throw new IllegalStateException("Aucun exemplaire disponible avec ce code barre.");
        }
        // Créer une Location pour cette réservation
        Location location = new Location();
        location.setClient(client);
        LocalDate start = LocalDate.now();
        location.setDateDebut(start.atStartOfDay());
        location.setDateRetour(start.plusDays(7).atStartOfDay());
        location.setTarifJour(exemplaire.getJeu().getTarifJour());
        // Marquer l'exemplaire comme réservé (non louable)
        exemplaire.setLouable(false);
        exemplaireRepository.save(exemplaire);
        // Associer l'exemplaire à la Location
        Set<Exemplaire> set = new HashSet<>();
        set.add(exemplaire);
        location.setExemplaires(set);
        return locationRepository.save(location);
    }

    /**
     * Traite le retour d'un exemplaire.
     * Selon l'état retourné ("neuf", "correct", "inlouable"), l'exemplaire est rendu disponible ou non louable.
     */
    public Exemplaire processReturn(ReturnRequestDTO dto) {
        Exemplaire exemplaire = exemplaireRepository.findByCodebarre(dto.getBarcode());
        if (exemplaire == null) {
            throw new IllegalStateException("Exemplaire non trouvé.");
        }
        exemplaire.setEtat(dto.getEtat()); // Mise à jour de l'état
        // Si l'exemplaire est en bon état, il devient louable, sinon il reste indisponible
        if ("neuf".equalsIgnoreCase(dto.getEtat()) || "correct".equalsIgnoreCase(dto.getEtat())) {
            exemplaire.setLouable(true);
        } else {
            exemplaire.setLouable(false);
        }
        return exemplaireRepository.save(exemplaire);
    }

    /**
     * Retourne la liste complète des exemplaires (pour l'inventaire).
     */
    public List<Exemplaire> getInventory() {
        return exemplaireRepository.findAll();
    }
}
