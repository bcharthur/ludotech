$(document).ready(function () {
    if ($('#usersTable').length) {
        $('#usersTable').DataTable({
            ajax: {
                url: '/admin/datatable-json',
                dataSrc: ''  // L'endpoint renvoie une liste d'objets
            },
            columns: [
                { data: 'id' },
                { data: 'firstName' },
                { data: 'lastName' },
                { data: 'email' },
                { data: 'phone' },
                { data: 'address' }
            ],
            language: {
                url: "//cdn.datatables.net/plug-ins/1.13.4/i18n/fr-FR.json",
                paginate: {
                    previous: "",
                    next: ""
                }
            }
        });
    }
});
