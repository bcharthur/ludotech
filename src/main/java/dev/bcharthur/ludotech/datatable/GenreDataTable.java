package dev.bcharthur.ludotech.datatable;

import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class GenreDataTable {

    @Autowired
    private GenreService genreService;

    // Pour le rendu initial (si besoin), ici on retourne la vue "index"
    @GetMapping("/admin/genre/datatable")
    public String getGenreDataTable(org.springframework.ui.Model model) {
        return "index";
    }

    @GetMapping("/admin/genre/datatable-json")
    @ResponseBody
    public List<Genre> getGenreDataTableJson() {
        return genreService.getAllGenres();
    }
}
