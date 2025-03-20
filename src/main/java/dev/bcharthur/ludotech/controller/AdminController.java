package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.models.Genre;
import dev.bcharthur.ludotech.repository.ClientRepository;
import dev.bcharthur.ludotech.repository.JeuRepository;
import dev.bcharthur.ludotech.repository.GenreRepository;
import dev.bcharthur.ludotech.service.ClientService;
import dev.bcharthur.ludotech.service.JeuService;
import dev.bcharthur.ludotech.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    GenreService genreService;

    @GetMapping("")
    public String adminPage(Model model) {
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "admin/index";
    }

}
