// static/js/employe/clientMagasin.js
$(document).ready(function () {
    // Ouvre la modal avec l'exemplaire sélectionné
    $(document).on('click', '.btnAddClientMagasin', function () {
        const exId = $(this).data('exemplaire-id');
        $('#ficheExemplaireId').val(exId);
        $('#addClientMagasinModal').modal('show');
    });

    // Envoi du formulaire
    $('#addClientMagasinForm').on('submit', function (e) {
        e.preventDefault();

        const data = {
            prenom: $(this).find('input[name="prenom"]').val(),
            nom: $(this).find('input[name="nom"]').val(),
            email: $(this).find('input[name="email"]').val(),
            telephone: $(this).find('input[name="telephone"]').val()
        };

        const exemplaireId = $('#ficheExemplaireId').val();

        $.ajax({
            url: '/api/client-magasin/add?exemplaireId=' + exemplaireId,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                $('#addClientMagasinModal').modal('hide');
                alert("Fiche client magasin ajoutée avec succès !");
            },
            error: function (xhr) {
                alert("Erreur : " + xhr.responseText);
            }
        });
    });
});
