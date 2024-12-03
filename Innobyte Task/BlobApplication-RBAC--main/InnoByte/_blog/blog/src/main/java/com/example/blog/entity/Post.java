package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a blog post in the system.
 * Each post has a title, content, an author, timestamps for creation and updates,
 * and a list of associated comments.
 */
@Entity // Marks this class as a JPA entity, mapped to a database table
@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields as parameters
@Builder // Enables a builder pattern for creating instances of Post
public class Post {

    /**
     * Primary key for the Post entity.
     * Automatically generated using an identity generation strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the blog post.
     * This field is mandatory.
     */
    @Column(nullable = false) // Ensures the title cannot be null in the database
    private String title;

    /**
     * The content of the blog post.
     * This field is mandatory and stored as text in the database.
     */
    @Column(nullable = false, columnDefinition = "TEXT") // Ensures non-null content and stores large text
    private String content;

    /**
     * Many-to-one relationship with the UserInfo entity (author of the post).
     * Each post must have one author, mapped to `author_id` in the database.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false) // Foreign key to the UserInfo table (author)
    private UserInfo author;

    /**
     * Timestamp indicating when the post was created.
     * Automatically set when the post is created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the post was last updated.
     * Can be updated when the post content is modified.
     */
    private LocalDateTime updatedAt;

    /**
     * One-to-many relationship with the Comment entity.
     * Each post can have many comments, mapped by the `post` field in the Comment entity.
     * CascadeType.ALL ensures that all related comments are persisted or deleted with the post.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
