$(document).ready(function () {
    // Quand la modal login est fermée, on efface le message d'erreur
    $('#loginModal').on('hidden.bs.modal', function () {
        $('#alert-placeholder').empty();
    });

    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        $('#spinner').removeClass('d-none');
        var email = $('#email').val();
        var password = $('#password').val();
        var csrfParameterName = $('#csrfToken').attr('name');
        var csrfToken = $('#csrfToken').val();

        // Vérifier si l'email est enregistré
        $.ajax({
            type: 'GET',
            url: '/check-email',
            data: { email: email },
            success: function(response) {
                // L'email existe, on procède à vérifier le mot de passe
                $.ajax({
                    type: 'GET',
                    url: '/check-password',
                    data: { email: email, password: password },
                    success: function(response) {
                        // Le mot de passe est correct, on procède à la connexion
                        $.ajax({
                            type: 'POST',
                            url: '/login',
                            data: {
                                username: email,
                                password: password,
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
                    },
                    error: function(xhr) {
                        $('#spinner').addClass('d-none');
                        // Si le statut 401 indique un mot de passe erroné
                        if (xhr.status === 401) {
                            var msg = "Mot de passe erroné";
                            $('#alert-placeholder').html('<div class="alert alert-danger" role="alert">' + msg + '</div>');
                        } else {
                            var msg = "Erreur lors de la vérification du mot de passe";
                            $('#alert-placeholder').html('<div class="alert alert-danger" role="alert">' + msg + '</div>');
                        }
                    }
                });
            },
            error: function(xhr) {
                $('#spinner').addClass('d-none');
                // L'email n'est pas enregistré
                var msg = "Vous n'êtes pas inscrit : <a href=\"#\" data-bs-dismiss=\"modal\" data-bs-toggle=\"modal\" data-bs-target=\"#registerModal\">Inscrivez-vous ici</a>";
                $('#alert-placeholder').html('<div class="alert alert-danger" role="alert">' + msg + '</div>');
            }
        });
    });
});
