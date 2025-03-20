$(document).ready(function () {
    if ($('#genresTable').length) {
        $('#genresTable').DataTable({
            ajax: {
                url: '/admin/genre/datatable-json',
                dataSrc: ''
            },
            columns: [
                { data: 'id' },
                { data: 'libelle' },
                {
                    data: null,
                    render: function (data, type, row) {
                        return `
                            <button class="btn btn-warning btn-sm btnEdit" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#editGenreModal"><i class="fa-solid fa-pen-to-square"></i></button>
                            <button class="btn btn-danger btn-sm btnDelete" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#deleteGenreModal"><i class="fa-solid fa-trash"></i></button>
                        `;
                    },
                    orderable: false
                }
            ]
        });
    }
});
