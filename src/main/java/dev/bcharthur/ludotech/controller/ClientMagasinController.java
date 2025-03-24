// ClientMagasinController.java
package dev.bcharthur.ludotech.controller;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.service.ClientMagasinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-magasin")
public class ClientMagasinController {

    @Autowired
    private ClientMagasinService service;

    @GetMapping
    public List<ClientMagasin> all() {
        return service.getAllFiches();
    }

    @PostMapping("/add")
    public ClientMagasin add(@RequestBody ClientMagasin fiche,
                             @RequestParam(value = "exemplaireId", required = false) Integer exemplaireId) {
        return service.create(fiche, exemplaireId);
    }
}
