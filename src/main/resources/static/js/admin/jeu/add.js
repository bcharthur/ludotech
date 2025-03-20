$(document).ready(function() {
    $('#addJeuForm').on('submit', function(e) {
        e.preventDefault();
        // Récupère les identifiants sélectionnés dans le select multiple
        const selectedGenreIds = $('#addGenres').val() || [];

        const jeuData = {
            titre: $('#addTitre').val(),
            reference: $('#addReference').val(),
            ageMin: $('#addAgeMin').val(),
            description: $('#addDescription').val(),
            duree: $('#addDuree').val(),
            tarifJour: $('#addTarifJour').val(),
            // Construit la liste des genres en créant un objet avec l'id pour chaque genre sélectionné
            genres: selectedGenreIds.map(function(id) {
                return { id: id };
            })
        };

        $.ajax({
            url: '/admin/jeu/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(jeuData),
            success: function(response) {
                $('#addJeuModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#jeuxTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de l'ajout du jeu : " + xhr.responseText);
            }
        });
    });
});
