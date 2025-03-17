package dev.ananas.mystore.datatable;

import dev.ananas.mystore.models.AppUser;
import dev.ananas.mystore.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class AppUserDataTable {

    @Autowired
    private AppUserService userService;

    // Méthode qui renvoie la vue avec le datatable (pour le rendu initial)
    @GetMapping("/admin/datatable")
    public String getUserDataTable(org.springframework.ui.Model model) {
        // Optionnel si vous ne souhaitez pas pré-remplir la table côté serveur
        return "index"; // ou le nom de votre vue Thymeleaf
    }

    // Nouvelle méthode pour renvoyer les données en JSON
    @GetMapping("/admin/datatable-json")
    @ResponseBody
    public List<AppUser> getUserDataTableJson() {
        return userService.getAllUsers();
    }
}
