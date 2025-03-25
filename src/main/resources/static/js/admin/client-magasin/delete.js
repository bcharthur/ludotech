$(document).ready(function() {
    let currentClientIdToDelete = null;

    // Lorsqu'on clique sur un bouton de suppression, stocker l'ID et ouvrir la modal de confirmation
    $('#clientsMagasinTable').on('click', '.btnDelete', function() {
        currentClientIdToDelete = $(this).data('id');
        $('#deleteClientMagasinModal').modal('show');
    });

    // Lorsque l'utilisateur confirme la suppression
    $('#confirmDeleteClientMagasinButton').on('click', function() {
        if (!currentClientIdToDelete) return;

        $.ajax({
            url: '/api/client-magasin/' + currentClientIdToDelete,
            type: 'DELETE',
            success: function(response) {
                $('#deleteClientMagasinModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');

                $('#clientsMagasinTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la suppression : " + xhr.responseText);
            }
        });
    });
});
