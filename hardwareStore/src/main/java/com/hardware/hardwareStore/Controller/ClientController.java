package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "client/index";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        try {
            if (client.getId() == null) {
                clientService.create(client);
                redirectAttributes.addFlashAttribute("success", "Cliente creado exitosamente");
            } else {
                clientService.update(client.getId(), client);
                redirectAttributes.addFlashAttribute("success", "Cliente actualizado exitosamente");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/clients";
    }

    @PostMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado exitosamente");
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/clients";
    }


    /*
     * Mapeo para las apis
     */
    @GetMapping("/api/clients")
    @ResponseBody
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/api/clients/{id}")
    @ResponseBody
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping("/api/clients")
    @ResponseBody
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.create(client));
    }

    @PutMapping("/api/clients/{id}")
    @ResponseBody
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/api/clients/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteClientApi(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}