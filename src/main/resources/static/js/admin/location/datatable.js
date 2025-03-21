// static/js/client/location/datatable.js
$(document).ready(function() {
    $('#locationsTable').DataTable({
        ajax: {
            url: '/api/locations/datatable',
            dataSrc: ''
        },
        columns: [
            { data: 'id', title: 'ID' },
            { data: 'clientName', title: 'Client' },
            { data: 'dateDebut', title: 'Date début' },
            { data: 'dateRetour', title: 'Date retour' },
            { data: 'tarifJour', title: 'Tarif/jour' },
            { data: 'jeux', title: 'Jeux', render: function(data) {
                    return data.join(', ');
                }
            },
            {
                data: null,
                title: 'Actions',
                render: function(data, type, row) {
                    return `<button class="btn btn-primary btn-edit" data-id="${row.id}">Editer</button>
                            <button class="btn btn-danger btn-delete" data-id="${row.id}">Supprimer</button>`;
                }
            }
        ]
    });
});
