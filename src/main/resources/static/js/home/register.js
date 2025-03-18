$(document).ready(function () {
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
