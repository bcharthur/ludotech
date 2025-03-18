$(document).ready(function () {

    // Afficher/masquer le bloc des critères lors du focus/blur
    $('#passwordReg').on('focus', function () {
        $('#passwordMessage').show();
    }).on('blur', function () {
        $('#passwordMessage').hide();
    });

    // Écoute de l'input sur le mot de passe
    $('#passwordReg').on('keyup', function () {
        var password = $(this).val();
        var score = calculateStrength(password);
        updateProgressBar(score);
        updateCriteria(password);
    });

    // Fonction de calcul de la force du mot de passe (score de 0 à 4)
    function calculateStrength(password) {
        var score = 0;
        if (/[a-z]/.test(password)) {
            score++;
        }
        if (/[A-Z]/.test(password)) {
            score++;
        }
        if (/[0-9]/.test(password)) {
            score++;
        }
        if (password.length >= 8) {
            score++;
        }
        return score;
    }

    // Mise à jour de la progressbar Bootstrap selon le score
    function updateProgressBar(score) {
        var percentage = (score / 4) * 100;
        var progressBar = $('#passwordProgress');
        progressBar.css('width', percentage + '%');
        progressBar.attr('aria-valuenow', percentage);

        // Définition de la couleur en fonction du score
        if (score <= 1) {
            progressBar.removeClass().addClass('progress-bar bg-danger');
        } else if (score === 2) {
            progressBar.removeClass().addClass('progress-bar bg-warning');
        } else if (score >= 3) {
            progressBar.removeClass().addClass('progress-bar bg-success');
        }
    }

    // Met à jour les messages de critères en fonction du contenu du mot de passe
    function updateCriteria(password) {
        // Critère : lettre minuscule
        if (/[a-z]/.test(password)) {
            $('#letter').removeClass('text-danger').addClass('text-success').html('<i class="fa-solid fa-check"></i> Une lettre <b>minuscule</b>');
        } else {
            $('#letter').removeClass('text-success').addClass('text-danger').html('<i class="fa-solid fa-xmark"></i> Une lettre <b>minuscule</b>');
        }
        // Critère : lettre majuscule
        if (/[A-Z]/.test(password)) {
            $('#capital').removeClass('text-danger').addClass('text-success').html('<i class="fa-solid fa-check"></i> Une lettre <b>majuscule</b>');
        } else {
            $('#capital').removeClass('text-success').addClass('text-danger').html('<i class="fa-solid fa-xmark"></i> Une lettre <b>majuscule</b>');
        }
        // Critère : nombre
        if (/[0-9]/.test(password)) {
            $('#number').removeClass('text-danger').addClass('text-success').html('<i class="fa-solid fa-check"></i> Un <b>chiffre</b>');
        } else {
            $('#number').removeClass('text-success').addClass('text-danger').html('<i class="fa-solid fa-xmark"></i> Un <b>chiffre</b>');
        }
        // Critère : longueur minimale
        if (password.length >= 8) {
            $('#length').removeClass('text-danger').addClass('text-success').html('<i class="fa-solid fa-check"></i> Au moins <b>8 caractères</b>');
        } else {
            $('#length').removeClass('text-success').addClass('text-danger').html('<i class="fa-solid fa-xmark"></i> Au moins <b>8 caractères</b>');
        }
    }


    // Votre code existant pour la soumission du formulaire
    $('#registerForm').on('submit', function (e) {
        e.preventDefault();
        $('#registerSpinner').removeClass('d-none');
        var csrfParameterName = $('#registerCsrfToken').attr('name');
        var csrfToken = $('#registerCsrfToken').val();
        $.ajax({
            type: 'POST',
            url: '/register',
            data: {
                firstName: $('#firstName').val(),
                lastName: $('#lastName').val(),
                email: $('#emailReg').val(),
                phone: $('#phone').val(),
                address: $('#address').val(),
                password: $('#passwordReg').val(),
                confirmPassword: $('#confirmPassword').val(),
                [csrfParameterName]: csrfToken
            },
            success: function (response) {
                $('#registerSpinner').addClass('d-none');
                var registerModalEl = document.getElementById('registerModal');
                var registerModal = bootstrap.Modal.getInstance(registerModalEl);
                registerModal.hide();
                var successModalEl = document.getElementById('registerSuccessModal');
                var successModal = new bootstrap.Modal(successModalEl);
                successModal.show();
            },
            error: function (xhr) {
                $('#registerSpinner').addClass('d-none');
                var errorMsg = "";
                if (xhr.responseJSON) {
                    $.each(xhr.responseJSON, function (key, value) {
                        errorMsg += "<p>" + key + ": " + value + "</p>";
                    });
                } else {
                    errorMsg = "Registration failed due to unknown error.";
                }
                $('#register-alert-placeholder').html('<div class="alert alert-danger" role="alert">' + errorMsg + '</div>');
            }
        });
    });
});
