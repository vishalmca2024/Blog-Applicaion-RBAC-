package com.example.blog.controller;

import com.example.blog.entity.Comment;
import com.example.blog.service.ServiceInterface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
// Controller to manage CRUD operations for comments
public class CommentController {

    @Autowired
    private CommentService commentService; // Service interface to handle comment-related operations

    // Create a new comment for a specific post
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment comment, // Comment object sent in the request body
            @RequestParam Long postId, // Post ID to associate the comment with
            @RequestParam String username // Username of the user creating the comment
    ) {
        // Calls the service to create a new comment and associate it with the post and user
        Comment createdComment = commentService.createComment(comment, postId, username);
        return ResponseEntity.ok(createdComment); // Returns the created comment in the response
    }

    // Get all comments for a specific post
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPostId(
            @RequestParam Long postId // Post ID whose comments need to be retrieved
    ) {
        // Calls the service to fetch all comments associated with the specified post
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments); // Returns the list of comments in the response
    }

    // Get a single comment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(
            @PathVariable Long id // ID of the comment to retrieve
    ) {
        // Calls the service to fetch the comment by its ID
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment); // Returns the retrieved comment in the response
    }

    // Update an existing comment
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long id, // ID of the comment to be updated
            @RequestBody Comment comment, // Updated comment details
            @RequestParam String username // Username of the user requesting the update
    ) {
        // Calls the service to update the comment with new details and validate the user
        Comment updatedComment = commentService.updateComment(id, comment, username);
        return ResponseEntity.ok(updatedComment); // Returns the updated comment in the response
    }

    // Delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id, // ID of the comment to be deleted
            @RequestParam String username // Username of the user requesting the deletion
    ) {
        // Calls the service to delete the comment and validate the user's authorization
        commentService.deleteComment(id, username);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status to indicate successful deletion
    }
}
