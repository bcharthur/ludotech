$(document).ready(function() {
    // Lorsqu'on clique sur un bouton d'édition, charger les données de la fiche client magasin
    $('#clientsMagasinTable').on('click', '.btnEdit', function() {
        const clientId = $(this).data('id');
        $.ajax({
            url: '/api/client-magasin/' + clientId,
            type: 'GET',
            success: function(client) {
                // Remplir le formulaire d'édition
                $('#editClientMagasinId').val(client.id);
                $('#editPrenom').val(client.prenom);
                $('#editNom').val(client.nom);
                $('#editEmail').val(client.email);
                $('#editTelephone').val(client.telephone);
                // Ouvrir la modal d'édition
                $('#editClientMagasinModal').modal('show');
            },
            error: function() {
                alert("Erreur lors de la récupération des informations du client magasin.");
            }
        });
    });

    // Lorsque le formulaire d'édition est soumis
    $('#editClientMagasinForm').on('submit', function(e) {
        e.preventDefault();
        const clientData = {
            id: $('#editClientMagasinId').val(),
            prenom: $('#editPrenom').val(),
            nom: $('#editNom').val(),
            email: $('#editEmail').val(),
            telephone: $('#editTelephone').val()
        };

        $.ajax({
            url: '/api/client-magasin/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(clientData),
            success: function(response) {
                $('#editClientMagasinModal').modal('hide');
                $('#successModalMessage').text("Fiche client magasin modifiée avec succès !");
                $('#successModal').modal('show');
                $('#clientsMagasinTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la modification : " + xhr.responseText);
            }
        });
    });
});
