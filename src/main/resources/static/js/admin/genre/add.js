$(document).ready(function() {
    $('#addGenreForm').on('submit', function(e) {
        e.preventDefault();
        const genreData = {
            libelle: $('#addLibelle').val()
        };

        $.ajax({
            url: '/admin/genre/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(genreData),
            success: function(response) {
                $('#addGenreModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#genresTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de l'ajout du genre : " + xhr.responseText);
            }
        });
    });
});
