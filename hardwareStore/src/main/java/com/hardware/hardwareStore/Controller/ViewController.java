package com.hardware.hardwareStore.Controller;



import com.hardware.hardwareStore.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.hardware.hardwareStore.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/client")
    public String clientPage(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "client/index";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute Client client) {
        // Si es nuevo cliente, establecer fecha actual
        if(client.getId() == null) {
            client.setDateClient(new Date());
        } else {
            // Si es edición, mantener la fecha original
            Client existing = clientRepository.findById(client.getId()).orElse(null);
            if(existing != null) {
                client.setDateClient(existing.getDateClient());
            }
        }
        clientRepository.save(client);
        return "redirect:/client";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/client";
    }


    @GetMapping("/employee")
    public String showEmployeePage() {
        return "employee/index";
    }

    @GetMapping("/entry")
    public String showEntryPage() {
        return "entry/index";
    }

    @GetMapping("/inventory")
    public String showInventoryPage() {
        return "inventory/index";
    }

    @GetMapping("/issue")
    public String showIssuePage() {
        return "issue/index";
    }

    @GetMapping("/orderBuy")
    public String showOrderBuyPage() {
        return "orderBuy/index";
    }

    @GetMapping("/sale")
    public String showSalePage() {
        return "sale/index";
    }

    @GetMapping("/supplier")
    public String showSupplierPage() {
        return "supplier/index";
    }





}
