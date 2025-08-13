package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.RoleRepository;
import com.hardware.hardwareStore.Repository.UsersRepository;
import com.hardware.hardwareStore.model.Role;
import com.hardware.hardwareStore.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
}