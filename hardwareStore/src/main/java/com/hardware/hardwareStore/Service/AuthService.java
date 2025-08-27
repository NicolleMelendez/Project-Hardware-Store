package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.PasswordResetTokenRepository;
import com.hardware.hardwareStore.Repository.RoleRepository;
import com.hardware.hardwareStore.Repository.UsersRepository;
import com.hardware.hardwareStore.model.PasswordResetToken;
import com.hardware.hardwareStore.model.Role;
import com.hardware.hardwareStore.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private EmailService emailService;

    public Users registerUser(String name, String email, String plainPassword, String roleName) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email ya registrado");
        }
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role no existe"));
        Users u = new Users();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(plainPassword));
        u.setRole(role);
        return userRepository.save(u);
    }

    public boolean tokenExistsForEmail(String email) {
        return passwordResetTokenRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public String createPasswordResetToken(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ese email.");
        }

        passwordResetTokenRepository.deleteByEmail(email);

        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken(email, tokenValue, new Date());
        passwordResetTokenRepository.save(token);

        emailService.sendResetPasswordEmail(email, tokenValue);

        return token.getToken();
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token de restablecimiento inválido."));

        long oneDayInMillis = 86400000;
        if (resetToken.getCreatedAt().getTime() + oneDayInMillis < new Date().getTime()) {
            throw new RuntimeException("El token de restablecimiento ha expirado. Por favor, solicite uno nuevo.");
        }

        return resetToken.getEmail();
    }

    @Transactional
    public void updatePassword(String token, String newPassword) {
        String email = validatePasswordResetToken(token);
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.deleteByEmail(email);
    }
}