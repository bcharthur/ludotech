$(document).ready(function() {
    let currentGenreIdToDelete = null;

    $('#genresTable').on('click', '.btnDelete', function() {
        currentGenreIdToDelete = $(this).data('id');
        $('#deleteGenreModal').modal('show');
    });

    $('#confirmDeleteGenreButton').on('click', function() {
        if (!currentGenreIdToDelete) return;

        $.ajax({
            url: '/admin/genre/' + currentGenreIdToDelete,
            type: 'DELETE',
            success: function(response) {
                $('#deleteGenreModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#genresTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la suppression du genre : " + xhr.responseText);
            }
        });
    });
});
