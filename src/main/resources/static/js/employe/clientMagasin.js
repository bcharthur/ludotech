$(document).ready(function () {

    // Fonction pour rafraîchir le DataTable des clients magasin
    function refreshClientMagasinTable() {
        if ($('#clientsMagasinTable').length) {
            $('#clientsMagasinTable').DataTable().ajax.reload(null, false);
        }
    }

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
                // Rafraîchit à la fois le DataTable des exemplaires et celui des clients magasin
                $('#exemplairesTable').DataTable().ajax.reload();
                refreshClientMagasinTable();
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
        // Récupère l'ID de l'exemplaire depuis le bouton (si besoin)
        const exemplaireId = $(this).data('exemplaire-id');
        // Pour créer une fiche, on veut que le champ soit vide (l'exemplaire n'est pas rattaché dès la création)
        $('#ficheExemplaireId').val('');
        // Ouvre la modal d'ajout
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
        // L'exemplaireId n'est renseigné qu'en cas de location via la modal "Louer", sinon il reste vide pour créer une fiche avec exemplaire à null
        const exemplaireId = $('#ficheExemplaireId').val();

        $.ajax({
            url: '/api/client-magasin/add' + (exemplaireId ? '?exemplaireId=' + exemplaireId : ''),
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                $('#addClientMagasinModal').modal('hide');
                $('#successModalMessage').text("Fiche client magasin ajoutée avec succès !");
                $('#successModal').modal('show');
                refreshClientMagasinTable();
            },
            error: function (xhr) {
                $('#addClientMagasinModal').modal('hide');
                $('#errorModalMessage').text("Erreur : " + xhr.responseText);
                $('#errorModal').modal('show');
            }
        });
    });

    // Pour les opérations d'édition et de suppression,
    // pensez à appeler "refreshClientMagasinTable()" dans les callbacks success des fichiers "edit.js" et "delete.js"

});
