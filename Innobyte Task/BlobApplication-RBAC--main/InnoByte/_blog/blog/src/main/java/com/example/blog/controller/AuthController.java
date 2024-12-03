package com.example.blog.controller;

import com.example.blog.dto.AuthRequest;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserInfo;
import com.example.blog.service.JwtService;
import com.example.blog.service.PostServiceImpl;
import com.example.blog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BlogApp")
// Controller for handling user authentication and blog-related endpoints
public class AuthController {

    @Autowired
    private ProductService service; // Service to manage product-related logic (e.g., adding users)

    @Autowired
    private JwtService jwtService; // Service to handle JWT generation and validation

    @Autowired
    private PostServiceImpl Postservice; // Service to manage blog posts

    @Autowired
    private AuthenticationManager authenticationManager; // Manages user authentication

    @GetMapping("/welcome")
    // Public endpoint to display a welcome message
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    // Endpoint to add a new user to the system
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo); // Calls the service to add the user and returns a message
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Only accessible to users with ROLE_ADMIN authority
    // Endpoint to fetch all blog posts
    public List<Post> getAllTheProducts() {
        return Postservice.getAllPosts(); // Fetches all posts using the PostService
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')") // Only accessible to users with ROLE_USER authority
    // Endpoint to fetch a blog post by its ID
    public Post getProductById(@PathVariable long id) {
        return Postservice.getPostById(id); // Fetches the post by ID using the PostService
    }

    @PostMapping("/authenticate")
    // Endpoint to authenticate a user and generate a JWT token
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // Authenticate the user using their email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // If authentication is successful, generate and return a JWT token
        if (authentication.isAuthenticated()) {
            System.out.print(authRequest.getEmail()); // Debugging/logging email
            return jwtService.generateToken(authRequest.getEmail()); // Generate token
        } else {
            // If authentication fails, throw an exception
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
