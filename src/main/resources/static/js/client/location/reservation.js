$(document).ready(function() {
    // Stocker globalement les dates réservées pour le jeu actuellement sélectionné
    let globalReservedDates = [];

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
                // Stocke les dates réservées dans une variable globale pour validation ultérieure
                globalReservedDates = reservedDates;
                initDatepicker(reservedDates);
                // Renseigne le champ caché avec l'ID du jeu sélectionné
                $('#reservationModal').find('input[name="jeuId"]').val(jeuId);
                $('#reservationModal').modal('show');
            },
            error: function() {
                alert("Erreur lors de la récupération des dates réservées pour ce jeu.");
            }
        });
    });

    // Initialisation du datepicker avec désactivation des dates réservées
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

        // Vérification côté client si la date sélectionnée est déjà réservée
        if (globalReservedDates.indexOf(reservationData.dateDebut) !== -1) {
            alert("Cette date est déjà réservée, veuillez en choisir une autre.");
            return;
        }

        $.ajax({
            url: '/api/jeu/reserver', // Endpoint pour la réservation
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservationData),
            success: function(response) {
                // Déclenche la demande de mise à jour via WebSocket
                if (stompClient) {
                    stompClient.send('/app/availability/' + reservationData.jeuId, {}, {});
                }
                // Réinitialise le formulaire et le datepicker de la modal
                $('#reservationForm')[0].reset();
                $("#reservationDate").datepicker("destroy");
                $('#reservationModal').modal('hide');
                $('#successModalMessage').text(response.message || "Réservation effectuée !");
                $('#successModal').modal('show');
            },
            error: function(xhr) {
                let errorMsg = "Erreur lors de la réservation.";
                if (xhr.responseJSON && xhr.responseJSON.error) {
                    errorMsg = xhr.responseJSON.error;
                    if (xhr.responseJSON.details) {
                        errorMsg += " – " + xhr.responseJSON.details;
                    }
                }
                alert(errorMsg);
            }
        });
    });
});
