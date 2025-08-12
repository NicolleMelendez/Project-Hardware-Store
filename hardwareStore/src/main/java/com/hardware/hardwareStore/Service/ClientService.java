package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.ClientRepository;
import com.hardware.hardwareStore.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Todos los clientes
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    // Cliente por ID (con excepción si no existe)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    // Crear cliente (con validación de email)
    public Client create(Client client) {
        if (client.getEmail() != null && clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        return clientRepository.save(client);
    }

    // Actualizar cliente
    public Client update(Long id, Client client) {
        Client existingClient = findById(id);
        existingClient.setName(client.getName());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());

        // Validar email único si cambia
        if (!existingClient.getEmail().equals(client.getEmail())) {
            if (clientRepository.existsByEmail(client.getEmail())) {
                throw new RuntimeException("El nuevo email ya está registrado");
            }
            existingClient.setEmail(client.getEmail());
        }

        return clientRepository.save(existingClient);
    }

    // Eliminar cliente
    public void delete(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
}
