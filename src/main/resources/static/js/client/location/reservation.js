$(document).ready(function() {
    // Lorsque l'utilisateur clique sur le bouton "Louer"
    $('.btn-louer').on('click', function(e) {
        e.preventDefault();
        e.stopPropagation(); // Empêche d'ouvrir la modal de détail
        let jeuId = $(this).data('jeu-id');

        // Appel à l'endpoint pour récupérer les dates réservées pour le jeu
        $.ajax({
            url: `/api/jeu/${jeuId}/reservations/dates`,
            type: 'GET',
            success: function(reservedDates) {
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

    // Intercepter la soumission du formulaire de réservation pour utiliser AJAX
    $('#reservationForm').on('submit', function(e) {
        e.preventDefault(); // Empêche la soumission classique du formulaire

        let reservationData = {
            jeuId: $(this).find('input[name="jeuId"]').val(),
            dateDebut: $(this).find('input[name="dateDebut"]').val(),
            duration: $(this).find('input[name="duration"]').val()
        };

        $.ajax({
            url: '/api/jeu/reserver', // Endpoint côté serveur pour la réservation
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservationData),
            success: function(response) {
                // Masquer la modal de réservation
                $('#reservationModal').modal('hide');
                // Afficher la modal de succès
                $('#successReservModal').modal('show');
            },
            error: function(xhr, status, error) {
                alert("Erreur lors de la réservation : " + error);
            }
        });
    });
});
