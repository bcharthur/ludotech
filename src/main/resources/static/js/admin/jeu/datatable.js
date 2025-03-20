$(document).ready(function () {
    if ($('#jeuxTable').length) {
        $('#jeuxTable').DataTable({
            ajax: {
                url: '/admin/jeu/datatable-json',
                dataSrc: ''
            },
            columns: [
                { data: 'id' },
                { data: 'titre' },
                { data: 'reference' },
                { data: 'ageMin' },
                { data: 'description' },
                { data: 'duree' },
                { data: 'tarifJour' },
                {
                    data: 'genres',
                    render: function (data, type, row) {
                        if(data && data.length > 0) {
                            return data.map(function(g) { return g.libelle; }).join(', ');
                        }
                        return '';
                    }
                },
                {
                    data: null,
                    render: function(data, type, row) {
                        return `
                            <button class="btn btn-warning btn-sm btnEdit" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#editJeuModal">Éditer</button>
                            <button class="btn btn-danger btn-sm btnDelete" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#deleteJeuModal">Supprimer</button>
                        `;
                    },
                    orderable: false
                }
            ]
        });
    }
});
