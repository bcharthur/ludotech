$(document).ready(function() {
    const table = $('#exemplairesTable').DataTable({
        ajax: {
            url: '/admin/exemplaire/all',
            dataSrc: ''
        },
        columns: [
            { data: 'id', title: 'ID' },
            { data: 'codebarre', title: 'Code-barres' },
            {
                data: 'louable',
                title: 'Louable',
                render: function(data) {
                    return data ? 'Oui' : 'Non';
                }
            },
            {
                data: 'jeu',
                title: 'Jeu',
                render: function(data) {
                    return data ? data.titre : '';
                }
            },
            {
                data: null,
                title: 'Actions',
                render: function(data, type, row) {
                    let buttons = '';
                    // Si l'exemplaire est louable, affiche le bouton "Louer" pour rattacher à un client magasin
                    if (row.louable) {
                        buttons += '<button class="btn btn-success btn-sm btn-louer-client-magasin" data-exemplaire-id="' + row.id + '">Louer</button> ';
                    } else {
                        // Sinon, affiche le bouton de retour (si non louable)
                        buttons += '<button class="btn btn-primary btn-sm btn-return" data-codebarre="' + row.codebarre + '">Retour</button> ';
                    }
                    buttons += '<button class="btn btn-warning btn-sm btnEdit" data-id="' + row.id + '"><i class="fa-solid fa-pen-to-square"></i></button> ';
                    buttons += '<button class="btn btn-danger btn-sm btnDelete" data-id="' + row.id + '"><i class="fa-solid fa-trash"></i></button>';
                    return buttons;
                }
            }
        ]
    });

    // Filtre par code-barres
    $('#barcodeFilter').on('keyup', function () {
        table.column(1).search(this.value).draw();
    });

    // Gestion du clic sur le bouton "Louer" pour un client magasin
    $('#exemplairesTable').on('click', '.btn-louer-client-magasin', function() {
        const exemplaireId = $(this).data('exemplaire-id');
        // On stocke l'ID de l'exemplaire dans un attribut data de la modal
        $('#modalLouerExemplaire').data('exemplaire-id', exemplaireId);
        $('#modalLouerExemplaire').modal('show');
    });

    // Gestion de la soumission du formulaire de location pour un client magasin
    $('#formLouerClientMagasin').on('submit', function(e){
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
                table.ajax.reload();
            },
            error: function(xhr) {
                $('#modalLouerExemplaire').modal('hide');
                $('#errorModalMessage').text("Erreur lors de la location : " + xhr.responseText);
                $('#errorModal').modal('show');
            }
        });
    });
});
