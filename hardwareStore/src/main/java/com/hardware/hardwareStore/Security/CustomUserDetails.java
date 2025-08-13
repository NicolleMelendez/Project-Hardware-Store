package com.hardware.hardwareStore.Security;

import com.hardware.hardwareStore.model.Users;
import com.hardware.hardwareStore.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Users user;

    public CustomUserDetails(Users u) { this.user = u; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole() == null ?
                java.util.List.of() :
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public String getName() {
        return user.getName();
    }
    public String getRoleName() {
        return user.getRole() != null ? user.getRole().getName() : "USER";
    }
}

