// static/js/admin/exemplaire/datatable.js
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
                    if (!row.louable) {
                        buttons += '<button class="btn btn-primary btn-sm btn-return" data-codebarre="'+row.codebarre+'">Retour</button> ';
                    } else {
                        buttons += '<button class="btn btn-success btn-sm btn-louer-client-magasin" data-exemplaire-id="'+row.id+'">Louer</button> ';
                    }
                    buttons += '<button class="btn btn-warning btn-sm btnEdit" data-id="'+row.id+'"><i class="fa-solid fa-pen-to-square"></i></button> ';
                    buttons += '<button class="btn btn-danger btn-sm btnDelete" data-id="'+row.id+'"><i class="fa-solid fa-trash"></i></button>';
                    return buttons;
                }
            }
        ]
    });

    $('#barcodeFilter').on('keyup', function () {
        table.column(1).search(this.value).draw();
    });

    // Gestion du clic sur "Louer"
    $('#exemplairesTable').on('click', '.btn-louer-client-magasin', function() {
        const exemplaireId = $(this).data('exemplaire-id');
        $('#modalLouerExemplaire').data('exemplaire-id', exemplaireId);
        $('#modalLouerExemplaire').modal('show');
    });

    // Soumission de la location pour un client magasin
    $('#formLouerClientMagasin').on('submit', function(e){
        e.preventDefault();
        const clientId = $('#selectClientMagasin').val();
        const exemplaireId = $('#modalLouerExemplaire').data('exemplaire-id');

        $.ajax({
            url: '/api/employe/louer-exemplaire',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                clientMagasinId: clientId,
                exemplaireId: exemplaireId
            }),
            success: function() {
                $('#modalLouerExemplaire').modal('hide');
                $('#successModalMessage').text("Exemplaire loué avec succès !");
                $('#successModal').modal('show');
                table.ajax.reload();
            },
            error: function(xhr) {
                $('#modalLouerExemplaire').modal('hide');
                $('#errorModalMessage').text("Erreur : " + xhr.responseText);
                $('#errorModal').modal('show');
            }
        });
    });
});
