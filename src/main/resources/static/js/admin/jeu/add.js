$(document).ready(function() {

    // Fonction qui recharge le select des genres via l'API de HomeController
    function updateGenreSelect() {
        $.ajax({
            url: '/api/genres', // endpoint qui renvoie tous les genres en JSON
            method: 'GET',
            success: function(genres) {
                var select = $('#addGenres');
                select.empty();
                // Option par défaut
                select.append($('<option>', { text: "Sélectionnez un ou plusieurs genres", disabled: true, selected: true }));
                genres.forEach(function(genre) {
                    select.append($('<option>', { value: genre.id, text: genre.libelle }));
                });
            },
            error: function(error) {
                console.error("Erreur lors de la récupération des genres", error);
            }
        });
    }

    $('#addJeuModal').on('show.bs.modal', function() {
        updateGenreSelect();
    });


    // Recharge la liste des genres à chaque ouverture du modal d'ajout de jeu
    $('#addJeuModal').on('show.bs.modal', function() {
        updateGenreSelect();
    });

    // Si vous avez un modal d'ajout de genre, vous pouvez aussi recharger dès qu'il se ferme
    $('#addGenreModal').on('hidden.bs.modal', function() {
        updateGenreSelect();
    });

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
                let errorMessage = "Erreur lors de l'ajout du jeu";
                try {
                    const errorResponse = JSON.parse(xhr.responseText);
                    if (errorResponse.message) {
                        errorMessage += " : " + errorResponse.message;
                    } else {
                        errorMessage += " : " + xhr.responseText;
                    }
                } catch(e) {
                    errorMessage += " : " + xhr.responseText;
                }
                alert(errorMessage);
            }
        });
    });
});
