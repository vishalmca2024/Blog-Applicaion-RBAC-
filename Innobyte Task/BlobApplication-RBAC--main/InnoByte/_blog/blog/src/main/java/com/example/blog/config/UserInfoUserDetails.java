package com.example.blog.config;

import com.example.blog.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Implementation of the UserDetails interface to integrate user information with Spring Security
public class UserInfoUserDetails implements UserDetails {

    private String email; // The email address used as the username
    private String password; // The user's password
    private List<GrantedAuthority> authorities; // List of roles/authorities assigned to the user

    // Constructor to initialize UserInfoUserDetails with data from the UserInfo entity
    public UserInfoUserDetails(UserInfo userInfo) {
        email = userInfo.getEmail(); // Initialize the username (email)
        password = userInfo.getPassword(); // Initialize the password
        // Split the roles string from the UserInfo entity and convert each role to a SimpleGrantedAuthority
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new) // Convert each role to a GrantedAuthority
                .collect(Collectors.toList()); // Collect the authorities into a list
    }

    @Override
    // Returns the list of authorities (roles) assigned to the user
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    // Returns the user's password
    public String getPassword() {
        return password;
    }

    @Override
    // Returns the username (email) of the user
    public String getUsername() {
        return email;
    }

    @Override
    // Indicates whether the user's account is expired (always true here)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // Indicates whether the user's account is locked (always true here)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // Indicates whether the user's credentials (password) are expired (always true here)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // Indicates whether the user's account is enabled (always true here)
    public boolean isEnabled() {
        return true;
    }
}
