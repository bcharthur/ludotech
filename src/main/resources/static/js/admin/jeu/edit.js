$(document).ready(function() {

    // Fonction qui recharge le select des genres pour l'édition en pré-sélectionnant ceux du jeu
    function updateGenreSelectForEdit(game) {
        $.ajax({
            url: '/api/genres', // appel à l'API qui renvoie tous les genres
            method: 'GET',
            success: function(genres) {
                var select = $('#editGenres');
                select.empty();
                // Pour chaque genre, créer une option et si le jeu possède ce genre, le marquer comme sélectionné
                genres.forEach(function(genre) {
                    var option = $('<option>', { value: genre.id, text: genre.libelle });
                    if (game.genres && game.genres.some(g => g.id === genre.id)) {
                        option.prop('selected', true);
                    }
                    select.append(option);
                });
            },
            error: function(error) {
                console.error("Erreur lors de la récupération des genres", error);
            }
        });
    }

    // Lorsqu'on clique sur le bouton d'édition, on charge les informations du jeu et on remplit le select
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
                // Recharge le select des genres avec pré-sélection
                updateGenreSelectForEdit(jeu);
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
        const selectedGenreIds = $('#editGenres').val() || [];
        const jeuData = {
            id: $('#editJeuId').val(),
            titre: $('#editTitre').val(),
            reference: $('#editReference').val(),
            ageMin: $('#editAgeMin').val(),
            description: $('#editDescription').val(),
            duree: $('#editDuree').val(),
            tarifJour: $('#editTarifJour').val(),
            // Construit la liste des genres en envoyant leurs ids
            genres: selectedGenreIds.map(function(id) {
                return { id: id };
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
