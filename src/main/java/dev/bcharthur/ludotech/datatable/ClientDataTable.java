package dev.bcharthur.ludotech.datatable;

import dev.bcharthur.ludotech.models.Client;
import dev.bcharthur.ludotech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientDataTable {

    @Autowired
    private ClientService userService;

    @GetMapping("/admin/datatable")
    public String getUserDataTable(org.springframework.ui.Model model) {
        // Retourne simplement une vue si vous souhaitez un rendu
        return "index";
    }

    @GetMapping("/admin/datatable-json")
    @ResponseBody
    public List<Client> getUserDataTableJson() {
        return userService.getAllUsers();
    }
}
