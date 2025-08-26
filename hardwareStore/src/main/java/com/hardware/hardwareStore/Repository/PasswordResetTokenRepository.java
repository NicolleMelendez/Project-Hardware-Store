package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importa Optional

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {

    // Devuelve el token envuelto en un Optional, que es la práctica moderna.
    Optional<PasswordResetToken> findByToken(String token);

    // ✅ **CORRECCIÓN 1: AÑADIMOS EL MÉTODO QUE FALTABA**
    // Este método buscará un token por el email.
    Optional<PasswordResetToken> findByEmail(String email);

    // Este método es para borrar un token por el email.
    void deleteByEmail(String email);
}