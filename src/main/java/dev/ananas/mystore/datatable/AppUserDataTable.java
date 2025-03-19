package dev.ananas.mystore.datatable;

import dev.ananas.mystore.models.AppUser;
import dev.ananas.mystore.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AppUserDataTable {

    @Autowired
    private AppUserService userService;

    @GetMapping("/admin/datatable")
    public String getUserDataTable(org.springframework.ui.Model model) {
        // Retourne simplement une vue si vous souhaitez un rendu
        return "index";
    }

    @GetMapping("/admin/datatable-json")
    @ResponseBody
    public List<AppUser> getUserDataTableJson() {
        return userService.getAllUsers();
    }
}
