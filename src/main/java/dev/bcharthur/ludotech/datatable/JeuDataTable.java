package dev.bcharthur.ludotech.datatable;

import dev.bcharthur.ludotech.models.Jeu;
import dev.bcharthur.ludotech.service.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class JeuDataTable {

    @Autowired
    private JeuService jeuService;

    // Pour le rendu initial, vous pouvez retourner "index" ou la vue souhaitée
    @GetMapping("/admin/jeu/datatable")
    public String getJeuDataTable(org.springframework.ui.Model model) {
        return "index";
    }

    @GetMapping("/admin/jeu/datatable-json")
    @ResponseBody
    public List<Jeu> getJeuDataTableJson() {
        return jeuService.getAllJeux();
    }
}
