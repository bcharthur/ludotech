$(document).ready(function() {
    $(".jeu-card").on("click", function() {
        let jeuId = $(this).attr("data-id");

        $.ajax({
            url: `/api/jeu/${jeuId}`,
            type: "GET",
            success: function(jeu) {
                $("#detailJeuTitre").text(jeu.titre);
                $("#detailJeuReference").text(jeu.reference);
                $("#detailJeuAgeMin").text(jeu.ageMin);
                $("#detailJeuDescription").text(jeu.description);
                $("#detailJeuDuree").text(jeu.duree);
                $("#detailJeuTarifJour").text(jeu.tarifJour);
                $("#detailJeuGenres").text(jeu.genres.map(g => g.libelle).join(", "));
                $("#detailJeuImage")
                    .attr("src", jeu.image || "https://placehold.co/300x200")
                    .css({
                        "height": "300px",
                        "object-fit": "contain",
                        "display": "block",
                        "margin": "auto"
                    });



                $("#detailJeuModal").modal("show");
            },
            error: function() {
                alert("Erreur lors de la récupération des détails du jeu.");
            }
        });
    });
});
