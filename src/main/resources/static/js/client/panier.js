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
                    html += '<thead><tr><th>Jeu</th><th>Action</th></tr></thead><tbody>';
                    panier.jeux.forEach(function(jeu) {
                        html += '<tr>';
                        html += '<td>' + jeu.titre + '</td>';
                        html += '<td><button class="btn btn-danger btn-remove" data-jeu-id="' + jeu.id + '">Retirer</button></td>';
                        html += '</tr>';
                    });
                    html += '</tbody></table>';
                } else {
                    html += '<p>Votre panier est vide.</p>';
                }
                $('#panierContent').html(html);
                // Ré-attacher l'événement de suppression pour les nouveaux boutons
                $('.btn-remove').on('click', function(){
                    let jeuId = $(this).data('jeu-id');
                    $.post('/api/panier/remove/' + jeuId, function(data){
                        loadPanier(); // Recharge le contenu du panier après suppression
                    }).fail(function(xhr){
                        alert("Erreur lors de la suppression : " + xhr.responseText);
                    });
                });
            },
            error: function() {
                $('#panierContent').html('<p>Erreur lors du chargement du panier.</p>');
            }
        });
    }

    // Lorsque la modal "Panier" est ouverte, charger le contenu
    $('#panierModal').on('shown.bs.modal', function(){
        loadPanier();
    });

    // Gérer l'ajout au panier pour le bouton "Ajouter au panier" sur les cartes de jeu
    $('.btn-add-panier').on('click', function(e){
        e.preventDefault();
        e.stopPropagation(); // Empêche la propagation vers le conteneur de la carte qui déclenche le détail
        let jeuId = $(this).data('jeu-id');
        $.ajax({
            url: '/api/panier/add/' + jeuId,
            type: 'POST',
            success: function(response) {
                // Affiche la modal de succès
                $("#successPanierModalMessage").text("Jeu ajouté au panier !");
                $("#successPanierModal").modal("show");
            },
            error: function(xhr) {
                alert("Erreur lors de l'ajout au panier : " + xhr.responseText);
            }
        });
    });
});
