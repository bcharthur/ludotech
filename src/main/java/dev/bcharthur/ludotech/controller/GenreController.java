package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.repository.GenreRepository;
import dev.bcharthur.ludotech.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genre")
public class GenreController {

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private GenreService genreService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getGenreById(@PathVariable int id) {
        Genre genre = genreRepo.findById(id).orElse(null);
        if(genre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editGenre(@RequestBody Genre genre) {
        Genre existing = genreRepo.findById(genre.getId()).orElse(null);
        if(existing == null) {
            return ResponseEntity.badRequest().body("Genre introuvable");
        }
        existing.setLibelle(genre.getLibelle());
        genreRepo.save(existing);
        return ResponseEntity.ok("Genre modifié avec succès");
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addGenre(@RequestBody Genre genre) {
        Genre saved = genreRepo.save(genre);
        return ResponseEntity.ok("Genre ajouté avec succès");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteGenre(@PathVariable int id) {
        if(!genreRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Genre introuvable");
        }
        genreRepo.deleteById(id);
        return ResponseEntity.ok("Genre supprimé avec succès");
    }
}
