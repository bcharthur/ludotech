$(document).ready(function() {
    const table = $('#exemplairesTable').DataTable({  // <== ici on garde une référence
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
                    }
                    buttons += '<button class="btn btn-warning btn-sm btnEdit" data-id="'+row.id+'"><i class="fa-solid fa-pen-to-square"></i></button> ';
                    buttons += '<button class="btn btn-danger btn-sm btnDelete" data-id="'+row.id+'"><i class="fa-solid fa-trash"></i></button>';
                    return buttons;
                }
            }
        ]
    });

    // 🔍 Champ de filtre pour le code-barres
    $('#barcodeFilter').on('keyup', function () {
        table.column(1).search(this.value).draw();
    });
});
