package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Client;
import com.hardware.hardwareStore.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ControllerClient {


    @Autowired
    private ClientRepository clientRepository;

    // Obtener todos los clientes
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    // Crear nuevo cliente
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        return clientRepository.save(client);
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}
