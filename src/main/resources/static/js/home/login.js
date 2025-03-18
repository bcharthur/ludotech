$(document).ready(function () {
    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        $('#spinner').removeClass('d-none');
        var csrfParameterName = $('#csrfToken').attr('name');
        var csrfToken = $('#csrfToken').val();
        $.ajax({
            type: 'POST',
            url: '/login',
            data: {
                username: $('#email').val(),
                password: $('#password').val(),
                [csrfParameterName]: csrfToken
            },
            success: function (response) {
                $('#spinner').addClass('d-none');
                window.location.href = "/";
            },
            error: function (xhr) {
                $('#spinner').addClass('d-none');
                var msg = "Échec de la connexion : " +
                    (xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : "Erreur inconnue");
                $('#alert-placeholder').html('<div class="alert alert-danger" role="alert">' + msg + '</div>');
            }
        });
    });
});
