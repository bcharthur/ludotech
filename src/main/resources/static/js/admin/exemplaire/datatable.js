$(document).ready(function() {
    $('#exemplairesTable').DataTable({
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
                    return '<button class="btn btn-warning btn-sm btnEdit" data-id="'+row.id+'"><i class="fa-solid fa-pen-to-square"></i></button> ' +
                        '<button class="btn btn-danger btn-sm btnDelete" data-id="'+row.id+'"><i class="fa-solid fa-trash"></i></button>';
                }
            }
        ]
    });
});
