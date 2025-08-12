package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ControllerClient {

    @Autowired
    private ClientService clientService;

    // GET /api/clients
    @GetMapping
    public List<Client> listClients() {
        return clientService.findAll();
    }

    // GET /api/clients/{id}
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.findById(id);
    }

    // POST /api/clients
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.create(client);
    }

    // PUT /api/clients/{id}
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    // DELETE /api/clients/{id}
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}