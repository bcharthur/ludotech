$(document).ready(function() {

    // Fonction pour charger dynamiquement la liste des jeux dans le select du modal d'ajout d'exemplaire
    function updateJeuSelect() {
        $.ajax({
            url: '/api/jeux', // Cet endpoint renvoie tous les jeux au format JSON
            method: 'GET',
            success: function(jeux) {
                console.log("Jeux récupérés :", jeux);
                var select = $('#addJeuSelect');
                select.empty();
                // Option par défaut
                select.append($('<option>', {
                    value: '',
                    text: "-- Sélectionnez un jeu --",
                    disabled: true,
                    selected: true
                }));
                // Ajouter une option pour chaque jeu
                jeux.forEach(function(jeu) {
                    // On utilise "jeu.titre" comme texte, en supposant que c'est le titre du jeu
                    select.append($('<option>', {
                        value: jeu.id,
                        text: jeu.titre
                    }));
                });
            },
            error: function(error) {
                console.error("Erreur lors de la récupération des jeux :", error);
            }
        });
    }

    // Charger la liste des jeux à l'ouverture du modal d'ajout d'exemplaire
    $('#addExemplaireModal').on('show.bs.modal', function() {
        updateJeuSelect();
    });

    // Envoi du formulaire d'ajout d'exemplaire
    $('#addExemplaireForm').on('submit', function(e) {
        e.preventDefault();

        // Construire l'objet exemplaire
        const exemplaireData = {
            codebarre: $('#addCodebarre').val(),
            // La case checkbox renvoie true si cochée, sinon false
            louable: $('#addLouable').is(':checked'),
            // Comme un exemplaire est lié à un seul jeu, on envoie un objet jeu avec son id
            jeu: { id: $('#addJeuSelect').val() }
        };

        console.log("Données de l'exemplaire à ajouter :", exemplaireData);

        $.ajax({
            url: '/admin/exemplaire/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(exemplaireData),
            success: function(response) {
                console.log("Exemplaire ajouté :", response);
                $('#addExemplaireModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#exemplairesTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                console.error("Erreur lors de l'ajout de l'exemplaire :", xhr.responseText);
                alert("Erreur lors de l'ajout de l'exemplaire : " + xhr.responseText);
            }
        });
    });
});
