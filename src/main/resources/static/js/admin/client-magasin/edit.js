$(document).ready(function() {
    // Lorsque l'on clique sur un bouton .btnEdit, charger les données du client
    $('#usersTable').on('click', '.btnEdit', function() {
        const clientId = $(this).data('id');
        $.ajax({
            url: '/admin/client/' + clientId,
            type: 'GET',
            success: function(client) {
                // Remplir les champs du formulaire
                $('#editUserId').val(client.id);
                $('#editFirstName').val(client.firstName);
                $('#editLastName').val(client.lastName);
                $('#editEmail').val(client.email);
                $('#editPhone').val(client.phone);

                // Remplir les champs d'adresse
                $('#editStreet').val(client.adresse ? client.adresse.street : '');
                $('#editCity').val(client.adresse ? client.adresse.city : '');
                $('#editPostalCode').val(client.adresse ? client.adresse.postalCode : '');
                $('#editCountry').val(client.adresse ? client.adresse.country : '');

                // Ouvrir la modal
                $('#editUserModal').modal('show');
            },
            error: function() {
                alert("Impossible de récupérer les informations du client.");
            }
        });
    });

    // Lorsque le formulaire d'édition est soumis
    $('#editUserForm').on('submit', function(e) {
        e.preventDefault();
        const clientData = {
            id: $('#editUserId').val(),
            firstName: $('#editFirstName').val(),
            lastName: $('#editLastName').val(),
            email: $('#editEmail').val(),
            phone: $('#editPhone').val(),
            adresse: {
                street: $('#editStreet').val(),
                city: $('#editCity').val(),
                postalCode: $('#editPostalCode').val(),
                country: $('#editCountry').val()
            }
        };

        $.ajax({
            url: '/admin/client/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(clientData),
            success: function(response) {
                $('#editUserModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#usersTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                alert("Erreur lors de la modification du client : " + xhr.responseText);
            }
        });
    });
});
