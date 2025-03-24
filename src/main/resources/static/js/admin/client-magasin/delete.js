$(document).ready(function() {
    let currentUserIdToDelete = null;

    // Lorsque l'on clique sur un bouton .btnDelete
    $('#clientsMagasinTable').on('click', '.btnDelete', function() {
        currentUserIdToDelete = $(this).data('id');
        // Ouvrir la modal de confirmation
        $('#deleteUserModal').modal('show');
    });

    // Lorsque l'utilisateur confirme la suppression
    $('#confirmDeleteButton').on('click', function() {
        if (!currentUserIdToDelete) return;

        $.ajax({
            url: '/employe/client/' + currentUserIdToDelete,
            type: 'DELETE',
            success: function(response) {
                // Fermer la modal de confirmation de suppression
                $('#deleteUserModal').modal('hide');

                // Afficher la modal de succès
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');

                // Rafraîchir le DataTable
                $('#usersTable').DataTable().ajax.reload(null, false);
            },

            error: function(xhr) {
                alert("Erreur lors de la suppression : " + xhr.responseText);
            }
        });
    });
});
