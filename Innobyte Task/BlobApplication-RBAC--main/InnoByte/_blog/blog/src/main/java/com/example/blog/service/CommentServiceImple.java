package com.example.blog.service;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserInfo;
import com.example.blog.reporsitry.CommentRepository;
import com.example.blog.reporsitry.PostRepository;
import com.example.blog.reporsitry.UserInfoRepository;
import com.example.blog.service.ServiceInterface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service, so it's automatically detected and registered in the Spring context
public class CommentServiceImple implements CommentService {

    @Autowired
    private CommentRepository commentRepository; // Repository for interacting with Comment entities

    @Autowired
    private PostRepository postRepository; // Repository for interacting with Post entities

    @Autowired
    private UserInfoRepository userRepository; // Repository for interacting with UserInfo entities

    /**
     * Creates a new comment for a specific post.
     *
     * @param comment  The comment object to be created
     * @param postId   The ID of the post to which the comment belongs
     * @param username The username of the user creating the comment
     * @return The saved Comment entity
     * @throws RuntimeException if the post or user is not found
     */
    @Override
    public Comment createComment(Comment comment, Long postId, String username) {
        // Fetch the post using the post ID
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found"); // Throw an exception if the post is not found
        }
        Post post = postOptional.get();

        // Fetch the user (author) using the username
        Optional<UserInfo> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found"); // Throw an exception if the user is not found
        }
        UserInfo author = userOptional.get();

        // Set the post and author details on the comment
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setCreatedAt(LocalDateTime.now()); // Set the current time as the creation time

        return commentRepository.save(comment); // Save and return the comment
    }

    /**
     * Retrieves all comments associated with a specific post ID.
     *
     * @param postId The ID of the post to get comments for
     * @return A list of comments related to the given post
     */
    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId); // Fetch comments by post ID
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id The ID of the comment to retrieve
     * @return The comment entity
     * @throws RuntimeException if the comment is not found
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found")); // If the comment doesn't exist, throw an exception
    }

    /**
     * Updates an existing comment's content.
     *
     * @param id       The ID of the comment to be updated
     * @param comment  The updated comment data
     * @param username The username of the user attempting the update
     * @return The updated Comment entity
     * @throws RuntimeException if the comment is not found or if the user is unauthorized
     */
    @Override
    public Comment updateComment(Long id, Comment comment, String username) {
        // Fetch the existing comment by ID
        Comment existingComment = getCommentById(id);

        // Check if the current user is the author of the comment
        if (!existingComment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized"); // If the user is not the author, throw an exception
        }

        // Update the comment's content
        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment); // Save and return the updated comment
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param id       The ID of the comment to be deleted
     * @param username The username of the user attempting the deletion
     * @throws RuntimeException if the comment is not found or if the user is unauthorized
     */
    @Override
    public void deleteComment(Long id, String username) {
        // Fetch the existing comment by ID
        Comment existingComment = getCommentById(id);

        // Check if the current user is the author of the comment
        if (!existingComment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized"); // If the user is not the author, throw an exception
        }

        // Delete the comment
        commentRepository.delete(existingComment); // Delete the comment from the repository
    }
}
