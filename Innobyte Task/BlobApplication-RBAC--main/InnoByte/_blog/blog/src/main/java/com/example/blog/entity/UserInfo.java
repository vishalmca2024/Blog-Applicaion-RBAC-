package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a user in the system.
 * Each user has a unique ID, username, email, password, roles, and associated posts and comments.
 */
@Entity // Marks this class as a JPA entity, mapped to a database table
@Data // Generates getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // Generates a constructor with all fields as parameters
@NoArgsConstructor // Generates a no-argument constructor
public class UserInfo {

    /**
     * The unique identifier for the user.
     * Automatically generated using an identity generation strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The username of the user.
     * Each user must have a unique username.
     */
    private String username;

    /**
     * The email of the user.
     * Each user must have a unique email.
     */
    private String email;

    /**
     * The password of the user.
     * This should be securely hashed before storing in the database.
     */
    private String password;

    /**
     * The roles assigned to the user.
     * The roles are stored as a comma-separated string, and represent the user's access level (e.g., ROLE_USER, ROLE_ADMIN).
     */
    private String roles;

    /**
     * One-to-many relationship with the Post entity.
     * Each user can create multiple posts, mapped by the `author` field in the Post entity.
     * CascadeType.ALL ensures that operations on a user (like saving or deleting) also affect the related posts.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    /**
     * One-to-many relationship with the Comment entity.
     * Each user can author multiple comments, mapped by the `author` field in the Comment entity.
     * CascadeType.ALL ensures that operations on a user (like saving or deleting) also affect the related comments.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Custom getter and setter methods (lombok @Data already generates these, so they are redundant)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

 /*   public UserInfo orElseThrow(Object userNotFound) {

    }*/
}
