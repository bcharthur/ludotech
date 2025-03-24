$(document).ready(function(){
    // Ouvre la modal retour avec le bon code barre
    $(document).on('click', '.btn-return', function(){
        const barcode = $(this).data('codebarre');
        $('#retourBarcode').val(barcode);
        $('#retourExemplaireModal').modal('show');
    });

    // Soumission du formulaire de retour
    $('#formRetourExemplaire').on('submit', function(e){
        e.preventDefault();
        const dto = {
            barcode: $('#retourBarcode').val(),
            etat: $('#etat').val()
        };
        $.ajax({
            url: '/api/employe/return',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(){
                $('#retourExemplaireModal').modal('hide');
                $('#successModalMessage').text('Retour enregistré avec succès !');
                $('#successModal').modal('show');

                // Recharge la DataTable
                $('#exemplairesTable').DataTable().ajax.reload();
            },
            error: function(xhr){
                $('#retourExemplaireModal').modal('hide');
                $('#errorModalMessage').text("Erreur : " + xhr.responseText);
                $('#errorModal').modal('show');
            }
        });
    });
});
