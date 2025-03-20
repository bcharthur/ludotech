$(document).ready(function () {
    if ($('#usersTable').length) {
        $('#usersTable').DataTable({
            ajax: {
                url: '/admin/datatable-json',
                dataSrc: ''
            },
            columns: [
                { data: 'id' },
                { data: 'firstName' },
                { data: 'lastName' },
                { data: 'email' },
                { data: 'phone' },
                {
                    data: null,
                    render: function (data, type, row) {
                        if (row.adresse) {
                            return row.adresse.street + ', ' + row.adresse.city;
                        }
                        return '';
                    }
                },
                {
                    data: null,
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
