// Fonction qui met à jour l'affichage du "Dispo : X" pour un jeu donné
function updateAvailability(jeuId, callback) {
    $.ajax({
        url: `/admin/exemplaire/disponibilite/${jeuId}`,
        type: 'GET',
        success: function(newCount) {
            let availableCountElement = $(`.available-count[data-jeu-id="${jeuId}"]`);
            availableCountElement.text("Dispo : " + newCount);
            // Désactive ou active le bouton "Louer" selon le stock
            if (newCount <= 0) {
                $(`.btn-louer[data-jeu-id="${jeuId}"]`).prop("disabled", true);
            } else {
                $(`.btn-louer[data-jeu-id="${jeuId}"]`).prop("disabled", false);
            }
            if (callback) {
                callback();
            }
        },
        error: function(xhr, status, error) {
            console.error("Erreur lors de la mise à jour de la disponibilité : ", error);
            if (callback) {
                callback();
            }
        }
    });
}

// Code de réservation dans reservation.js
$(document).ready(function() {
    // Lors du clic sur le bouton "Louer"
    $('.btn-louer').on('click', function(e) {
        e.preventDefault();
        e.stopPropagation(); // Empêche d'ouvrir la modal de détail
        let jeuId = $(this).data('jeu-id');

        // Récupération des dates réservées pour initialiser le datepicker
        $.ajax({
            url: `/api/jeu/${jeuId}/reservations/dates`,
            type: 'GET',
            success: function(reservedDates) {
                initDatepicker(reservedDates);
                // On renseigne le champ caché avec l'ID du jeu sélectionné
                $('#reservationModal').find('input[name="jeuId"]').val(jeuId);
                $('#reservationModal').modal('show');
            },
            error: function() {
                alert("Erreur lors de la récupération des dates réservées pour ce jeu.");
            }
        });
    });

    function initDatepicker(reservedDates) {
        $("#reservationDate").datepicker("destroy");
        $("#reservationDate").datepicker({
            dateFormat: "yy-mm-dd",
            beforeShowDay: function(date) {
                let y = date.getFullYear();
                let m = ("0" + (date.getMonth() + 1)).slice(-2);
                let d = ("0" + date.getDate()).slice(-2);
                let formattedDate = `${y}-${m}-${d}`;
                if (reservedDates.indexOf(formattedDate) !== -1) {
                    return [false, "reserved-date", "Date déjà réservée"];
                }
                return [true, "", ""];
            }
        });
    }

    // Soumission du formulaire de réservation via AJAX
    $('#reservationForm').on('submit', function(e) {
        e.preventDefault(); // Empêche la soumission classique du formulaire

        let reservationData = {
            jeuId: $(this).find('input[name="jeuId"]').val(),
            dateDebut: $(this).find('input[name="dateDebut"]').val(),
            duration: $(this).find('input[name="duration"]').val()
        };

        $.ajax({
            url: '/api/jeu/reserver', // Endpoint pour la réservation
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservationData),
            success: function(response) {
                let jeuId = reservationData.jeuId;
                // Petite pause pour que la base soit à jour, puis on met à jour l'affichage de "Dispo"
                setTimeout(function() {
                    updateAvailability(jeuId, function() {
                        $('#reservationModal').modal('hide');
                        $('#successModal').modal('show');
                    });
                }, 100); // délai de 100 ms (ajustable si besoin)
            },
            error: function(xhr, status, error) {
                alert("Erreur lors de la réservation : " + error);
            }
        });
    });
});
