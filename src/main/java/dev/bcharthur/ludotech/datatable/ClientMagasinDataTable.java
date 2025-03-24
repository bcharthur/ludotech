package dev.bcharthur.ludotech.datatable;

import dev.bcharthur.ludotech.models.ClientMagasin;
import dev.bcharthur.ludotech.service.ClientMagasinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientMagasinDataTable {

    @Autowired
    private ClientMagasinService clientMagasinService;

    @GetMapping("/employe/datatable")
    public String getClientMagasinDataTable(org.springframework.ui.Model model) {
        return "index"; // ou une autre vue si nécessaire
    }

    @GetMapping("/employe/datatable-json")
    @ResponseBody
    public List<ClientMagasin> getClientMagasinDataTableJson() {
        return clientMagasinService.getAllUsers();
    }
}
