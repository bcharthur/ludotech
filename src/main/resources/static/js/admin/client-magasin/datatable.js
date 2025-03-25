// static/js/admin/client-magasin/datatable.js
$(document).ready(function () {
    if ($('#clientsMagasinTable').length) {
        $('#clientsMagasinTable').DataTable({
            ajax: {
                url: '/employe/datatable-json',
                dataSrc: ''
            },
            columns: [
                { data: 'id', title: 'ID' },
                { data: 'prenom', title: 'Prénom' },
                { data: 'nom', title: 'Nom' },
                { data: 'email', title: 'Email' },
                { data: 'telephone', title: 'Téléphone' },
                {
                    data: 'exemplaire',
                    title: 'Exemplaire',
                    render: function (data, type, row) {
                        if (data != null) {
                            // Affiche le code-barres et, si souhaité, le titre du jeu associé
                            return data.codebarre + ' (' + (data.jeu ? data.jeu.titre : '') + ')';
                        }
                        return 'Aucun';
                    }
                },
                {
                    data: null,
                    title: 'Actions',
                    render: function (data, type, row) {
                        return `
                            <button class="btn btn-warning btn-sm btnEdit" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#editUserModal"><i class="fa-solid fa-pen-to-square"></i></button>
                            <button class="btn btn-danger btn-sm btnDelete" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#deleteUserModal"><i class="fa-solid fa-trash"></i></button>
                        `;
                    },
                    orderable: false
                }
            ]
        });
    }
});
