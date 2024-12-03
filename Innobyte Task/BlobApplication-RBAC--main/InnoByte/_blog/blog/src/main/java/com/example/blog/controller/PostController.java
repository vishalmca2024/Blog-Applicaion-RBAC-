package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.service.ServiceInterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
// Controller to handle operations related to blog posts
public class PostController {

    @Autowired
    private PostService postService; // Service interface for managing posts

    /**
     * Endpoint to create a new post.
     *
     * @param post The post object containing details of the post to be created.
     * @param username The username of the user creating the post.
     * @return ResponseEntity containing the created Post object.
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam String username) {
        // Calls the service to create a new post and associate it with the specified user
        return ResponseEntity.ok(postService.createPost(post, username));
    }

    /**
     * Endpoint to retrieve all posts.
     *
     * @return ResponseEntity containing a list of all Post objects.
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        // Calls the service to fetch all posts
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * Endpoint to retrieve a specific post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return ResponseEntity containing the Post object corresponding to the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        // Calls the service to fetch the post by its ID
        return ResponseEntity.ok(postService.getPostById(id));
    }

    /**
     * Endpoint to update an existing post.
     *
     * @param id The ID of the post to update.
     * @param post The Post object containing updated post details.
     * @param username The username of the user updating the post.
     * @return ResponseEntity containing the updated Post object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post, @RequestParam String username) {
        // Calls the service to update the post and associate the changes with the specified user
        return ResponseEntity.ok(postService.updatePost(id, post, username));
    }

    /**
     * Endpoint to delete a post.
     *
     * @param id The ID of the post to delete.
     * @param username The username of the user requesting the deletion.
     * @return ResponseEntity with no content indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam String username) {
        // Calls the service to delete the post and validate the user’s authorization
        postService.deletePost(id, username);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status
    }
}
