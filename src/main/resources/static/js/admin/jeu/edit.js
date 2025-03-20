$(document).ready(function() {
    // Lorsqu'on clique sur le bouton d'édition, charger les informations du jeu
    $('#jeuxTable').on('click', '.btnEdit', function() {
        const jeuId = $(this).data('id');
        $.ajax({
            url: '/admin/jeu/' + jeuId,
            type: 'GET',
            success: function(jeu) {
                $('#editJeuId').val(jeu.id);
                $('#editTitre').val(jeu.titre);
                $('#editReference').val(jeu.reference);
                $('#editAgeMin').val(jeu.ageMin);
                $('#editDescription').val(jeu.description);
                $('#editDuree').val(jeu.duree);
                $('#editTarifJour').val(jeu.tarifJour);
                // Pour les genres, on utilise un champ texte avec les libellés séparés par une virgule
                if (jeu.genres && jeu.genres.length > 0) {
                    let genresStr = jeu.genres.map(function(g) { return g.libelle; }).join(', ');
                    $('#editGenres').val(genresStr);
                } else {
                    $('#editGenres').val('');
                }
                $('#editJeuModal').modal('show');
            },
            error: function() {
                alert("Impossible de récupérer les informations du jeu.");
            }
        });
    });

    // Envoi du formulaire d'édition
    $('#editJeuForm').on('submit', function(e) {
        e.preventDefault();
        const jeuData = {
            id: $('#editJeuId').val(),
            titre: $('#editTitre').val(),
            reference: $('#editReference').val(),
            ageMin: $('#editAgeMin').val(),
            description: $('#editDescription').val(),
            duree: $('#editDuree').val(),
            tarifJour: $('#editTarifJour').val(),
            // Les genres sont envoyés sous forme d'une liste d'objets avec le libellé
            genres: $('#editGenres').val().split(',').map(function(item) {
                return { libelle: item.trim() };
            })
        };

        $.ajax({
            url: '/admin/jeu/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(jeuData),
            success: function(response) {
                $('#editJeuModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#jeuxTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la modification du jeu : " + xhr.responseText);
            }
        });
    });
});
