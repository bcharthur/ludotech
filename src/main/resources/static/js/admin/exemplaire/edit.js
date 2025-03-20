$(document).ready(function() {

    // Fonction pour charger la liste des jeux dans le select du modal d'édition
    function updateEditJeuSelect(callback) {
        $.ajax({
            url: '/api/jeux', // Assurez-vous que cet endpoint renvoie tous les jeux en JSON
            type: 'GET',
            success: function(jeux) {
                console.log("Jeux récupérés pour l'édition :", jeux);
                let select = $('#editJeuSelect');
                select.empty();
                select.append($('<option>', {
                    value: '',
                    text: "-- Sélectionnez un jeu --",
                    disabled: true,
                    selected: true
                }));
                jeux.forEach(function(jeu) {
                    select.append($('<option>', {
                        value: jeu.id,
                        text: jeu.titre
                    }));
                });
                if (callback) callback();
            },
            error: function(error) {
                console.error("Erreur lors de la récupération des jeux pour l'édition :", error);
            }
        });
    }

    // Lorsqu'on clique sur le bouton "Editer" d'un exemplaire
    $('#exemplairesTable').on('click', '.btnEdit', function() {
        const exemplaireId = $(this).data('id');
        console.log("Bouton Editer cliqué pour l'exemplaire id :", exemplaireId);
        // Charger le select des jeux puis récupérer les infos de l'exemplaire
        updateEditJeuSelect(function() {
            $.ajax({
                url: '/admin/exemplaire/' + exemplaireId,
                type: 'GET',
                success: function(exemplaire) {
                    console.log("Exemplaire récupéré :", exemplaire);
                    $('#editExemplaireId').val(exemplaire.id);
                    $('#editCodebarre').val(exemplaire.codebarre);
                    $('#editLouable').prop('checked', exemplaire.louable);
                    // Définir la valeur du select sur l'id du jeu associé
                    $('#editJeuSelect').val(exemplaire.jeu.id);
                    $('#editExemplaireModal').modal('show');
                },
                error: function() {
                    alert("Impossible de récupérer les informations de l'exemplaire.");
                }
            });
        });
    });

    // Envoi du formulaire d'édition
    $('#editExemplaireForm').on('submit', function(e) {
        e.preventDefault();
        const exemplaireData = {
            id: $('#editExemplaireId').val(),
            codebarre: $('#editCodebarre').val(),
            louable: $('#editLouable').is(':checked'),
            jeu: { id: $('#editJeuSelect').val() }
        };
        console.log("Données à envoyer pour l'édition de l'exemplaire :", exemplaireData);
        $.ajax({
            url: '/admin/exemplaire/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(exemplaireData),
            success: function(response) {
                console.log("Exemplaire modifié :", response);
                $('#editExemplaireModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#exemplairesTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                console.error("Erreur lors de la modification de l'exemplaire :", xhr.responseText);
                alert("Erreur lors de la modification de l'exemplaire : " + xhr.responseText);
            }
        });
    });
});
