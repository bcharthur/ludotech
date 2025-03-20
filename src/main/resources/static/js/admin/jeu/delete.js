$(document).ready(function() {
    let currentJeuIdToDelete = null;
    console.log("delete.js chargé");

    // Lorsqu'on clique sur un bouton de suppression dans le tableau, on récupère l'ID et on affiche la modal
    $('#jeuxTable').on('click', '.btnDelete', function() {
        currentJeuIdToDelete = $(this).data('id');
        console.log("Bouton de suppression cliqué pour le jeu id :", currentJeuIdToDelete);
        $('#deleteJeuModal').modal('show');
    });

    // Délégation : quand on clique sur le bouton "Supprimer" de la modal
    $(document).on('click', '#confirmDeleteButton', function() {
        console.log("Bouton 'Supprimer' de la modal cliqué");
        if (!currentJeuIdToDelete) {
            console.error("Aucun jeu sélectionné pour la suppression");
            return;
        }
        console.log("Envoi de la requête DELETE pour le jeu id :", currentJeuIdToDelete);
        $.ajax({
            url: '/admin/jeu/' + currentJeuIdToDelete,
            type: 'DELETE',
            success: function(response) {
                console.log("Réponse de suppression :", response);
                $('#deleteJeuModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                console.log("Rafraîchissement de la DataTable...");
                $('#jeuxTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                console.error("Erreur lors de la suppression du jeu id", currentJeuIdToDelete, ":", xhr.responseText);
                alert("Erreur lors de la suppression du jeu : " + xhr.responseText);
            }
        });
    });
});
