package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a comment on a blog post.
 * Each comment is associated with a specific post and has an author, content, and timestamp.
 */
@Entity // Marks this class as a JPA entity mapped to a database table
@Data // Generates getters, setters, and utility methods
@NoArgsConstructor // Generates a no-arguments constructor
@AllArgsConstructor // Generates a constructor with all fields as parameters
@Builder // Enables a builder pattern for creating instances
public class Comment {

    /**
     * Primary key for the Comment entity.
     * Automatically generated using an identity generation strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with the Post entity.
     * Indicates that each comment is associated with one specific post.
     * Maps to the `post_id` column in the database.
     */
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Foreign key to the Post table
    private Post post;

    /**
     * The content of the comment.
     * This field is mandatory and stored as text in the database.
     */
    @Column(nullable = false, columnDefinition = "TEXT") // Ensures non-null and stores long text
    private String content;

    /**
     * Many-to-one relationship with the UserInfo entity.
     * Indicates that each comment has an author (user).
     * Maps to the `author_id` column in the database.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false) // Foreign key to the UserInfo table
    private UserInfo author;

    /**
     * The timestamp when the comment was created.
     * Used to track when the comment was made.
     */
    private LocalDateTime createdAt;
}
