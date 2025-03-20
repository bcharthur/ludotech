$(document).ready(function() {
    let currentExemplaireIdToDelete = null;

    $('#exemplairesTable').on('click', '.btnDelete', function() {
        currentExemplaireIdToDelete = $(this).data('id');
        console.log("Bouton de suppression cliqué pour l'exemplaire id :", currentExemplaireIdToDelete);
        $('#deleteExemplaireModal').modal('show');
    });

    $('#confirmDeleteExemplaireButton').on('click', function() {
        if (!currentExemplaireIdToDelete) {
            console.error("Aucun exemplaire sélectionné pour la suppression");
            return;
        }
        console.log("Envoi de la requête DELETE pour l'exemplaire id :", currentExemplaireIdToDelete);
        $.ajax({
            url: '/admin/exemplaire/' + currentExemplaireIdToDelete,
            type: 'DELETE',
            success: function(response) {
                console.log("Exemplaire supprimé :", response);
                $('#deleteExemplaireModal').modal('hide');
                $('#successModalMessage').text(response);
                $('#successModal').modal('show');
                $('#exemplairesTable').DataTable().ajax.reload(null, false);
            },
            error: function(xhr) {
                console.error("Erreur lors de la suppression de l'exemplaire id", currentExemplaireIdToDelete, ":", xhr.responseText);
                alert("Erreur lors de la suppression de l'exemplaire : " + xhr.responseText);
            }
        });
    });
});
