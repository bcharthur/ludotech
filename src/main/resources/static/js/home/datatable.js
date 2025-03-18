$(document).ready(function () {
    if ($('#usersTable').length) {
        $('#usersTable').DataTable({
            ajax: {
                url: '/admin/datatable-json',
                dataSrc: ''  // Puisque l'endpoint renvoie directement une liste d'objets
            },
            columns: [
                { data: 'id' },
                { data: 'firstName' },
                { data: 'lastName' },
                { data: 'email' },
                { data: 'phone' },
                { data: 'address' }
            ]
        });
    }
});
