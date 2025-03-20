$(document).ready(function() {
    let currentJeuIdToDelete = null;

    // Lorsqu'on clique sur un bouton de suppression, enregistrer l'ID et ouvrir la modal
    $('#jeuxTable').on('click', '.btnDelete', function() {
        currentJeuIdToDelete = $(this).data('id');
        $('#deleteJeuModal').modal('show');
    });

    // Lorsqu'on confirme la suppression, envoyer la requête DELETE
    $('#confirmDeleteButton').on('click', function() {
        if (!currentJeuIdToDelete) return;

        $.ajax({
            url: '/admin/jeu/' + currentJeuIdToDelete,
            type: 'DELETE',
            success: function(response) {
                $('#deleteJeuModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#jeuxTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la suppression du jeu : " + xhr.responseText);
            }
        });
    });
});
