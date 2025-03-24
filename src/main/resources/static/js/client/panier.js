// static/js/client/panier.js
$(document).ready(function(){
    // Fonction pour charger et afficher le contenu du panier dans la modal
    function loadPanier() {
        $.ajax({
            url: '/api/panier',
            type: 'GET',
            success: function(panier) {
                let html = '';
                if (panier.jeux && panier.jeux.length > 0) {
                    html += '<table class="table">';
                    html += '<thead><tr><th>Jeu</th><th>Disponibilité</th><th>Action</th></tr></thead><tbody>';
                    panier.jeux.forEach(function(jeu) {
                        html += '<tr data-jeu-id="' + jeu.id + '">';
                        html += '<td>' + jeu.titre + '</td>';
                        // La cellule de disponibilité (sera mise à jour dynamiquement)
                        html += '<td class="available-count" data-jeu-id="' + jeu.id + '">Dispo : -</td>';
                        html += '<td><button class="btn btn-danger btn-remove" data-jeu-id="' + jeu.id + '">Retirer</button></td>';
                        html += '</tr>';
                    });
                    html += '</tbody></table>';
                } else {
                    html += '<p>Votre panier est vide.</p>';
                }
                $('#panierContent').html(html);

                // Attacher l'événement de suppression pour les boutons nouvellement générés
                $('.btn-remove').on('click', function(){
                    let jeuId = $(this).data('jeu-id');
                    $.post('/api/panier/remove/' + jeuId, function(data){
                        loadPanier(); // Recharge le contenu du panier après suppression
                    }).fail(function(xhr){
                        $("#errorModalMessage").text("Erreur lors de la suppression : " + xhr.responseText);
                        $("#errorModal").modal("show");
                    });
                });

                // Pour chaque jeu du panier, vérifier la disponibilité via AJAX et utiliser des promesses
                if (panier.jeux && panier.jeux.length > 0) {
                    let promises = [];
                    panier.jeux.forEach(function(jeu) {
                        let promise = $.ajax({
                            url: '/api/jeu/' + jeu.id + '/exemplaires/disponibles',
                            type: 'GET'
                        }).done(function(count) {
                            // Met à jour le compteur de disponibilité
                            $('td.available-count[data-jeu-id="' + jeu.id + '"]').text("Dispo : " + count);
                            // Marquer le jeu comme indisponible s'il n'y a plus d'exemplaires disponibles
                            if (count <= 0) {
                                let row = $('tr[data-jeu-id="' + jeu.id + '"]');
                                if (row.find('.badge-not-available').length === 0) {
                                    row.find('td.available-count').append(' <span class="badge bg-danger badge-not-available">Plus disponible !</span>');
                                }
                                row.data('notAvailable', true);
                            } else {
                                $('tr[data-jeu-id="' + jeu.id + '"]').data('notAvailable', false);
                                $('tr[data-jeu-id="' + jeu.id + '"]').find('.badge-not-available').remove();
                            }
                        }).fail(function() {
                            $('td.available-count[data-jeu-id="' + jeu.id + '"]').text("Erreur de disponibilité");
                            $('tr[data-jeu-id="' + jeu.id + '"]').data('notAvailable', true);
                        });
                        promises.push(promise);
                    });
                    // Une fois que toutes les requêtes sont terminées, vérifier si au moins un jeu est indisponible
                    $.when.apply($, promises).done(function() {
                        let disableReservation = false;
                        panier.jeux.forEach(function(jeu) {
                            if ($('tr[data-jeu-id="' + jeu.id + '"]').data('notAvailable')) {
                                disableReservation = true;
                            }
                        });
                        $('#btn-reserver-panier').prop('disabled', disableReservation);
                    });
                } else {
                    $('#btn-reserver-panier').prop('disabled', true);
                }
            },
            error: function() {
                $('#panierContent').html('<p>Erreur lors du chargement du panier.</p>');
            }
        });
    }

    // Lorsque la modal "Panier" est ouverte, charger son contenu
    $('#panierModal').on('shown.bs.modal', function(){
        loadPanier();
    });

    // Gérer l'ajout au panier pour le bouton "Ajouter au panier" sur les cartes de jeu
    $('.btn-add-panier').on('click', function(e){
        e.preventDefault();
        e.stopPropagation(); // Empêche l'ouverture de la modal de détail
        let jeuId = $(this).data('jeu-id');
        $.ajax({
            url: '/api/panier/add/' + jeuId,
            type: 'POST',
            success: function(response) {
                // Affiche la modal de succès pour l'ajout au panier
                $("#successPanierModalMessage").text("Jeu ajouté au panier !");
                $("#successPanierModal").modal("show");
            },
            error: function(xhr) {
                $("#errorModalMessage").text("Erreur lors de l'ajout au panier : " + xhr.responseText);
                $("#errorModal").modal("show");
            }
        });
    });
});
