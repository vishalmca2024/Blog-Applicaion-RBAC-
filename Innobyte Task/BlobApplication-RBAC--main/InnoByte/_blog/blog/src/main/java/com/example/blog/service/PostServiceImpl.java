package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.entity.UserInfo;
import com.example.blog.reporsitry.PostRepository;
import com.example.blog.reporsitry.UserInfoRepository;
import com.example.blog.service.ServiceInterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Marks the class as a Spring service, which can be injected into other classes
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository; // Repository for interacting with the Post entity

    @Autowired
    private UserInfoRepository userRepository; // Repository for interacting with the UserInfo entity


    /**
     * Creates a new blog post and associates it with the author.
     *
     * @param post The post object to be created
     * @param username The username of the user creating the post
     * @return The created post
     */
    @Override
    public Post createPost(Post post, String username) {
        // Retrieve the user by username
        Optional<UserInfo> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        // If the user does not exist, throw an exception
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User with username '" + username + "' not found");
        }

        // Set the user as the author of the post
        UserInfo author = userOptional.get();
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now()); // Set the creation time of the post
        post.setUpdatedAt(LocalDateTime.now()); // Set the update time to the current time

        // Save the post to the database and return the saved post
        return postRepository.save(post);
    }


    /**
     * Retrieves all blog posts from the database.
     *
     * @return A list of all posts
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll(); // Return all posts from the repository
    }

    /**
     * Retrieves a blog post by its ID.
     *
     * @param id The ID of the post to retrieve
     * @return The post if found
     * @throws RuntimeException If the post is not found
     */
    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found")); // Throw an exception if post is not found
    }

    /**
     * Updates an existing blog post.
     *
     * @param id The ID of the post to update
     * @param post The post object with updated data
     * @param username The username of the person trying to update the post
     * @return The updated post
     * @throws RuntimeException If the user is not the author of the post (Unauthorized)
     */
    @Override
    public Post updatePost(Long id, Post post, String username) {
        // Get the existing post by ID
        Post existingPost = getPostById(id);

        // Check if the current user is the author of the post
        if (!existingPost.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized"); // Throw exception if the user is not the author
        }

        // Update the post fields with new content
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now()); // Update the timestamp of the post

        // Save the updated post and return the updated object
        return postRepository.save(existingPost);
    }

    /**
     * Deletes a blog post.
     *
     * @param id The ID of the post to delete
     * @param username The username of the person trying to delete the post
     * @throws RuntimeException If the user is not the author of the post (Unauthorized)
     */
    @Override
    public void deletePost(Long id, String username) {
        // Get the existing post by ID
        Post existingPost = getPostById(id);

        // Check if the current user is the author of the post
        if (!existingPost.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized"); // Throw exception if the user is not the author
        }

        // Delete the post from the repository
        postRepository.delete(existingPost);
    }
}
