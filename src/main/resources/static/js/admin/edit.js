$(document).ready(function() {
    // Lorsque l'on clique sur un bouton .btnEdit, charger les données de l'utilisateur
    $('#usersTable').on('click', '.btnEdit', function() {
        const userId = $(this).data('id');
        $.ajax({
            url: '/admin/user/' + userId,
            type: 'GET',
            success: function(user) {
                // Remplir les champs de la modal
                $('#editUserId').val(user.id);
                $('#editFirstName').val(user.firstName);
                $('#editLastName').val(user.lastName);
                $('#editEmail').val(user.email);
                $('#editPhone').val(user.phone);
                $('#editAddress').val(user.address);
                // Ouvrir la modal
                $('#editUserModal').modal('show');
            },
            error: function() {
                alert("Impossible de récupérer les informations de l'utilisateur.");
            }
        });
    });

    // Lorsque le formulaire d'édition est soumis
    $('#editUserForm').on('submit', function(e) {
        e.preventDefault();
        const userData = {
            id: $('#editUserId').val(),
            firstName: $('#editFirstName').val(),
            lastName: $('#editLastName').val(),
            email: $('#editEmail').val(),
            phone: $('#editPhone').val(),
            address: $('#editAddress').val()
        };

        $.ajax({
            url: '/admin/user/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: function(response) {
                // Fermer la modal d'édition
                $('#editUserModal').modal('hide');

                // Afficher la modal de succès
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');

                // Rafraîchir le DataTable
                $('#usersTable').DataTable().ajax.reload(null, false);
            },

            error: function(xhr) {
                alert("Erreur lors de la modification de l'utilisateur : " + xhr.responseText);
            }
        });
    });
});
