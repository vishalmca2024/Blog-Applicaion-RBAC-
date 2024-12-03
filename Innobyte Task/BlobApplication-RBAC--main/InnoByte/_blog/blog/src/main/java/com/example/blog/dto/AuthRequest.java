package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for authentication requests.
 * Used to encapsulate the login credentials (email and password)
 * sent by the user during authentication.
 */
@Data // Generates getters, setters, and other utility methods
@AllArgsConstructor // Generates a constructor with all fields as parameters
@NoArgsConstructor // Generates a no-argument constructor
public class AuthRequest {

    /**
     * The email of the user attempting to authenticate.
     */
    private String email;

    /**
     * The password of the user attempting to authenticate.
     */
    private String password;
}
