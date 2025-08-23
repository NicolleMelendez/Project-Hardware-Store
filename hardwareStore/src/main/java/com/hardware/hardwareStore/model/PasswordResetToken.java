package com.hardware.hardwareStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class PasswordResetToken {
    @Id
    private String token;
    private String email;
    private Date createdAt;

    // Getters y Setters
    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token, Date createdAt) {
        this.email = email;
        this.token = token;
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}