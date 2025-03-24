// static/js/client/reservationPanier.js
$(document).ready(function() {
    // Initialiser le datepicker pour le formulaire de réservation du panier
    $("#panierReservationDate").datepicker({
        dateFormat: "yy-mm-dd"
    });

    // Lorsque le formulaire de réservation du panier est soumis
    $('#reservationPanierForm').on('submit', function(e) {
        e.preventDefault();
        let reservationData = {
            dateDebut: $(this).find('input[name="dateDebut"]').val(),
            duration: $(this).find('input[name="duration"]').val()
        };

        $.ajax({
            url: '/api/jeu/reserverPanier',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservationData),
            success: function(response) {
                // Déclenche la mise à jour en temps réel du compteur "Dispo :" pour chaque jeu réservé
                if (stompClient && response.location && response.location.exemplaires) {
                    response.location.exemplaires.forEach(function(exemplaire) {
                        // On suppose que chaque exemplaire possède un attribut "jeu" avec son "id"
                        stompClient.send('/app/availability/' + exemplaire.jeu.id, {}, {});
                    });
                }
                // Réinitialise le formulaire, détruit le datepicker et ferme la modal de réservation du panier
                $('#reservationPanierForm')[0].reset();
                $("#panierReservationDate").datepicker("destroy");
                $("#reservationPanierModal").modal("hide");
                // Affiche une modal de succès (par exemple, successModal déjà définie)
                $('#successModalMessage').text(response.message || "Réservation effectuée !");
                $('#successModal').modal("show");
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
