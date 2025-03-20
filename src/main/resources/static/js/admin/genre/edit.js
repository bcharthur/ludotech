$(document).ready(function() {
    $('#genresTable').on('click', '.btnEdit', function() {
        const genreId = $(this).data('id');
        $.ajax({
            url: '/admin/genre/' + genreId,
            type: 'GET',
            success: function(genre) {
                $('#editGenreId').val(genre.id);
                $('#editLibelle').val(genre.libelle);
                $('#editGenreModal').modal('show');
            },
            error: function() {
                alert("Impossible de récupérer les informations du genre.");
            }
        });
    });

    $('#editGenreForm').on('submit', function(e) {
        e.preventDefault();
        const genreData = {
            id: $('#editGenreId').val(),
            libelle: $('#editLibelle').val()
        };

        $.ajax({
            url: '/admin/genre/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(genreData),
            success: function(response) {
                $('#editGenreModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#genresTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la modification du genre : " + xhr.responseText);
            }
        });
    });
});
