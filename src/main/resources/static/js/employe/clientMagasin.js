$(document).ready(function () {
    // Fonction pour charger et alimenter le select avec la liste des clients magasin
    function loadClientMagasinList() {
        $.ajax({
            url: '/api/client-magasin',
            type: 'GET',
            success: function (clients) {
                let $select = $('#selectClientMagasin');
                $select.empty(); // Vide le select
                $select.append('<option value="">-- Choisissez un client --</option>');
                // Pour chaque client, ajouter une option
                clients.forEach(function(client) {
                    let optionText = client.prenom + " " + client.nom + " (" + client.telephone + ")";
                    $select.append('<option value="' + client.id + '">' + optionText + '</option>');
                });
            },
            error: function () {
                alert("Erreur lors du chargement de la liste des clients magasin.");
            }
        });
    }

    // Lors de l'ouverture de la modal "Louer à un client magasin", charger la liste
    $('#modalLouerExemplaire').on('shown.bs.modal', function () {
        loadClientMagasinList();
    });

    // Gestion de la soumission du formulaire de location pour un client magasin
    $('#formLouerClientMagasin').on('submit', function (e) {
        e.preventDefault();
        const clientId = $('#selectClientMagasin').val();
        const exemplaireId = $('#modalLouerExemplaire').data('exemplaire-id');

        if (!clientId) {
            alert("Veuillez sélectionner un client.");
            return;
        }

        $.ajax({
            url: '/api/jeu/louer-exemplaire',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                clientMagasinId: parseInt(clientId),
                exemplaireId: parseInt(exemplaireId)
            }),
            success: function(response) {
                $('#modalLouerExemplaire').modal('hide');
                $('#successModalMessage').text("Exemplaire loué avec succès !");
                $('#successModal').modal('show');
                // Actualiser le DataTable (si nécessaire)
                $('#exemplairesTable').DataTable().ajax.reload();
            },
            error: function(xhr) {
                $('#modalLouerExemplaire').modal('hide');
                $('#errorModalMessage').text("Erreur lors de la location : " + xhr.responseText);
                $('#errorModal').modal('show');
            }
        });
    });


    // Lorsque l'on clique sur "Ajouter fiche client magasin"
    $(document).on('click', '.btnAddClientMagasin', function () {
        // Récupère l'ID de l'exemplaire depuis le bouton
        const exemplaireId = $(this).data('exemplaire-id');
        // Place cette valeur dans le champ caché de la modal
        $('#ficheExemplaireId').val(exemplaireId);
        // Ouvre la modal
        $('#addClientMagasinModal').modal('show');
    });

    // Gestion de l'envoi du formulaire de création de fiche client magasin
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
                // Optionnel : actualiser un DataTable si nécessaire
            },
            error: function (xhr) {
                alert("Erreur : " + xhr.responseText);
            }
        });
    });

});
