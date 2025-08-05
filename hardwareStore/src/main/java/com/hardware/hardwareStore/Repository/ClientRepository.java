package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
}
